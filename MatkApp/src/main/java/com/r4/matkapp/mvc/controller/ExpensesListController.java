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
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.view.ExpenseListFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
    private Label descriptionLabel, totalpriceLabel, participantsLabel, totalPrice, descriptionLabel1, totalpriceLabel1, participantsLabel1, totalLabel, descriptionLabel2, totalpriceLabel2, participantsLabel2;
    @FXML
    private Button updateButton, updateButton1, updateButton2;
    @FXML
    private Tab allExpenses, myExpenses, notJoinedExpenses;

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
        descriptionLabel1.setText(bundle.getString("GroupExpensesDescLabel"));
        descriptionLabel2.setText(bundle.getString("GroupExpensesDescLabel"));
        totalpriceLabel.setText(bundle.getString("GroupExpensesPriceLabel"));
        totalpriceLabel1.setText(bundle.getString("GroupExpensesMyPortionLabel"));
        totalpriceLabel2.setText(bundle.getString("GroupExpensesPriceLabel"));
        participantsLabel.setText(bundle.getString("GroupExpensesParticipantLabel"));
        participantsLabel1.setText(bundle.getString("GroupExpensesParticipantLabel"));
        participantsLabel2.setText(bundle.getString("GroupExpensesParticipantLabel"));
        updateButton.setText(bundle.getString("GroupExpensesUpdateButton"));
        updateButton1.setText(bundle.getString("GroupExpensesUpdateButton"));
        updateButton2.setText(bundle.getString("GroupExpensesUpdateButton"));
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
        list.getChildren().add(new ExpenseListFactory(this).createList(cont.getSelectedGroup().getExpenses()));

        Set<Expense>[] expenses = split(cont.getSelectedGroup().getExpenses());
        listNotJoined.getChildren().remove(1); // remove old list (index 1)
        listNotJoined.getChildren().add(new ExpenseListFactory(this).createList(expenses[0]));

        listJoined.getChildren().remove(1);
        listJoined.getChildren().add(new ExpenseListFactory(true).createList(expenses[1]));
        double total = 0;
        for (Expense e : expenses[1]) {
            total += e.getExpense_amount();
        }
        totalPrice.setText(Double.toString(total));

    }

    private Set<Expense>[] split(Set<Expense> expenses) {
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
        Set<Expense>[] result = new Set[2];
        result[0] = new HashSet<>(notJoined);
        result[1] = new HashSet<>(joined);
        return result;
    }
}
