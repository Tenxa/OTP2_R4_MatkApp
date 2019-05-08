/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.Expense;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Eero
 */
public class ExpenseDAO implements DAO<Expense> {

    private SessionFactory sessionFactory = null;
    private Session session = null;

    @Autowired
    public ExpenseDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
    }

    @Override
    public List<Expense> getAll() {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            List<Expense> result = getSession().createQuery("from Expense", Expense.class).list();
            getSession().getTransaction().commit();
            return result.isEmpty() ? null : result;
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
    public void create(Expense e) {
        setSession(getSessionFactory().openSession());
        getSession().beginTransaction();
        getSession().saveOrUpdate(e);
        getSession().getTransaction().commit();
        getSession().close();
    }

    @Override
    public void update(Expense e) {
        setSession(getSessionFactory().openSession());
        getSession().beginTransaction();
        getSession().update(e);
        getSession().getTransaction().commit();
        getSession().close();
    }

    @Override
    public void delete(Expense e) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            e = getSession().get(Expense.class, e.getId());
            if (e != null) {
                getSession().delete(e);
                getSession().getTransaction().commit();
            }
        } catch (Exception ex) {
            if (getSession().getTransaction() != null) {
                getSession().beginTransaction().rollback();
            }
            throw ex;
        } finally {
            getSession().close();
        }
    }

    @Override
    public Expense find(String description) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            Query q = getSession().createQuery("from Expense where expense_description = :expense_description");
            q.setParameter("expense_description", description);
            List<Expense> result = q.getResultList();
            Expense e = null;
            if (result.size() == 1) {
                e = result.get(0);
            }
            getSession().getTransaction().commit();
            return e;
        } catch (Exception ex) {
            if (getSession().getTransaction().isActive()) {
                getSession().beginTransaction().rollback();
            }
            throw ex;
        } finally {
            getSession().close();
        }
    }

    @Override
    public Expense find(int id) {
        setSession(getSessionFactory().openSession());
        try {
            getSession().beginTransaction();
            Expense e = getSession().find(Expense.class, id);
            getSession().getTransaction().commit();
            return e;
        } catch (Exception ex) {
            if (getSession().getTransaction().isActive()) {
                getSession().beginTransaction().rollback();
            }
            throw ex;
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

    @Override
    public void refresh(Expense t) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.refresh(t);
        session.getTransaction().commit();
        session.close();
    }
}
