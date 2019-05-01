/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.view.ExpenseListFactory;
import java.net.URL;
import java.util.ResourceBundle;
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

    @FXML
    private GridPane expenseList;
    @FXML
    private VBox list;

    private Group activeGroup;
    private AltGroupSceneController cont;
    
    public ExpensesListController(AltGroupSceneController cont) {
        this.cont = cont;
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
        list.getChildren().remove(1); // remove old list (index 1)
        cont.updateGroupData();
        
        ExpenseListFactory factory = new ExpenseListFactory();
        list.getChildren().add(factory.createList(cont.getSelectedGroup().getExpenses()));
    } 
}
