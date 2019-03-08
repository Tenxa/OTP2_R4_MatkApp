/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.SecurePassword;
import com.r4.matkapp.dao.*;
import com.r4.matkapp.mvc.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author teemu
 */

// TODO:
// - Ryhmään liityinen (invite)
// - Ryhmän poistaminen
// - 


public class UserController {

    private static User selectedUser;
    public static DatabaseSession dbSession = new DatabaseSession();
    static DAO dao = new UserDAO(dbSession.getSessionFactory());
    
    private User loggedUser;

    public UserController() {
        loggedUser = null;
    }

    public boolean addUser(String first_name, String last_name, String email, String password) {
        
        // Validate user input.
        if (ValidateUserInfo.isValid(first_name, last_name, email, password)) {
            
            SecurePassword sPass = new SecurePassword();
            try {
                // encrypt password
                String userSalt = sPass.getNewSalt();
                String encryptedPassword = sPass.generateEncryptedPassword(password, userSalt);
                
                User user = new User(first_name, last_name, email, encryptedPassword, userSalt);
                
                // check if email is not reserved.
                if (dao.find(email) == null) {
                    dao.create(user);
                    return true;
                } else {
                    // tän vois joskus siirtää/ toteuttaa FXMLControlleris
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setHeaderText("Käyttäjän luonti epäonnistui!");
                    alert.setContentText("Sähköposti on jo käytössä.");
                    alert.showAndWait();
                }
            } catch (Exception e) {

            }

        } else {
            // tän vois joskus siirtää/ toteuttaa FXMLControlleris
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Käyttäjän luonti epäonnistui!");
            alert.setContentText("Tietoja puuttuu tai ne ovat virheelliset.\n"
                    + "Salasanan pituus pitää olla 4 - 14 merkkiä.\n"
                    + "Sähköposti formaattia 'nimi@domain.fi'.");
            alert.showAndWait();
        }

        return false;
    }

    public void deleteUser(User user) {
        dao.delete(user);
        // TODO
    }
    
    // Create new group if logged in and user is not in any group.
    // Sets created group as User group property and updates User.
    // Updating User with group that does not exists creates
    // new Group row into database.
    public String createGroup(String group_name) {
        if(loggedUser != null) {
            if(loggedUser.getGroup() != null) return null; // ei voi tehä grp jos on jo
            Group group = new Group(group_name);
            loggedUser.setGroup(group); 
            try {
               dao.update(loggedUser);
               return group.getInvite();
            } catch (Exception e) {
                
            }
        }  
        return null;
    }

    public void deleteGroup(Group group) {
       // TODO
    }
    
    public boolean checkLogin(String email, String password) {
        User user = (User) dao.find(email);
        if(user != null) {
            SecurePassword sPass = new SecurePassword();
            try {
                if(sPass.authenticateUser(user, password)) {
                    loggedUser = user;
                    return true;
                }          
            } catch (Exception e) {
                
            }
        }
        return false;
    }
}
