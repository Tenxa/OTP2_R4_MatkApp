/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Access object for User class.
 * 
 * @author teemu
 */
public class UserDAO implements DAO<User> {
    
    private SessionFactory sessionFactory = null;
    private Session session = null;
    
    @Autowired
    public UserDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
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
    public void create(User u) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().saveOrUpdate(u);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
            throw e;
        } finally {
            getSession().close();
        }
    }

    @Override
    public void update(User u) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().update(u);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().getTransaction().rollback();
            }
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
            e.printStackTrace();
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
        } finally {
            getSession().close();
        } 
        return null;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void refresh(User t) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.refresh(t);
        session.getTransaction().commit();
        session.close();  
    }

}
