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
import com.r4.matkapp.mvc.view.GroupBudgetListFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller for HomeScene.
 * 
 * @author Eero
 */
public class HomeSceneController extends AbstractSceneController {

    @FXML
    private ProgressBar bar;

    @FXML
    private Label text, personalTotal, groupBudgetsLabel, totalExpensesLabel, lastMonthLabel, summaryLabel;

    @FXML
    private ScrollPane budgetList;
    
    @FXML
    private Button junaResetButton;

    private ResourceBundle bundle;

    public HomeSceneController(SceneController owner) {
        super(owner);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());

        groupBudgetsLabel.setText(bundle.getString("HomeLabelGroupBudgets"));
        totalExpensesLabel.setText(bundle.getString("HomeLabelTotalExpenses"));
        lastMonthLabel.setText(bundle.getString("HomeLabelLastMonth"));
        summaryLabel.setText(bundle.getString("HomeLabelSummary"));
        junaResetButton.setText(bundle.getString("HomeButtonTrainReset"));
        
        junajunajuna();
        update();
    }

    @FXML
    private void junajunajuna() {

        bar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    text.setText("....?");
                } else {
                    text.setText(bundle.getString("HomeLabelTrainProgress"));
                }
            }
        });

        bar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() == 1) {
                    text.setText(bundle.getString("HomeLabelTrainFinished"));
                }
            }
        });

        Task juna = juna(50);
        bar.progressProperty().unbind();
        bar.progressProperty().bind(juna.progressProperty());
        bar.progressProperty().unbind();
        bar.progressProperty().bind(juna.progressProperty());
        new Thread(juna).start();
    }

    private Task juna(final int s) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < s; i++) {
                    Thread.sleep(1000);
                    updateProgress(i + 1, s);
                }
                return true;
            }

        };
    }

    /**
     * Updates budget list and total personal expenses.
     */
    @Override
    public void update() {
        DatabaseManager.updateLoggedUser();
        budgetList.setContent(new GroupBudgetListFactory().createList(DatabaseManager.getLoggedUser().getGroup()));
        personalTotal.setText(Double.toString(calculateTotalExpense()));
    }

    private double calculateTotalExpense() {
        User u = DatabaseManager.getLoggedUser();
        double total = 0;
        for (Expense e : u.getUser_expenses()) {
            total += e.getExpense_amount();
        }
        return total;
    }

}
