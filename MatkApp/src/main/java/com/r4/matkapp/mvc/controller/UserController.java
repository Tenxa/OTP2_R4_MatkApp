/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.dao.*;
import com.r4.matkapp.mvc.model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author teemu
 */
public class UserController {

    private static User selectedUser;
    public static DAO dao = new UserDAO();

    public UserController() {

    }

    public boolean addUser(String first_name, String last_name, String email, String password) {
        // TODO salasanan salaus / validointi

        if (!(first_name.equals("")
                || last_name.equals("")
                || email.equals("")
                || password.equals(""))) {
            User user = new User(first_name, last_name, email, password);
            try {
                if (dao.find(email) == null) {
                    dao.create(user);
                    return true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(null);
                    alert.setHeaderText("Käyttäjän luonti epäonnistui!");
                    alert.setContentText("Sähköposti on jo käytössä.");
                    alert.showAndWait();
                }
            } catch (Exception e) {

            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(null);
            alert.setHeaderText("Käyttäjän luonti epäonnistui!");
            alert.setContentText("Tietoja puuttuu.");
            alert.showAndWait();
        }

        return false;
    }

    public void deleteUser(User user) {
        dao.delete(user);
    }

    public void addGroup(String group_name) {
        Group group = new Group(group_name);
        dao.create(group);
    }

    public void deleteGroup(Group group) {
        dao.delete(group);
    }
}
