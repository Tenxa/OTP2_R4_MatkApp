/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.InformationAlert;
import com.r4.matkapp.mvc.view.alertfactory.WarningAlert;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    
    @FXML 
    private Button loginButton, createUserButton, loginSignUpButton, backButton;
    
    @FXML
    private ImageView finFlag, engFlag, jpnFlag;
    
    private UserController uController;
    private ResourceBundle bundle;
    private Alert loginAlert;
    
    /**
     * initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uController = new UserController();        
        setTexts();
    }
    
    private void setTexts() {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
        loginEmailLabel.setText(bundle.getString("GenericEmailLabel"));
        loginPasswordLabel.setText(bundle.getString("LoginPasswordLabel"));
        loginButton.setText(bundle.getString("LoginLoginButton"));
        loginOrLabel.setText(bundle.getString("LoginOrLabel"));
        loginSignUpButton.setText(bundle.getString("LoginSignUpLabel"));
        
        registerFNameLabel.setText(bundle.getString("GenericFNameLabel"));
        registerLNameLabel.setText(bundle.getString("GenericLNameLabel"));
        registerEmailLabel.setText(bundle.getString("GenericEmailLabel"));
        registerPasswordLabel.setText(bundle.getString("LoginPasswordLabel"));
        createUserButton.setText(bundle.getString("LoginCreateUser"));
        backButton.setText(bundle.getString("GenericCancelButton"));
    }
    
    @FXML
    public void setLanguage(MouseEvent e) throws IOException {
        if (e.getSource() == finFlag) {
            MainApp.setPerf("fi", "FI");
        } else if (e.getSource() == engFlag) {
            MainApp.setPerf("en", "US");
        } else if (e.getSource() == jpnFlag) {
            MainApp.setPerf("ja", "JP");
        }
        
        setTexts();
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException {
        if (uController.checkLogin(getLoginEmail().getText(), loginPassword.getText())) {
            // SET MAIN SCENE
            
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/fxml/RootScene.fxml").openStream());
            RootSceneController cont = loader.getController();
            Stage window = MainApp.getWindow();
            window.setResizable(true);
            window.setMinWidth(750);
            window.setMinHeight(450);
            BorderPane p = (BorderPane) ((BorderPane) window.getScene().getRoot()).getCenter();
            p.setCenter(root);
            cont.setHomeScene();
            window.show();
        } else {
            AlertFactory alert = new InformationAlert();
            loginAlert = alert.createAlert(bundle.getString("LoginFailureHdr"), bundle.getString("LoginFailureMsg"));
        }
    }
    
    @FXML
    private void createUser(ActionEvent event) {
        int result = uController.addUser(first_name.getText(), last_name.getText(),
                email.getText(), password.getText());
        AlertFactory factory;
        switch (result) {
            case UserController.OK:
                factory = new InformationAlert();
                factory.createAlert(null, bundle.getString("LoginSignUpSuccess"));
                first_name.clear();
                last_name.clear();
                email.clear();
                password.clear();
                setLoginPane();
                break;
            case UserController.INCORRECT_DATA:
                factory = new WarningAlert();
                factory.createAlert(bundle.getString("RegisterFailureHeader"), 
                        bundle.getString("RegisterFailureHelp1") + "\n"
                        + bundle.getString("RegisterFailureHelp2") + "\n"
                        + bundle.getString("RegisterFailureHelp3"));
                break;
            case UserController.EMAIL_EXISTS:
                factory = new WarningAlert(); 
                factory.createAlert(bundle.getString("RegisterFailureHeader"), bundle.getString("RegisterFailureEmailInUse"));
                break;
            default:
                factory = new WarningAlert(); 
                factory.createAlert(bundle.getString("RegisterFailureHeader"), "ERROR");
                break;
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
    
    public Alert getAlert() {
        return loginAlert;
    }
    
}
