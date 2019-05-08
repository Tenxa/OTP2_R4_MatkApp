/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view;

import com.r4.matkapp.mvc.model.Expense;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Eero
 */
public class ExpenseListFactoryTest extends ApplicationTest {

    public ExpenseListFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createList method, of class ExpenseListFactory.
     */
    @Test
    public void testCreateList() {
        List<Expense> expenses = null;
        ResourceBundle bundle = ResourceBundle.getBundle("properties.default", new Locale("en", "US"));
        ExpenseListFactory factory = new ExpenseListFactory(bundle, true);
        GridPane result = factory.createList(expenses);
        assertNull(result);

        expenses = new ArrayList<>();
        Expense e = new Expense();
        String name = "test";
        double ammount = 50;
        e.setExpense_description(name);
        e.setExpense_amount(ammount);
        expenses.add(e);
        result = factory.createList(expenses);
        
        int expectedColumnCount = 5;
        assertEquals(expectedColumnCount, result.getChildren().size());
        
        int expectedRowCount = 1;
        assertEquals(expectedRowCount, getRowCount(result));
        
    }

    private int getRowCount(GridPane pane) {
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) {
                Integer rowIndex = GridPane.getRowIndex(child);
                if (rowIndex != null) {
                    numRows = Math.max(numRows, rowIndex + 1);
                }
            }
        }
        return numRows;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

}
