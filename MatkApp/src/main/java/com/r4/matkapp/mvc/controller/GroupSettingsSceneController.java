package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.GroupManager;
import com.r4.matkapp.mvc.model.dbmanager.UserManager;
import com.r4.matkapp.mvc.view.ElementInitor;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.WarningAlert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Eero
 */
public class GroupSettingsSceneController extends AbstractSceneController implements Initializable {

    @FXML
    private TextField groupName, invite;
    
    @FXML
    private Spinner budget;
    
    @FXML
    private Label grpSettingNameLabel, grpSettingBudgetLabel, grpSettingInvLabel;
    
    @FXML
    private Button saveButton, cancelButton;

    private Group group;
    private ResourceBundle bundle;

    public GroupSettingsSceneController(AbstractSceneController owner, Group g) {
        super(owner);
        group = g;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());
        groupName.setText(group.getGroup_name());
        new ElementInitor().init(budget);
        budget.getValueFactory().setValue(group.getBudget());
        invite.setText(group.getInvite());
        
        grpSettingNameLabel.setText(bundle.getString("GroupSettingNameLabel"));
        grpSettingBudgetLabel.setText(bundle.getString("GroupSettingBudgetLabel"));
        grpSettingInvLabel.setText(bundle.getString("GroupSettingInvCode"));
        saveButton.setText(bundle.getString("GroupSettingSaveButton"));
        cancelButton.setText(bundle.getString("GenericCancelButton"));
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
            
            owner.update();
            closeWindow(event);
        } catch (NumberFormatException e) {
            AlertFactory alert = new WarningAlert();
            alert.createAlert(bundle.getString("GenericInvalidInput"), "");
        }
    }

    @Override
    protected void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
