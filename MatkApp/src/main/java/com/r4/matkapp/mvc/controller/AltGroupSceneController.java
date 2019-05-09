/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.GroupManager;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.ConfirmationAlert;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class AltGroupSceneController extends AbstractSceneController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private ProgressBar budgetBar;
    @FXML
    private Label budgetIndicator, groupNameLabel;
    @FXML
    private Button homeButton, settingsButton, inviteButton, addExpButton, leaveGrpButton, expensesButton, notepadButton, chatButton;
    
    private Group selectedGroup;
    private Node navMenu;
    private ExpensesListController expenseList;
    
    private ResourceBundle bundle;

    protected AltGroupSceneController(AbstractSceneController ctrl, Group g) {
        super(ctrl);
        DatabaseManager<Group> manager = new GroupManager();
        selectedGroup = manager.find(g.getId());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
        homeButton.setText(bundle.getString("GroupHomeButton"));
        settingsButton.setText(bundle.getString("GroupSettingsButton"));
        inviteButton.setText(bundle.getString("GroupInviteUserButton"));
        addExpButton.setText(bundle.getString("GroupAddExpense"));
        leaveGrpButton.setText(bundle.getString("GroupExitGroup"));
        
        expensesButton.setText(bundle.getString("GroupExpensesButton"));
        notepadButton.setText(bundle.getString("GroupNotepadButton"));
        chatButton.setText(bundle.getString("GroupChatButton"));
        
        updateBudgetProgress();
        groupNameLabel.setText(getSelectedGroup().getGroup_name());
        navMenu = root.getCenter();

    }

    @FXML
    private void showGroupProperties() {
        try {
            Parent rootPane = loadFXML("GroupSettingsScene", new GroupSettingsSceneController(this, selectedGroup));

            String title = selectedGroup.getGroup_name() + " " + bundle.getString("GroupSettingHeaderLabel").intern();
            createNewStage(rootPane, title);
        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openExpenseWizard() {
        try {
            Parent rootPane = loadFXML("ExpenseWizard", new ExpenseWizardController(this, selectedGroup));
            createNewStage(rootPane, bundle.getString("GroupNewExpenseHeaderLabel"));
        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createNewStage(Parent pane, String title) throws IOException {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(pane));

        // set owner and modality
        stage.initOwner(MainApp.getWindow());
        // block evets from beign delivered to its entire owner window hierarchy
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    private void openGroupInvitationScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GroupInvitationScene.fxml"));
            loader.setController(new GroupInvitationSceneController(selectedGroup));

            Stage stage = new Stage();
            stage.setTitle(bundle.getString("GroupInviteHeaderLabel"));
            stage.setScene(new Scene(loader.load()));
            stage.initOwner(MainApp.getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void setNavMenu() {
        root.setCenter(navMenu);
    }

    @FXML
    private void setExpenseListScene() {
        if (expenseList == null) {
            expenseList = new ExpensesListController(this);

        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExpensesList.fxml"));
        loader.setController(expenseList);
        try {
            root.setCenter(loader.load());
        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void leaveGroup() throws IOException {
        AlertFactory confirmation = new ConfirmationAlert();
        Alert alert = confirmation.createAlert(null, bundle.getString("GroupExitConfirmLabel") + selectedGroup.getGroup_name() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Set<User> users = selectedGroup.getUsers();
            Iterator<User> itr = users.iterator();
            while (itr.hasNext()) {
                if (itr.next().getId() == DatabaseManager.getLoggedUser().getId()) {
                    itr.remove();
                }
            }
            selectedGroup.setUsers(users);
            DatabaseManager<Group> manager = new GroupManager();
            manager.update(selectedGroup);
            owner.update();
            ((RootSceneController) owner).setHomeScene();
        }
    }

    private void updateBudgetProgress() {
        Set<Expense> expenses = selectedGroup.getExpenses();
        double spent = 0;
        for (Expense e : expenses) {
            spent += e.getExpense_amount();
        }
        double progress = spent / selectedGroup.getBudget();

        budgetIndicator.setText(String.format("%.1f", progress * 100) + "% " + bundle.getString("GroupExpensesPercentage"));
        budgetBar.setProgress(progress);
    }

    /**
     * @return the selectedGroup
     */
    public Group getSelectedGroup() {
        return selectedGroup;
    }

    @Override
    protected void update() {
        DatabaseManager<Group> manager = new GroupManager();
        selectedGroup = manager.find(selectedGroup.getId());
        if (!(groupNameLabel.getText().equals(selectedGroup.getGroup_name()))) {
            groupNameLabel.setText(getSelectedGroup().getGroup_name());
            owner.update();
        }

        if (expenseList != null) {
            expenseList.loadExpenseList();
        }

        updateBudgetProgress();
    }

    // poistaa kuha kaikki child scenet muutettu et extendaa abstractscene
    void updateGroupData() {
        update();
    }

}