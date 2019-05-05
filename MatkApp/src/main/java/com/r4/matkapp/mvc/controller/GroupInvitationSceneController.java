/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.UserManager;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.InformationAlert;
import com.r4.matkapp.mvc.view.alertfactory.WarningAlert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author teemu
 */
public class GroupInvitationSceneController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private Label emailLabel;       
    @FXML
    private Button sendButton, cancelButton;
    
    private UserController uController;
    private ResourceBundle bundle;
    
    private Group activeGroup;
    private AltGroupSceneController parentController;
    private AlertFactory alert;

    protected GroupInvitationSceneController(AltGroupSceneController parentController, Group activeGroup) {
        this.activeGroup = activeGroup;
        this.parentController = parentController;
    }
    
    /* Sähköposti osoite, mikä syötetään käyttöliittymään
        täytyy olla jollakin käyttäjällä olemassa*/
    @FXML
    private void inviteMember(ActionEvent event){
        DatabaseManager<User> manager = new UserManager();
        User addUser = manager.find(email.getText());
        if (addUser != null){
            
            if (activeGroup != null){
                
                uController.sendInvitation(activeGroup, addUser);
                
                alert = new InformationAlert();
                alert.createAlert(bundle.getString("GroupInvitePopupSuccess") + addUser.getEmail() + "", null);
                closeWindow(event);
                
            } else {                
                alert = new WarningAlert();
                alert.createAlert(bundle.getString("GroupInvitePopupFailure"), null);
            }
        } else{
            alert = new WarningAlert();
            alert.createAlert(bundle.getString("GroupInvitePopupFailure"), null);
        }
    }
    
    @FXML
    private void closeWindow(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
        uController = new UserController();
        
        emailLabel.setText(bundle.getString("GenericEmailLabel"));
        sendButton.setText(bundle.getString("GroupInviteSendButton"));
        cancelButton.setText(bundle.getString("GenericCancelButton"));
    }

    public TextField getEmail() {
        return email;
    }
    
    
}
