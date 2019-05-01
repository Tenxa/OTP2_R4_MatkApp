/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.InformationAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class LoginSceneController implements Initializable {
    
    @FXML
    private Text registerFNameLabel, registerLNameLabel, registerEmailLabel, registerPasswordLabel;
    
    @FXML
    private Label loginEmailLabel, loginPasswordLabel, loginOrLabel;
    
    @FXML
    private TextField first_name, last_name, email, password;
    
    @FXML
    private TextField loginEmail, loginPassword;
    
    @FXML
    private StackPane loginPane, createPane;
    
    @FXML Button loginButton, createUserButton, loginSignUpButton, backButton;

    
    private UserController uController;
    
    private AlertFactory alert;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uController = new UserController();
        ResourceBundle bundle = ResourceBundle.getBundle("properties.Login", MainApp.getLocale());
        
        loginEmailLabel.setText(bundle.getString("EmailLabel"));
        loginPasswordLabel.setText(bundle.getString("PasswordLabel"));
        loginButton.setText(bundle.getString("LoginButton"));
        loginOrLabel.setText(bundle.getString("OrLabel"));
        loginSignUpButton.setText(bundle.getString("SignUp"));
        
        registerFNameLabel.setText(bundle.getString("FNameLabel"));
        registerLNameLabel.setText(bundle.getString("LNameLabel"));
        registerEmailLabel.setText(bundle.getString("EmailLabel"));
        registerPasswordLabel.setText(bundle.getString("PasswordLabel"));
        createUserButton.setText(bundle.getString("CreateUser"));
        backButton.setText(bundle.getString("Back"));
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        if (uController.checkLogin(getLoginEmail().getText(), loginPassword.getText())) {
            // SET MAIN SCENE
            
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/fxml/RootScene.fxml").openStream());
            RootSceneController cont = loader.getController();
            Scene scene = new Scene(root);
            Stage window = MainApp.getWindow();
            window.setResizable(true);
            window.setMinWidth(750);
            window.setMinHeight(450);
            window.setScene(scene);
            cont.setHomeScene();
            window.show();
        } else {
            alert = new InformationAlert();
            alert.createAlert("Kirjautuminen epäonnistui!", "Käyttäjä tunnus tai salasana väärin.");
        }
    }
    
    @FXML
    private void createUser(ActionEvent event) {
        if (uController.addUser(first_name.getText(), last_name.getText(),
                email.getText(), password.getText())) {
            AlertFactory alert = new InformationAlert();
            alert.createAlert(null, "Käyttäjän luonti onnistui!");

            first_name.clear();
            last_name.clear();
            email.clear();
            password.clear();
            setLoginPane();
        }

    }

    @FXML
    private void setCreatePane() {
        loginButton.setDefaultButton(false);
        createUserButton.setDefaultButton(true);
        createPane.toFront();
    }
    
    @FXML
    private void setLoginPane() {
        createUserButton.setDefaultButton(false);
        loginButton.setDefaultButton(true);
        loginPane.toFront();
    }

    /**
     * @return the loginEmail
     */
    public TextField getLoginEmail() {
        return loginEmail;
    }
    
    public AlertFactory getAlert() {
        return alert;
    }
    
}
