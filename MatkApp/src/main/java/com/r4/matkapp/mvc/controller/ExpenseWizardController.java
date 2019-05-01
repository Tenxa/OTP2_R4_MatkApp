package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.r4.matkapp.dao.ExpenseDAO;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.ExpenseManager;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.view.ElementInitor;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class ExpenseWizardController implements Initializable, SceneController {

    @FXML
    private StackPane firstPane, secondPane;

    @FXML
    private TextField description;
    @FXML
    private Spinner<Double> priceSpinner;
    @FXML
    private CheckBox equalSplit;
    @FXML
    private ListView<User> users, selectedUsers;

    private boolean isEqualSplit;

    // selected group object
    private Group activeGroup;
    private AltGroupSceneController parentController;

    protected ExpenseWizardController(AltGroupSceneController c, Group g) {
        parentController = c;
        activeGroup = g;
        isEqualSplit = false;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        initListView(users);
        initListView(selectedUsers);
        users.getItems().addAll(FXCollections.observableArrayList(activeGroup.getUsers()));

        new ElementInitor().init(priceSpinner);

        equalSplit.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isEqualSplit = newValue;
            }
        });

    }

    

    @FXML
    private void closeWindow(ActionEvent event) {
        parentController.updateGroupData();
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void createExpense(ActionEvent event) {
        Expense expense = new Expense();
        expense.setExpense_description(description.getText());
        expense.setExpense_amount((double) priceSpinner.getValue());
        expense.setEqual_split(isEqualSplit);
        expense.setExpense_group(activeGroup);

        Set<User> set = new HashSet<>(selectedUsers.getItems());
        expense.setUsers(set);
        
        DatabaseManager<Expense> manager = new ExpenseManager();
        manager.create(expense);
        
        closeWindow(event);
    }

    @FXML
    private void add() {
        ObservableList<User> list = users.getSelectionModel().getSelectedItems();
        for (User u : list) {
            users.getItems().remove(u);
            selectedUsers.getItems().add(u);
        }

    }

    @FXML
    private void remove() {
        ObservableList<User> list = users.getSelectionModel().getSelectedItems();
        for (User u : list) {
            selectedUsers.getItems().remove(u);
            users.getItems().add(u);
        }

    }

    @FXML
    private void setFirstPane() {
        firstPane.toFront();
    }

    @FXML
    private void setSecondPane() {
        secondPane.toFront();
    }

    /**
     * Set cell factory for users ListView
     *
     * cell value == first name
     */
    private void initListView(ListView list) {
        list.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> cell = new ListCell<User>() {

                    @Override
                    protected void updateItem(User u, boolean empty) {
                        super.updateItem(u, empty);
                        if (u != null) {
                            setText(u.getFirst_name());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
    }
}
