/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class AltGroupSceneController implements Initializable {

    @FXML
    ProgressBar budgetBar;
    @FXML
    Label budgetIndicator, groupNameLabel;

    private static Group SELECTED_GROUP;

    private RootSceneController parentController;

    protected AltGroupSceneController(RootSceneController ctrl, int groupId) {
        parentController = ctrl;
        SELECTED_GROUP = (Group) UserController.groupdao.find(groupId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateBudgetProgress();
        groupNameLabel.setText(SELECTED_GROUP.getGroup_name());

    }

    @FXML
    private void showGroupProperties() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GroupSettingsScene.fxml"));
            loader.setController(new GroupSettingsSceneController(this, SELECTED_GROUP));

            Stage stage = new Stage();
            stage.setTitle(SELECTED_GROUP.getGroup_name() + " Asetukset");
            stage.setScene(new Scene(loader.load()));
            stage.initOwner(MainApp.getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void openExpenseWizard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExpenseWizard.fxml"));
            loader.setController(new ExpenseWizardController(this, SELECTED_GROUP));

            Stage stage = new Stage();
            stage.setTitle("Luo uusi kulu");
            stage.setScene(new Scene(loader.load()));
            stage.initOwner(MainApp.getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AltGroupSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // parannettavaa..
    protected void updateGroupData(Group g) {
        if (SELECTED_GROUP.getGroup_name() != g.getGroup_name()) {
            groupNameLabel.setText(SELECTED_GROUP.getGroup_name());
            parentController.updateGroupList();
        }
        SELECTED_GROUP = g;
        updateBudgetProgress();

    }

    private void updateBudgetProgress() {
        Set<Expense> expenses = SELECTED_GROUP.getExpenses();
        double spent = 0;
        for (Expense e : expenses) {
            spent += e.getExpense_amount();
        }
        double progress = spent / SELECTED_GROUP.getBudget();

        budgetIndicator.setText(String.format("%.1f", progress * 100) + "% käytetty budjetista");
        budgetBar.setProgress(progress);
    }

}
