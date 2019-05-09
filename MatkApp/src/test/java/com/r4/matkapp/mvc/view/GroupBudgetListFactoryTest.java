/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view;

import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Eero
 */
@Ignore
public class GroupBudgetListFactoryTest extends ApplicationTest {

    public GroupBudgetListFactoryTest() {
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
     * Test createList() method by creating list with Groups and asserting its
     * size and correct Label texts.
     */
    @Test
    public void testCreateList() {
        Set<Group> groups = null;
        GroupBudgetListFactory factory = new GroupBudgetListFactory();
        VBox result = factory.createList(groups);
        assertNull(result);

        Group g = new Group();
        g.setGroup_name("test");
        double budget = 200;
        g.setBudget(budget);

        Set<Expense> expenses = new HashSet<>();
        Expense e = new Expense();
        double expenseAmmount = 50;
        e.setExpense_amount(expenseAmmount);
        expenses.add(e);
        g.setExpenses(expenses);

        groups = new HashSet<>();
        groups.add(g);

        result = factory.createList(groups);
        assertEquals(1, result.getChildren().size());

        GridPane pane = (GridPane) result.getChildren().get(0);

        Label name = (Label) getNodeByRowColumnIndex(0, 0, pane);
        assertEquals("test", name.getText());

        Label percentage = (Label) getNodeByRowColumnIndex(1, 1, pane);
        double dafug = expenseAmmount / budget;
        String expected = String.format("%.1f", dafug * 100) + "%";
        assertEquals(expected, percentage.getText());

        assertEquals(3, getRowCount(pane));

    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
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

}
