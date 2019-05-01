/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.SecurePassword;
import com.r4.matkapp.dao.*;
import com.r4.matkapp.mvc.model.*;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.GroupManager;
import com.r4.matkapp.mvc.model.dbmanager.UserManager;
import javafx.scene.control.Alert;

/**
 * Controller class for User data.
 * @author teemu
 */
// vois miettii tän luokan oikeet tarkotust
// näyttäis vaa database controllerilt
// muutenki aika sekava
public class UserController {
/*
    public static DatabaseSession dbSession = new DatabaseSession();
    static DAO dao = new UserDAO(dbSession.getSessionFactory());
    public static DAO groupDAO = new GroupDAO(dbSession.getSessionFactory());
    public static DAO expenseDAO = new ExpenseDAO(dbSession.getSessionFactory());

    private static User loggedUser = null;
*/
    public UserController() {

    }

    /**
     * Adds new user into database.
     * Validates user info and encrypts password before creating.
     * Returns true if creation was successful.
     * @param first_name
     * @param last_name
     * @param email
     * @param password
     * @return
     */
    public boolean addUser(String first_name, String last_name, String email, String password) {
        DatabaseManager<User> manager = new UserManager();
        // Validate user input.
        if (ValidateUserInfo.isValid(first_name, last_name, email, password)) {

            SecurePassword sPass = new SecurePassword();
            try {
                // encrypt password
                String userSalt = sPass.getNewSalt();
                String encryptedPassword = sPass.generateEncryptedPassword(password, userSalt);

                User user = new User(first_name, last_name, email, encryptedPassword, userSalt);

                // check if email is not reserved.
                if (manager.find(email) == null) {
                    manager.create(user);
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

    /**
     * Create new Group and sets created Group as User group property and
     * updates User. Updating User with group that does not exists creates 
     * new Group row into database.
     *
     * So this method can be used to adding user into existing group.
     * Returns true if successful.
     *
     * @param group_name
     * @return
     */
    public Group createGroup(String group_name) {
        if(DatabaseManager.getLoggedUser() != null) {

            Group group = new Group(group_name);
            group.getUsers().add(DatabaseManager.getLoggedUser());
            DatabaseManager<Group> manager = new GroupManager();
            DatabaseManager<User> uManager = new UserManager();
            try {
                manager.create(group);
                uManager.refresh(DatabaseManager.getLoggedUser());
                return group;
            } catch (Exception e) {

            }
        }
        return null;
    }

    public void sendInvitation(Group group, User user){
        SendEmail email = new SendEmail();
        String subject = "MatkApp group invitation";
        String text = "Group: " + group.getGroup_name() + " has sent you an invitation. Here is the link: " +group.getInvite();
        email.Send(user.getEmail(), subject, text);
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

    public void deleteGroup(Group group) {
        // TODO
    }

    /**
     * Authenticates user login input.
     * @param email
     * @param password
     * @return
     */
    public boolean checkLogin(String email, String password) {
        DatabaseManager<User> manager = new UserManager();
        User user = (User) manager.find(email);
        if(user != null) {
            SecurePassword sPass = new SecurePassword();
            try {
                if(sPass.authenticateUser(user, password)) {
                    DatabaseManager.setLoggedUser(user);
                    return true;
                }
            } catch (Exception e) {

            }
        }
        return false;
    }
}
