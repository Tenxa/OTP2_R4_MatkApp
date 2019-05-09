/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller.dbmanager;

import com.r4.matkapp.dao.ExpenseDAO;
import com.r4.matkapp.mvc.model.Expense;

/**
 * Class for managing Expense Database Access Object
 *
 * @author Eero
 */
public class ExpenseManager extends DatabaseManager<Expense> {
    
    /**
     * Creates a Manager with a {@link ExpenseDAO}.
     */
    public ExpenseManager() {
        super(new ExpenseDAO(dbSession.getSessionFactory()));
    }
}
