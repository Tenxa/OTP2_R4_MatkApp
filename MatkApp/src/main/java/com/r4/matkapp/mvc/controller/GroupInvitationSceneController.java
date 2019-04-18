/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
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
            
    
    private UserController uController;
    
    private Group activeGroup;
    private AltGroupSceneController parentController;
    private AlertFactory alert;

    protected GroupInvitationSceneController(AltGroupSceneController parentController, Group activeGroup) {
        this.activeGroup = activeGroup;
        this.parentController = parentController;
    }
    
    //En oo päässyt kokeilemaan näitä, koska tuo GroupInvitationScene ei aukea.
    
    @FXML
    private void sendInvitation(ActionEvent event){
        User addUser = uController.getUserbyEmail(email.getText());
        if (addUser != null){
            Group group = activeGroup;
            
            if (group != null){
                uController.sendInvitation(group, addUser);
                
                String content = "Osoitteeseen: " + addUser.getEmail();
                alert = new InformationAlert();
                alert.createAlert("Liittymis koodi on lähetetty", content);
                closeWindow(event);
            } else {
                alert = new WarningAlert();
                alert.createAlert("Liittymis koodin lähetys epäonnistui!", null);
            }
        }
    }
    
    @FXML
    private void closeWindow(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uController = new UserController();
    }

    public TextField getEmail() {
        return email;
    }
    
    
}
