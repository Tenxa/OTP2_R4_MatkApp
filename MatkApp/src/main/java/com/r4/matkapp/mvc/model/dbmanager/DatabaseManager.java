/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model.dbmanager;

import com.r4.matkapp.dao.DAO;
import com.r4.matkapp.dao.DatabaseSession;
import com.r4.matkapp.dao.ExpenseDAO;
import com.r4.matkapp.dao.GroupDAO;
import com.r4.matkapp.dao.UserDAO;
import com.r4.matkapp.mvc.model.Expense;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;

/**
 *
 * @author Eero
 */
public abstract class DatabaseManager<D> {
    
    private static DatabaseSession dbSession = new DatabaseSession();
    
    public static void close() {
        dbSession.close();
    }
    
    protected static DAO<User> USER_DAO = new UserDAO(dbSession.getSessionFactory());
    protected static DAO<Group> GROUP_DAO = new GroupDAO(dbSession.getSessionFactory());
    protected static DAO<Expense> EXPENSE_DAO = new ExpenseDAO(dbSession.getSessionFactory());

    private static User LOGGED_USER = null;
    
    public static User getLoggedUser() {
        return LOGGED_USER;
    }
    
    public static void setLoggedUser(User loggedUser) {
        LOGGED_USER = loggedUser;
    } 

    public abstract void create(D entity);
    public abstract void refresh(D entity);
    public abstract void update(D entity);
    public abstract void delete(D entity);
    public abstract D find(int id);
    public abstract D find(String col);
}
