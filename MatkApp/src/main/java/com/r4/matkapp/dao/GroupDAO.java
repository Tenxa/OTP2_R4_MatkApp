/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Group;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Access object for Group class.
 * 
 * 
 * 
 * @author Eero
 */


public class GroupDAO extends AbstractDAO<Group> {

    @Autowired
    public GroupDAO(SessionFactory dbSession) {
        super(dbSession);
    }
    
    @Override
    public List<Group> getAll() {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction(); 
            List<Group> result = getSession().createQuery("from Group", Group.class).list();
            getSession().getTransaction().commit();
            return result.isEmpty() ? null : result;              
        } catch (Exception e) {
            if (getSession().getTransaction() != null)
                getSession().beginTransaction().rollback();
            throw e;
        } finally {    
            getSession().close();
        }
    }

    @Override
    public void delete(Group g) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            g = getSession().get(Group.class, g.getId());
            if (g != null) {
                getSession().delete(g);
                getSession().getTransaction().commit();
            }
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().beginTransaction().rollback();
            }
            throw e;
        } finally {
            getSession().close();
        }
    }

    /**
     * Find Group by invite.
     * 
     * @param invite Group invite String
     * @return Group Object or null if no groups found with given invite
     */
    @Override
    public Group find(String invite) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            Query q = getSession().createQuery("from Group where invite = :invite");
            q.setParameter("invite", invite);
            List<Group> result = q.getResultList();
            Group g = null;
            if (result.size() == 1) {
                g = result.get(0);
            }
            getSession().getTransaction().commit();
            return g;
        } catch (Exception e) {
            if (getSession().getTransaction().isActive()) {
                getSession().beginTransaction().rollback();
            }
            throw e;
        } finally {
            getSession().close();
        }
    }
    
    @Override
    public Group find(int id) {
        setSession(getSessionFactory().openSession());
        try {
           getSession().beginTransaction();
           Group g = getSession().find(Group.class, id); 
           getSession().getTransaction().commit();
           return g;
        } catch(Exception ex) {
            if (getSession().getTransaction() != null) {
                getSession().beginTransaction().rollback();
            }
            throw ex;
        } finally {
            getSession().close();
        } 
    }
}
