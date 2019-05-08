/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
@Transactional
public class UserDAOTest {

    @Autowired
    UserDAO dao;
    
    static String email;
    private User u;
    
    @BeforeClass
    public static void setUpClass() {
        email = "email@domain.fi"; 
    }
    
    @Before
    public void setUp() {
        u = new User();
        u.setEmail(email);
    }
    
    @After
    public void tearDown() {
        dao.delete(u);
    }
    
    
    /**
     * Test of getAll method, of class UserDAO.
     */
    @Test
    public void testGetAll() {
        dao.create(u);
        dao.create(new User());
        dao.create(new User());
        List<User> result = dao.getAll();
        assertEquals(3, result.size());
    }
    
    
    /**
     * Test of create method, of class UserDAO.
     */
    @Test
    public void testCreate() {
        dao.create(u);
        assertEquals(u.getId(), dao.find(email).getId());
    }
    
    /**
     * Test of delete method, of class UserDAO.
     */
    @Test
    public void testDelete() {
        dao.create(u);
        assertEquals(u.getId(), dao.find(email).getId());
        dao.delete(u);
        assertNull(dao.find(email));
    }
    
    /**
     * Test of update method, of class GroupDAO.
     */
    @Test
    public void testUpdate() {
        dao.create(u);
        assertEquals(u.getId(), dao.find(email).getId());
        u.setCity("City");
        u.setCountry("Country");
        u.setFirst_name("FirstName");
        u.setLast_name("LastName");
        u.setPassword("Pass");
        u.setPhone_number("123456890");
        dao.update(u);
        assertEquals(u.getCountry(), dao.find(email).getCountry());      
    }
    
    @Test
    public void testFind() {
        
        String email = "test@test.com";
        u.setEmail(email);
        dao.create(u);
        assertNotNull(dao.find(u.getId()));
        assertNull(dao.find(999));
        
        assertNotNull(dao.find(email));
        assertNull(dao.find("asdfg"));
           
    }
    
    @Test
    public void testRefresh() {
        dao.create(u);
        User user = u;
        user.setCountry("england");
        dao.update(user);
        
        dao.refresh(u);
        assertEquals(user.getCountry(), u.getCountry());
        
    }
}
