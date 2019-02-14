/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.dao.DAO;
import com.r4.matkapp.User;
import com.r4.matkapp.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author teemu
 */
public class UserDAO implements DAO {

    private SessionFactory sessionFactory = null;
    private Session session = null;

    public UserDAO() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Connection Failed");
            StandardServiceRegistryBuilder.destroy(registry);
            System.exit(-1);
        }
    }

    @Override
    public List getAll() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object t) {
        session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate((User) t);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(session.getTransaction() != null) {
                session.beginTransaction().rollback();
            }
            throw e;        
        } finally {
            session.close();
        }
    }

    @Override
    public void update(Object t, String[] params) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
    @Override
    public void close() {
        sessionFactory.close();
    }

}
