/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.view.GroupBudgetListFactory;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author Eero
 */
public class HomeSceneController implements Initializable, SceneController  {

    @FXML
    ProgressBar bar;
    
    @FXML Label text, personalTotal;

    @FXML
    private ScrollPane budgetList;
    
    int p;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        junajunajuna();
        refresh();
    }

    @FXML
    private void junajunajuna() {
        
        bar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue) {
                    text.setText("....?");
                } else {
                    text.setText("juna kulkee...");
                }
            }
        });
        
        bar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.doubleValue() == 1) {
                    text.setText("nonniii! jotai saatu aikaseks");
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
                for(int i=0; i<s; i++) {
                    Thread.sleep(1000);
                    updateProgress(i+1, s);
                }
                return true;
            }
            
        };
    }
    
    private void refresh() {
        DatabaseManager.updateLoggedUser();
        budgetList.setContent(new GroupBudgetListFactory().createList(DatabaseManager.getLoggedUser().getGroup()));
        personalTotal.setText(Double.toString(calculateTotalExpense()));
    }
    
    private double calculateTotalExpense() {
        User u = DatabaseManager.getLoggedUser();
        double total = 0;
        for(Expense e : u.getUser_expenses()) {
            total += e.getExpense_amount();
        }
        return total;  
    }

}
