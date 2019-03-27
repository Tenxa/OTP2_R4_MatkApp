/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class RootSceneController implements Initializable {

    @FXML
    HBox menuHBox;
    
    @FXML MenuButton loggedUserBox;

    private boolean groupSceneVisible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUserBox.setText(UserController.getLoggedUser().getEmail());
    }

    @FXML
    public void setGroupScene(ActionEvent event) throws IOException {
        if (groupSceneVisible) {
            menuHBox.getChildren().remove(1);
            menuHBox.setMinWidth(165);
            groupSceneVisible = false;
        } else {
            menuHBox.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/GroupListScene.fxml")));
            menuHBox.setMinWidth(165+150);
            groupSceneVisible = true;
        }

    }

    @FXML
    public void setHomeScene(ActionEvent event) throws IOException {
        BorderPane root = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
        root.setCenter((Node) FXMLLoader.load(getClass().getResource("/fxml/HomeScene.fxml")));
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            UserController.setLoggedUser(null);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage window = MainApp.getWindow();
            window.setResizable(false);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(RootSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
