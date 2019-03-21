/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Group;
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
public class GroupDAOTest {
    
    @Autowired
    GroupDAO dao;
    
    static String grpName;
    private Group g;
    
    public GroupDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        grpName = "TestGroup";
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        g = new Group();
        g.setGroup_name(grpName);
    }
    
    @After
    public void tearDown() {
        dao.delete(g);
    }

    /**
     * Test of getAll method, of class GroupDAO.
     */
    @Test
    public void testGetAll() {
        Group g2 = new Group();
        g2.setGroup_name("Group2");
        
        dao.create(g);
        dao.create(g2);
        
        List<Group> result = dao.getAll();
        assertEquals(2, result.size());
        
    }

    /**
     * Test of create method, of class GroupDAO.
     */
    @Test
    public void testCreate() {
        String invite = g.getInvite();
        dao.create(g);   
        assertEquals(g.getId(), dao.find(invite).getId());
    }
    
    
    /**
     * Test of update method, of class GroupDAO.
     */
    @Test
    public void testUpdate() {
        String invite = g.getInvite();
        dao.create(g);   
        assertEquals(invite, dao.find(invite).getInvite());
        
        String newName = "newName";
        g.setGroup_name(newName);
        dao.update(g);
        assertEquals(newName, dao.find(invite).getGroup_name());
    }

    /**
     * Test of delete method, of class GroupDAO.
     */
    @Test
    public void testDelete() {
        String invite = g.getInvite();
        dao.create(g);   
        assertEquals(invite, dao.find(invite).getInvite());
        dao.delete(g);
        assertNull(dao.find(invite));
    }
   
}
