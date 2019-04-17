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
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class ExpenseWizardController implements Initializable {

    @FXML
    StackPane firstPane, secondPane;

    @FXML
    private TextField description, price;
    @FXML
    private CheckBox equalSplit;
    @FXML
    private ListView<User> users, selectedUsers;

    private boolean isEqualSplit;

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
        expense.setExpense_amount(Double.parseDouble(price.getText()));
        expense.setEqual_split(isEqualSplit);
        expense.setExpense_group(activeGroup);
        
        //  jostai syyst ei luo user - expense relaatioo
        Set<User> set = new HashSet<>(selectedUsers.getItems());
        expense.setUsers(set);
        UserController.expenseDAO.create(expense);
        
        // pitää viel lisäks päivittää käyttäjät eriksee
        // en keksiny miks, vois joskus selvitellä
        /*
        ObservableList<User> list = selectedUsers.getItems();
        for (User u : list) {
           u.addUser_expense(expense);
           UserController.dao.update(u);
        }
        */
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
