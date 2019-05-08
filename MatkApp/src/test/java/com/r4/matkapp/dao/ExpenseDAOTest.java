/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Expense;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Eero
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:DAOTestingContext.xml")
public class ExpenseDAOTest {

    @Autowired
    ExpenseDAO dao;

    Expense e;

    public ExpenseDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        e = new Expense();
    }

    @After
    public void tearDown() {
        dao.delete(e);
    }

    /**
     * Test of getAll method, of class ExpenseDAO.
     */
    @Test
    public void testGetAll() {
        int size = 5;
        for (int i = 0; i < size; i++) {
            dao.create(new Expense());
        }

        List<Expense> result = dao.getAll();
        assertEquals(size, result.size());
        result.forEach(expense -> dao.delete(expense));
    }

    /**
     * Test of create method, of class ExpenseDAO.
     */
    @Test
    public void testCreate() {
        dao.create(e);
        assertEquals(e.getId(), dao.find(e.getId()).getId());
    }

    /**
     * Test of update method, of class ExpenseDAO.
     */
    @Test
    public void testUpdate() {
        dao.create(e);
        assertEquals(e.getId(), dao.find(e.getId()).getId());
        e.setExpense_description("description");
        dao.update(e);
        assertEquals("description", dao.find(e.getId()).getExpense_description());
    }

    /**
     * Test of delete method, of class ExpenseDAO.
     */
    @Test
    public void testDelete() {
        dao.create(e);
        assertEquals(e.getId(), dao.find(e.getId()).getId());
        dao.delete(e);
        assertNull(dao.find(e.getId()));
    }

    /**
     * Test of find method, of class ExpenseDAO.
     */
    @Test
    public void testFind() {
        String s = "testing_find_with_description";
        e.setExpense_description(s);   
        dao.create(e); 
        assertEquals(s, dao.find(s).getExpense_description());
        assertNull(dao.find("not_in_database"));
        
        assertNotNull(dao.find(e.getId()));
        assertNull(dao.find(999));
    }
    
    @Test
    public void testRefresh() {
        dao.create(e);
        Expense expense = e;
        expense.setExpense_amount(500);
        dao.update(expense);
        
        dao.refresh(e);
        assertEquals(expense.getExpense_amount(), e.getExpense_amount(), 0.01);
    }
}
