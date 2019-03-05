/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;
import com.r4.matkapp.dao.*;
import com.r4.matkapp.mvc.model.*;
/**
 *
 * @author teemu
 */
public class UserController {
    private static User selectedUser;
    private static DAO dao = new UserDAO();
    
    public UserController() {
        
    }
    
    public void addUser(String first_name, String last_name, String email, String password){
        User user = new User(first_name, last_name, email, password);
        dao.create(user);
    }
    
    public void deleteUser(User user){
        dao.delete(user);
    }
}
