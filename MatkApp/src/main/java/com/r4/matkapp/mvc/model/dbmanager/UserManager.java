/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model.dbmanager;

import com.r4.matkapp.mvc.model.User;

/**
 *
 * @author Eero
 */
public class UserManager extends DatabaseManager<User> {

    @Override
    public void create(User entity) {
        USER_DAO.create(entity);
    }

    @Override
    public void refresh(User entity) {
        USER_DAO.refresh(entity);
    }

    @Override
    public void update(User entity) {
        USER_DAO.update(entity);
    }

    @Override
    public void delete(User entity) {
        USER_DAO.delete(entity);
    }

    @Override
    public User find(int id) {
        return USER_DAO.find(id);
    }

    @Override
    public User find(String col) {
        return USER_DAO.find(col);
    }
    
}
