/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Class for creating database SessionFactory.
 * @author Eero
 */
public class DatabaseSession {
    
    private SessionFactory sessionFactory;
    
    /**
     * Parameterless constructor that creates new sessionFactory.
     */
    public DatabaseSession() {
        final StandardServiceRegistry registry = 
                new StandardServiceRegistryBuilder().configure().build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection Failed");
            StandardServiceRegistryBuilder.destroy(registry);
            System.exit(-1);
        }
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void close() {
        sessionFactory.close();
    }
}
