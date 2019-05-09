/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.controller.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.view.ExpenseListFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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
    private VBox list, listJoined, listNotJoined;
    @FXML
    private Label descriptionLabel, totalpriceLabel, participantsLabel, totalPrice, 
            descriptionLabel1, totalpriceLabel1, participantsLabel1, totalLabel, 
            descriptionLabel2, totalpriceLabel2, participantsLabel2;
    @FXML
    private Button updateButton, updateButton1, updateButton2;
    @FXML
    private Tab allExpenses, myExpenses, notJoinedExpenses;

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
        
        final String GROUP_EXPENSE_DESC_LABEL = bundle.getString("GroupExpensesDescLabel");
        descriptionLabel.setText(GROUP_EXPENSE_DESC_LABEL);
        descriptionLabel1.setText(GROUP_EXPENSE_DESC_LABEL);
        descriptionLabel2.setText(GROUP_EXPENSE_DESC_LABEL);
        
        final String GROUP_EXPENSE_PRICE_LABEL = bundle.getString("GroupExpensesPriceLabel");
        totalpriceLabel.setText(GROUP_EXPENSE_PRICE_LABEL);
        totalpriceLabel1.setText(bundle.getString("GroupExpensesMyPortionLabel"));
        totalpriceLabel2.setText(GROUP_EXPENSE_PRICE_LABEL);
        
        final String GROUP_EXPENSE_PARTICIPANT_LABEL = bundle.getString("GroupExpensesParticipantLabel");
        participantsLabel.setText(GROUP_EXPENSE_PARTICIPANT_LABEL);
        participantsLabel1.setText(GROUP_EXPENSE_PARTICIPANT_LABEL);
        participantsLabel2.setText(GROUP_EXPENSE_PARTICIPANT_LABEL);
        
        final String GROUP_EXPENSE_UPDATE_BUTTON = bundle.getString("GroupExpensesUpdateButton");
        updateButton.setText(GROUP_EXPENSE_UPDATE_BUTTON);
        updateButton1.setText(GROUP_EXPENSE_UPDATE_BUTTON);
        updateButton2.setText(GROUP_EXPENSE_UPDATE_BUTTON);
        
        allExpenses.setText(bundle.getString("GroupExpensesTabAll"));
        myExpenses.setText(bundle.getString("GroupExpensesTabMine"));
        notJoinedExpenses.setText(bundle.getString("GroupExpensesTabOthers"));
        totalLabel.setText(bundle.getString("GroupExpensesTotalPortionLabel") + " ");
    }

    @FXML
    public void refreshExpenseList() {
        cont.updateGroupData();
    }

    public void loadExpenseList() {

        list.getChildren().remove(1);
        List<Expense> l = new ArrayList<>(cont.getSelectedGroup().getExpenses());
        Collections.sort(l);
        list.getChildren().add(new ExpenseListFactory(this).createList((l)));

        List<Expense>[] expenses = split(cont.getSelectedGroup().getExpenses());
        listNotJoined.getChildren().remove(1); // remove old list (index 1)
        listNotJoined.getChildren().add(new ExpenseListFactory(this).createList(expenses[0]));

        listJoined.getChildren().remove(1);
        listJoined.getChildren().add(new ExpenseListFactory(true).createList(expenses[1]));
        double total = 0;
        for (Expense e : expenses[1]) {
            total += e.getExpense_amount() / e.getUsers().size();
        }
        totalPrice.setText(String.format("%.2f", total));

    }

    private List<Expense>[] split(Set<Expense> expenses) {
        List<Expense> notJoined = new ArrayList<>();
        List<Expense> joined = new ArrayList<>();
        User loggedUser = DatabaseManager.getLoggedUser();
        for (Expense e : expenses) {
            boolean isJoined = false;
            for (User u : e.getUsers()) {
                if (loggedUser.getId() == u.getId()) {
                    isJoined = true;
                }
            }
            if (isJoined) {
                joined.add(e);
            } else {
                notJoined.add(e);
            }
        }
        Collections.sort(notJoined);
        Collections.sort(joined);
        List<Expense>[] result = new List[2];
        result[0] = notJoined;
        result[1] = joined;
        return result;
    }
}
