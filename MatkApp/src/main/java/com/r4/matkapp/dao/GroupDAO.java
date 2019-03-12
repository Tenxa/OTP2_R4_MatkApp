/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * Access object for Group class.
 * 
 * Currently only find() method is functional.
 * 
 * @author Eero
 */

// TODO:
// - loppujen metodien toteutus
// - testit

public class GroupDAO implements DAO {
    private SessionFactory sessionFactory = null;
    private Session session = null;
    
    public GroupDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
    }
    
    @Override
    public List getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Group find(String invite) {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Query q = session.createQuery("from Group where invite = :invite");
            q.setParameter("invite", invite);
            List<Group> result = q.getResultList();
            Group g = null;
            if (result.size() == 1) {
                g = result.get(0);
            }
            session.getTransaction().commit();
            return g;
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.beginTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

}
