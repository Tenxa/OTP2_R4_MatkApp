/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Abstract class for Data Access Objects. 
 * DAO create(), update() and refresh() methods are implemented here, getAll()
 * delete() and find() methods are implemented in concrete access objects.
 * 
 * @author Eero
 * @param <Entity> Entity datatype
 */
public abstract class AbstractDAO<Entity> implements DAO<Entity> {

    protected SessionFactory sessionFactory = null;
    protected Session session = null;

    public AbstractDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
    }

    @Override
    public void create(Entity entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().saveOrUpdate(entity);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            if (getSession().getTransaction() != null) {
                getSession().beginTransaction().rollback();
            }
            throw e;
        } finally {
            getSession().close();
        }
    }

    @Override
    public void update(Entity entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().update(entity);
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
    public void refresh(Entity entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().update(entity);
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
}
