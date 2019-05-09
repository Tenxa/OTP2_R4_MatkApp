package com.r4.matkapp.mvc.view;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.controller.ExpensesListController;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.controller.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.controller.dbmanager.ExpenseManager;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
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

    private static final String PROPERTIES = "properties.default";
    private ResourceBundle bundle;
    private String join;
    private String info;
    
    private boolean split;
    private ExpensesListController ctrl;
   

    public ExpenseListFactory() {
        this(ResourceBundle.getBundle(PROPERTIES, MainApp.getLocale()), false);
    }
    
    public ExpenseListFactory(boolean split) {
        this(ResourceBundle.getBundle(PROPERTIES, MainApp.getLocale()), split);
    }
    
    public ExpenseListFactory(ExpensesListController ctrl) {
        this(ResourceBundle.getBundle(PROPERTIES, MainApp.getLocale()), false);
        this.ctrl = ctrl;
    }
    
    public ExpenseListFactory(ResourceBundle rb, boolean split) {
        this.split = split;
        this.bundle = rb;
        this.join = bundle.getString("GroupExpensesJoinButton");
        this.info = bundle.getString("GroupExpensesInfoButton");
    }

    public GridPane createList(List<Expense> expenses) {
        if(expenses == null) {
            return null;
        }
        GridPane pane = new GridPane();
        int row = 0;
        for (Expense e : expenses) {

            Pane infoPane = createInfoPane();
            Pane description = createDescriptionPane(e.getExpense_description());
            double ammount = e.getExpense_amount();
            if(split) {
                ammount = ammount / e.getUsers().size();
            }
            Pane price = createPricePane(ammount);
            Pane participants = createParticipantsPane(e.getUsers().size());
            Pane joinPane = new Pane();
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
                joinPane = createJoinPane(e);
            }

            if (row % 2 == 0) {
                colorRow(infoPane);
                colorRow(description);
                colorRow(price);
                colorRow(joinPane);
                colorRow(participants);
            }

            pane.addRow(row++, description, price, participants, infoPane, joinPane);
            pane.getRowConstraints().add(new RowConstraints(50));

        }
        setColumnConstraints(pane);
        return pane;
    }

    private void setColumnConstraints(GridPane pane) {
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
            if(ctrl != null) {
                ctrl.update();
            }
        });
        b.setMinHeight(30);
        b.setMinWidth(50);
        anchorButton(b);
        
        p.getChildren().add(b);    
        return p;
    }

    private Pane createInfoPane() {
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
        Label l = new Label(String.format("%.2f",price));
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
