package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.GroupManager;
import com.r4.matkapp.mvc.view.ElementInitor;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.WarningAlert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class GroupSettingsSceneController implements Initializable, SceneController {

    @FXML
    TextField groupName, invite;
    
    @FXML
    Spinner budget;

    private Group group;
    private AltGroupSceneController parentController;

    protected GroupSettingsSceneController(AltGroupSceneController c, Group g) {
        parentController = c;
        group = g;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        groupName.setText(group.getGroup_name());
        new ElementInitor().init(budget);
        invite.setText(group.getInvite());
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    private void updateGroup(ActionEvent event) {
        try {
            group.setGroup_name(groupName.getText());
            group.setBudget((double) budget.getValue());
            
            DatabaseManager<Group> manager = new GroupManager();
            manager.update(group);
            
            parentController.updateGroupData();
            closeWindow(event);
        } catch (NumberFormatException e) {
            AlertFactory alert = new WarningAlert();
            alert.createAlert("Virheellinen syöte", "");
        }
    }
}
