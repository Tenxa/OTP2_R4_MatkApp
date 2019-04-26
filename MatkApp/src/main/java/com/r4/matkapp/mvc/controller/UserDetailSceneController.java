package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.r4.matkapp.mvc.model.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Heikki
 */
public class UserDetailSceneController implements Initializable {

    private Map<Label, TextField> laTe;
    private String placeholder = "Syötä tieto";
    private List<Label> lbDetails = new ArrayList<>();
    
    
    @FXML
    private Label lbFirstname, lbLastname, lbEmail, lbAddress, lbPhonenumber, lbPostnumber, lbCity, lbCountry;
    
    @FXML
    private TextField tfFirstname, tfLastname, tfEmail, tfAddress, tfPhonenumber, tfPostnumber, tfCity, tfCountry;
    
    @FXML
    private Button saveButton, resetButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLaTe();
        initlbDetails();
        loadUserDetails();
    }    
    
    //La = Label, Te = TextField
    private void initLaTe() {
        laTe = new HashMap();
        laTe.put(lbFirstname, tfFirstname);
        laTe.put(lbLastname, tfLastname);
        laTe.put(lbEmail, tfEmail);
        laTe.put(lbAddress, tfAddress);
        laTe.put(lbPhonenumber, tfPhonenumber);
        laTe.put(lbPostnumber, tfPostnumber);
        laTe.put(lbCity, tfCity);
        laTe.put(lbCountry, tfCountry);
    }
    
    private void initlbDetails() {
        lbDetails.add(lbFirstname);
        lbDetails.add(lbLastname);
        lbDetails.add(lbEmail);
        lbDetails.add(lbAddress);
        lbDetails.add(lbPostnumber);
        lbDetails.add(lbPhonenumber);
        lbDetails.add(lbCity);
        lbDetails.add(lbCountry);
    }
    
    //Method to edit user details
    @FXML
    private void clickLabelToShowTf(MouseEvent event) {
        Label label = (Label)event.getSource();
        label.setVisible(false);
        TextField tf = laTe.get(label);
        tf.setText(label.getText());
        tf.setVisible(true);
        tf.setOnAction((ActionEvent event1) -> {
            label.setText(tf.getText());
            tf.setVisible(false);
            label.setVisible(true);
        });
    }
    
    @FXML
    private void saveChanges() {
        User user = UserController.getLoggedUser();
        user.setFirst_name(lbFirstname.getText());
        user.setLast_name(lbLastname.getText());
        user.setEmail(lbEmail.getText());
        user.setPhone_number(lbPhonenumber.getText());
        user.setStreet_address(lbAddress.getText());
        user.setPost_number(lbPostnumber.getText());
        user.setCity(lbCity.getText());
        user.setCountry(lbCountry.getText());
        UserController.dao.update(user);
    }
    
    @FXML
    private void loadUserDetails() {
        User user = UserController.getLoggedUser();
        lbFirstname.setText(user.getFirst_name());
        lbLastname.setText(user.getLast_name());
        lbEmail.setText(user.getEmail());
        lbPhonenumber.setText(user.getPhone_number());
        lbAddress.setText(user.getStreet_address());
        lbPostnumber.setText(user.getPost_number());
        lbCity.setText(user.getCity());
        lbCountry.setText(user.getCountry());
        
        lbDetails.stream().filter((label) -> (label.getText() == null)).forEachOrdered((label) -> {
            label.setText(placeholder);
        });
    }
    
    @FXML
    private void setupToolTip() {
        Tooltip toolTip = new Tooltip();
        toolTip.setText("Klikkaa muokataksesi!");
        lbDetails.forEach((label) -> {
            label.setTooltip(toolTip);
        });
    }
}
