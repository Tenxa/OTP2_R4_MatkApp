/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.view.ExpenseListFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML
    private Label descriptionLabel, totalpriceLabel, participantsLabel;
    @FXML
    private Button updateButton;

    private Group activeGroup;
    private AltGroupSceneController cont;
    private ResourceBundle bundle;
    
    public ExpensesListController(AltGroupSceneController cont) {
        this.cont = cont;
    }

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
        refreshExpenseList();
        
        descriptionLabel.setText(bundle.getString("GroupExpensesDescLabel"));
        totalpriceLabel.setText(bundle.getString("GroupExpensesPriceLabel"));
        participantsLabel.setText(bundle.getString("GroupExpensesParticipantLabel"));
        updateButton.setText(bundle.getString("GroupExpensesUpdateButton"));
    }
    
    @FXML
    private void refreshExpenseList() { 
        cont.updateGroupData();
    } 
    
    public void loadExpenseList() {
        list.getChildren().remove(1); // remove old list (index 1)
        ExpenseListFactory factory = new ExpenseListFactory();
        list.getChildren().add(factory.createList(cont.getSelectedGroup().getExpenses()));
    }
}
