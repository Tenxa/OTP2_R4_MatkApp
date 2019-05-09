/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.SecurePassword;
import com.r4.matkapp.mvc.model.*;
import com.r4.matkapp.mvc.controller.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.controller.dbmanager.GroupManager;
import com.r4.matkapp.mvc.controller.dbmanager.UserManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for User data.
 * 
 * @author teemu
 */
public class UserController {

    static final int EMAIL_EXISTS = -1, INCORRECT_DATA = 0, OK = 1;
    /**
     * Adds new user into database.
     * Validates user info and encrypts password before creating.
     * Returns true if creation was successful.
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    public int addUser(String firstName, String lastName, String email, String password) {
        DatabaseManager<User> manager = new UserManager();
        // Validate user input.
        if (ValidateUserInfo.isValid(firstName, lastName, email, password)) {

            SecurePassword sPass = new SecurePassword();
            try {
                // encrypt password
                String userSalt = sPass.getNewSalt();
                String encryptedPassword = sPass.generateEncryptedPassword(password, userSalt);

                User user = new User(firstName, lastName, email, encryptedPassword, userSalt);

                // check if email is not reserved.
                if (manager.find(email) == null) {
                    manager.create(user);
                    return OK;
                } else {
                    return EMAIL_EXISTS;
                }
            } catch (Exception e) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return INCORRECT_DATA;
    }

    /**
     * Create new Group and refresh logged user.
     *
     * @param groupName
     * @return Created Group
     */
    public Group createGroup(String groupName) {
        if(DatabaseManager.getLoggedUser() != null) {

            Group group = new Group(groupName);
            group.getUsers().add(DatabaseManager.getLoggedUser());
            DatabaseManager<Group> manager = new GroupManager();
            DatabaseManager<User> uManager = new UserManager();
            try {
                manager.create(group);
                uManager.refresh(DatabaseManager.getLoggedUser());
                return group;
            } catch (Exception e) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }

    public void sendInvitation(Group group, User user){
        User loggedUser = DatabaseManager.getLoggedUser();
        SendEmail email = new SendEmail();
        String subject = "MatkApp group invitation";
        String text = loggedUser.getFirst_name() + " "+ loggedUser.getLast_name() + " has sent you an invitation.\n"
                + "Group: " + group.getGroup_name() + "\n"
                + "Here is the link: " +group.getInvite();
        email.send(user.getEmail(), subject, text);
    }

    public boolean addUsertoGroup(String invitation, Group group){
        if (DatabaseManager.getLoggedUser() != null && invitation.equals(group.getInvite()) && DatabaseManager.getLoggedUser().getGroup().contains(group) ){
            DatabaseManager.getLoggedUser().addGroup(group);
            DatabaseManager<User> manager = new UserManager();
            manager.update(DatabaseManager.getLoggedUser());
            return true;
        }
        return false;
    }

    /**
     * Authenticates user login input.
     * @param email
     * @param password
     * @return
     */
    public boolean checkLogin(String email, String password) {
        DatabaseManager<User> manager = new UserManager();
        User user = manager.find(email);
        if(user != null) {
            SecurePassword sPass = new SecurePassword();
            try {
                if(sPass.authenticateUser(user, password)) {
                    DatabaseManager.setLoggedUser(user);
                    return true;
                }
            } catch (Exception e) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return false;
    }
}
