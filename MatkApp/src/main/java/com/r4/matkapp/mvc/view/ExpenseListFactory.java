package com.r4.matkapp.mvc.view;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.ExpenseManager;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Eero
 */
public class ExpenseListFactory {

    private ResourceBundle bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
    private final String join = bundle.getString("GroupExpensesJoinButton"), info = bundle.getString("GroupExpensesInfoButton");
    private GridPane pane;

    public ExpenseListFactory() {
        pane = new GridPane();
        setColumnConstraints();
    }

    public GridPane createList(Set<Expense> expenses) {

        int row = 0;
        for (Expense e : expenses) {

            Pane info = createInfoPane(e.getId());
            Pane description = createDescriptionPane(e.getExpense_description());
            Pane price = createPricePane(e.getExpense_amount());
            Pane participants = createParticipantsPane(e.getUsers().size());
            Pane join = new Pane();
            Iterator itr = e.getUsers().iterator();
            boolean isInGroup = false;
            while(itr.hasNext()) {
                User u = (User) itr.next();
                if(u.getId() == DatabaseManager.getLoggedUser().getId()) {
                    isInGroup = true;
                    break;
                }
            }
            if(!isInGroup) {
                join = createJoinPane(e);
            }

            if (row % 2 == 0) {
                colorRow(info);
                colorRow(description);
                colorRow(price);
                colorRow(join);
                colorRow(participants);
            }

            pane.addRow(row++, description, price, participants, info, join);
            pane.getRowConstraints().add(new RowConstraints(50));

        }
        return pane;
    }

    private void setColumnConstraints() {
        ColumnConstraints descriptionColumn = new ColumnConstraints();
        descriptionColumn.setFillWidth(true);
        descriptionColumn.setPercentWidth(40);
  

        ColumnConstraints priceColumn = new ColumnConstraints();
        priceColumn.setFillWidth(true);
        priceColumn.setPercentWidth(15);


        ColumnConstraints buttonColumn = new ColumnConstraints();
        buttonColumn.setFillWidth(true);
        buttonColumn.setPercentWidth(15);

        pane.getColumnConstraints().addAll(descriptionColumn, priceColumn, priceColumn,
                buttonColumn, buttonColumn);

    }

    private Pane createJoinPane(Expense e) {
        AnchorPane p = new AnchorPane();
        Button b = new Button(join);
        b.setOnAction((ActionEvent event) -> {
           
            User u = DatabaseManager.getLoggedUser();
            e.addUser(u);
            DatabaseManager<Expense> manager = new ExpenseManager();
            manager.update(e);
            p.getChildren().clear();
        });
        b.setMinHeight(30);
        b.setMinWidth(50);
        anchorButton(b);
        
        p.getChildren().add(b);    
        return p;
    }

    private Pane createInfoPane(int expenseID) {
        AnchorPane p = new AnchorPane();
        Button b = new Button(info);
        b.setMinHeight(30);
        anchorButton(b);
        
        p.getChildren().add(b);
        return p;
    }

    private Pane createDescriptionPane(String expense) {
        AnchorPane p = new AnchorPane();
        Label l = new Label(expense);
        anchorLabel(l);
        
        p.getChildren().add(l); 
        return p;
    }

    private Pane createPricePane(double price) {
        AnchorPane p = new AnchorPane();
        Label l = new Label(Double.toString(price));
        anchorLabel(l);
        
        p.getChildren().add(l); 
        return p;
    }

    private Pane createParticipantsPane(int totalParticipants) {
        AnchorPane p = new AnchorPane();
        Label l = new Label(Integer.toString(totalParticipants));
        anchorLabel(l);

        p.getChildren().add(l);
        return p;
    }

    private void colorRow(Pane p) {
        if(p != null) {
            p.setStyle("-fx-background-color: #d4e2f9");
        }  
    }

    private void anchorLabel(Label l) {
        AnchorPane.setLeftAnchor(l, 5.0);
        AnchorPane.setTopAnchor(l, 0.0);
        AnchorPane.setBottomAnchor(l, 0.0);
    }
    
    private void anchorButton(Button b) {
        AnchorPane.setRightAnchor(b, 10.0);
        AnchorPane.setBottomAnchor(b, 4.0);
        AnchorPane.setTopAnchor(b, 4.0);
    }
}
