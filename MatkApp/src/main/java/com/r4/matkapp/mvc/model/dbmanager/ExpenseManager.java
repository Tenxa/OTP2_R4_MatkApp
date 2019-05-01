/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model.dbmanager;

import com.r4.matkapp.mvc.model.Expense;

/**
 *
 * @author Eero
 */
public class ExpenseManager extends DatabaseManager<Expense>{

    @Override
    public void create(Expense entity) {
        EXPENSE_DAO.create(entity);
    }

    @Override
    public void refresh(Expense entity) {
        EXPENSE_DAO.refresh(entity);
    }

    @Override
    public void update(Expense entity) {
        EXPENSE_DAO.update(entity);
    }

    @Override
    public void delete(Expense entity) {
        EXPENSE_DAO.delete(entity);
    }

    @Override
    public Expense find(int id) {
        return EXPENSE_DAO.find(id);
    }

    @Override
    public Expense find(String col) {
        return EXPENSE_DAO.find(col);
    }
    
}
