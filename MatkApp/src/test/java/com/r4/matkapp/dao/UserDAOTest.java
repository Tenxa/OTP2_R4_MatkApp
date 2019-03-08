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
@ContextConfiguration(locations = "classpath:hibernateTestContext.xml")
@Transactional
public class UserDAOTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void testCreate() {
        Session session = sessionFactory.getCurrentSession();

        User u = new User();
        u.setId(123);
        u.setFirst_name("Teppo");
        u.setLast_name("Testi");

        String grpName = "TestGroup";
        Group g = new Group();
        g.setGroup_name(grpName);

        u.setGroup(g);
        String invite = g.getInvite();
        session.saveOrUpdate(u);
        User findU = session.find(User.class, 123);

        assertNotNull(findU);
        assertEquals(grpName, findU.getGroup().getGroup_name());
        assertEquals(invite, findU.getGroup().getInvite());
        
    }

    @Test
    public void testDelete() {
        Session session = sessionFactory.getCurrentSession();

        User u = new User();
        u.setId(123);
        u.setFirst_name("Teppo");
        u.setLast_name("Testi");

        session.saveOrUpdate(u);
        User findU = session.find(User.class, 123);
        assertNotNull(findU);
        session.delete(u);
        findU = session.find(User.class, 123);
        assertNull(findU);
    }
    
    @Test
    public void testUpdate() {
        Session session = sessionFactory.getCurrentSession();

        User u = new User();
        u.setId(123);
        u.setFirst_name("Teppo");
        u.setLast_name("Testi");
        session.saveOrUpdate(u);
        
        User findU = session.find(User.class, 123);
        assertNotNull(findU);
        
        u.setFirst_name("Pekka");
        
        session.update(u);
        findU = session.find(User.class, 123);
        
        assertEquals("Pekka", findU.getFirst_name());
    }
    

}
