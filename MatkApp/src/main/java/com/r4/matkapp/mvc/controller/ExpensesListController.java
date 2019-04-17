/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.view.ExpenseListFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class ExpensesListController implements Initializable {

    List<String> expenses = new ArrayList<>();
    private Set<Expense> groupExpenses;

    @FXML
    private GridPane expenseList;
    @FXML
    private VBox list;
    
    private GridPane pane;
    
    private Group group;
    
    public ExpensesListController(AltGroupSceneController cont) {
        group = cont.getSelectedGroup();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshExpenseList();       
    }
    
    @FXML
    private void refreshExpenseList() {
        list.getChildren().remove(1);
        group = (Group) UserController.groupDAO.find(group.getId());
        ExpenseListFactory factory = new ExpenseListFactory();
        //pane = factory.createList(group.getExpenses());
        list.getChildren().add(factory.createList(group.getExpenses()));
    }

    
    
}
