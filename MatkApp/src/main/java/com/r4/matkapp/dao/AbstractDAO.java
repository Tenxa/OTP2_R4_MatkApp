/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Eero
 * @param <D>
 */
public abstract class AbstractDAO<D> implements DAO<D> {
    protected SessionFactory sessionFactory = null;
    protected Session session = null;
    
    public AbstractDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
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
