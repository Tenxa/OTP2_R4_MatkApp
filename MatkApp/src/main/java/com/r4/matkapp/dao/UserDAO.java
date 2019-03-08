/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author teemu
 */
public class UserDAO implements DAO {
    
    @Autowired
    private SessionFactory sessionFactory = null;
    private Session session = null;
    
    public UserDAO(SessionFactory dbSession) {
        sessionFactory = dbSession;
    }

    @Override
    public List<User> getAll() {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction(); 
            List<User> result = session.createQuery("from User", User.class).list();
            session.getTransaction().commit();
            return result.isEmpty() ? null : result;              
        } catch (Exception e) {
            if (session.getTransaction() != null)
                session.beginTransaction().rollback();
            throw e;
        } finally {    
            session.close();
        }
    }

    @Override
    public void create(Object t) {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate((User) t);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Object t) {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update((User) t);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Object t) {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            User u = (User) t;
            u = (User) session.get(User.class, u.getId());
            if (u != null) {
                session.delete(u);
                session.getTransaction().commit();
            }
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }


    @Override
    public User find(String email) {
       session = sessionFactory.openSession();
       try {
           session.beginTransaction();
           Query q = session.createQuery("from User where email = :email");
           q.setParameter("email", email);
           List<User> result = q.getResultList();
           User u = null;
           if(result.size() == 1) {
               u = result.get(0);
           } 
           session.getTransaction().commit();
           return u;
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
