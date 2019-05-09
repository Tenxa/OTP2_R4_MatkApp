/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Abstract class for Data Access Objects. DAO create(), update() and refresh()
 * methods are implemented here, getAll() delete() and find() methods are
 * implemented in concrete access objects.
 *
 * @author Eero
 * @param <E> datatype
 */
public abstract class AbstractDAO<E> implements DAO<E> {

    protected SessionFactory sessionFactory = null;
    protected Session session = null;

    public AbstractDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
    }

    @Override
    public void create(E entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().saveOrUpdate(entity);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    @Override
    public void update(E entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().update(entity);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    @Override
    public void refresh(E entity) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            getSession().refresh(entity);
            getSession().getTransaction().commit();
        } catch (Exception e) {
            rollback();
            throw e;
        } finally {
            getSession().close();
        }
    }

    private void rollback() {
        if (getSession().getTransaction() != null) {
            getSession().getTransaction().rollback();
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
