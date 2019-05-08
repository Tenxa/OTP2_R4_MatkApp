/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model.dbmanager;

import com.r4.matkapp.dao.DatabaseSession;
import com.r4.matkapp.dao.UserDAO;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.dao.DAO;

/**
 *
 * @author Eero
 * @param <Entity>
 */
public abstract class DatabaseManager<Entity> {

    protected static DatabaseSession dbSession = new DatabaseSession();

    protected DAO<Entity> dao;

    public DatabaseManager(DAO d) {
        dao = d;
    }

    public static void close() {
        dbSession.close();
    }

    private static User LOGGED_USER = null;

    public static User getLoggedUser() {
        return LOGGED_USER;
    }

    public static void setLoggedUser(User loggedUser) {
        LOGGED_USER = loggedUser;
    }

    public static void updateLoggedUser() {
        DAO<User> userDao = new UserDAO(dbSession.getSessionFactory());
        userDao.refresh(LOGGED_USER);
    }

    /**
     * @see DAO#create(java.lang.Object)
     * @param entity
     */
    public void create(Entity entity) {
        dao.create(entity);
    }

    /**
     * @see DAO#refresh(java.lang.Object)
     * @param entity
     */
    public void refresh(Entity entity) {
        dao.refresh(entity);
    }

    /**
     * @see DAO#update(java.lang.Object)
     * @param entity
     */
    public void update(Entity entity) {
        dao.update(entity);
    }

    /**
     * @see DAO#delete(java.lang.Object)
     * @param entity
     */
    public void delete(Entity entity) {
        dao.delete(entity);
    }

    /**
     * @see DAO#find(int)
     * @param id
     * @return
     */
    public Entity find(int id) {
        return dao.find(id);
    }

    /**
     * @see DAO#find(java.lang.String)
     * @param col
     * @return
     */
    public Entity find(String col) {
        return dao.find(col);
    }
}
