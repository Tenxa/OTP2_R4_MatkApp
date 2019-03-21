/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Expense;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
@Ignore
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
        for(int i=0; i<size; i++) {
            dao.create(new Expense());
        }
        
        List<Expense> result = dao.getAll();
        assertEquals(size, result.size());
    }

    /**
     * Test of create method, of class ExpenseDAO.
     */
    @Test
    public void testCreate() {
        dao.create(e);        
        assertEquals(e.getId(), dao.getSession().find(Expense.class, e).getId());  
    }

    /**
     * Test of update method, of class ExpenseDAO.
     */
    @Test
    public void testUpdate() {
        dao.create(e);        
        assertEquals(e.getId(), dao.getSession().find(Expense.class, e).getId());
        e.setId(5);
        dao.update(e);
        assertEquals(e.getId(), dao.getSession().find(Expense.class, e).getId());
    }

    /**
     * Test of delete method, of class ExpenseDAO.
     */
    @Test
    public void testDelete() {
        dao.create(e);        
        assertEquals(e.getId(), dao.getSession().find(Expense.class, e).getId());
        dao.delete(e);
        assertNull(dao.getSession().find(Expense.class, e));
    }

    /**
     * Test of find method, of class ExpenseDAO.
     */
    @Test
    public void testFind() {
        // TODO: Expense + ExpenseDAO class implementation
    }

}
