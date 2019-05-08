/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view;

import com.r4.matkapp.mvc.controller.HomeSceneController;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import java.util.Set;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 *
 * @author Eero
 */
public class GroupBudgetListFactory {

    HomeSceneController ctrl;

    public VBox createList(Set<Group> groups) {
        if(groups == null) {
            return null;
        }
        VBox box = new VBox();
        box.setFillWidth(true);
        box.setMaxWidth(Double.MAX_VALUE);
        box.setMaxHeight(Double.MAX_VALUE);
        
        
        for (Group g : groups) {
            GridPane p = createEntry(g);
            box.getChildren().add(p);
        }
        
        return box;
    }

    private GridPane createEntry(Group g) {
        GridPane pane = new GridPane();
        pane.add(new Label(g.getGroup_name()), 0, 0, 3, 1);
        
        double percent = calculatePercentage(g);
        pane.add(new Label(Double.toString(calculateSpent(g))), 0, 1, 1, 1);
        pane.add(new Label(String.format("%.1f", percent * 100) + "%"), 1, 1, 1 ,1);
        pane.add(new Label(Double.toString(g.getBudget())), 2, 1, 1 ,1);
        
        ProgressBar bar = new ProgressBar(percent);
        bar.setMaxWidth(Double.MAX_VALUE);
        pane.add(bar, 0, 2, 3 ,1);
        
        pane.styleProperty().set("-fx-background-color: white;");
        pane.setPadding(new Insets(5,5,5,5));
        setColumnConstraints(pane);
        pane.setMaxWidth(Double.MAX_VALUE);
        pane.setMaxHeight(Double.MAX_VALUE);
        return pane;
    }

    private double calculateSpent(Group group) {
        double spent = 0;
        for (Expense e : group.getExpenses()) {
            spent += e.getExpense_amount();
        }
        return spent;
    }

    private double calculatePercentage(Group group) {
        double spent = 0;
        for (Expense e : group.getExpenses()) {
            spent += e.getExpense_amount();
        }
        return spent / group.getBudget();
    }
    
    private void setColumnConstraints(GridPane p) {
        ColumnConstraints col = new ColumnConstraints(50, 100, Double.MAX_VALUE);
        col.setHalignment(HPos.CENTER);
        col.setFillWidth(true);
        col.setHgrow(Priority.ALWAYS);
        p.getColumnConstraints().addAll(col, col, col);
    }

}
