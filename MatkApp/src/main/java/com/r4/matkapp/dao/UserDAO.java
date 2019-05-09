/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The <code>GroupDAO</code> class provides CRUD operations for <code>Group</code>
 * instances.
 * 
 * @author teemu
 */
public class UserDAO extends AbstractDAO<User> {
    
    @Autowired
    public UserDAO(SessionFactory dbSession) {
        super(dbSession);
    }

    @Override
    public List<User> getAll() {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction(); 
            List<User> result = getSession().createQuery("from User", User.class).list();
            getSession().getTransaction().commit();
            return result.isEmpty() ? null : result;              
        } catch (Exception e) {
            if (getSession().getTransaction() != null)
                getSession().getTransaction().rollback();
            throw e;
        } finally {    
            getSession().close();
        }
    }

    @Override
    public void delete(User u) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            u = (User) getSession().get(User.class, u.getId());
            if (u != null) {
                getSession().delete(u);
                getSession().getTransaction().commit();
            }
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            throw e;
        } finally {
            getSession().close();
        }
    }

    /**
     * Find User by email
     * 
     * @param email User email
     * @return User Object or null if no user found with given email.
     */
    @Override
    public User find(String email) {
        setSession(getSessionFactory().openSession());
       try {
            getSession().beginTransaction();
           Query q = getSession().createQuery("from User where email = :email");
           q.setParameter("email", email);
           List<User> result = q.getResultList();
           User u = null;
           if(result.size() == 1) {
               u = result.get(0);
           } 
            getSession().getTransaction().commit();
           return u;
       } catch (Exception e) {
            if (getSession().getTransaction().isActive()) {
                getSession().getTransaction().rollback();
            }
            throw e;
       } finally {
            getSession().close();
        }
    }
    
    @Override
    public User find(int id) {
        setSession(getSessionFactory().openSession());
        try {
           getSession().beginTransaction();
           User u = getSession().find(User.class, id); 
           getSession().getTransaction().commit();
           return u;
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
