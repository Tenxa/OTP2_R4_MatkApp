/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.dao.ExpenseDAO;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Eero
 */
public class GroupSceneController implements Initializable {

    @FXML
    TitledPane titledPane;

    @FXML
    TextField newExpenseName, newExpenseValue;
    
    private Group activeGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // lister for expense value field -> removes all non numeric inputs
        newExpenseValue.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                newExpenseValue.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    public void setActiveGroup(Group g) {
        activeGroup = g;
    }

    public void setTitleText(String s) {
        titledPane.setText(s);
    }

    @FXML
    private void createNewExpense() {
        Expense e = new Expense();
        e.setExpense_description(newExpenseName.getText());
        e.setExpense_amount(Double.parseDouble(newExpenseValue.getText()));
        e.setExpense_group(activeGroup);
        
        ExpenseDAO dao = new ExpenseDAO(UserController.dbSession.getSessionFactory());
        
        dao.create(e);      
    }
}
