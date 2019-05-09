package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.controller.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.controller.dbmanager.UserManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Heikki
 */
public class UserDetailSceneController implements Initializable, SceneController {

    private Map<Label, TextField> laTe;
    private String placeholder = "";
    private List<Label> lbDetails = new ArrayList<>();

    @FXML
    private AnchorPane root;

    @FXML
    private Label lbFirstname, lbLastname, lbEmail, lbAddress, lbPhonenumber, lbPostnumber, lbCity, lbCountry, userDetailsLabel, userDetailsFN, userDetailsLN, userDetailsEmail, userDetailsPhoneNo, userDetailsAddress, userDetailsZipCode, userDetailsCity, userDetailsCountry;

    @FXML
    private TextField tfFirstname, tfLastname, tfEmail, tfAddress, tfPhonenumber, tfPostnumber, tfCity, tfCountry;

    private TextField lastFocus;
    private ResourceBundle bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());

    ;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLaTe();
        initlbDetails();
        loadUserDetails();

        userDetailsLabel.setText(bundle.getString("UserDetailsLabel"));
        userDetailsFN.setText(bundle.getString("GenericFNameLabel"));
        userDetailsLN.setText(bundle.getString("GenericLNameLabel"));
        userDetailsEmail.setText(bundle.getString("GenericEmailLabel"));
        userDetailsPhoneNo.setText(bundle.getString("GenericPhoneNoLabel"));
        userDetailsAddress.setText(bundle.getString("UserDetailsAddressLabel"));
        userDetailsZipCode.setText(bundle.getString("UserDetailsZipCode"));
        userDetailsCity.setText(bundle.getString("UserDetailsCity"));
        userDetailsCountry.setText(bundle.getString("UserDetailsCountry"));

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkFocus();
            }
        });
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

    private void saveChanges() {
        User user = DatabaseManager.getLoggedUser();
        user.setFirst_name(lbFirstname.getText());
        user.setLast_name(lbLastname.getText());
        user.setEmail(lbEmail.getText());
        user.setPhone_number(lbPhonenumber.getText());
        user.setStreet_address(lbAddress.getText());
        user.setPost_number(lbPostnumber.getText());
        user.setCity(lbCity.getText());
        user.setCountry(lbCountry.getText());

        DatabaseManager<User> manager = new UserManager();
        manager.update(user);
    }

    //Method to edit user details
    @FXML
    private void clickLabelToShowTf(MouseEvent event) {
        checkFocus();
        Label label = (Label) event.getSource();
        label.setVisible(false);
        TextField tf = laTe.get(label);
        tf.setText(label.getText());
        tf.setVisible(true);

        Platform.runLater(() -> {
            tf.requestFocus();
        });
        lastFocus = tf;

        tf.setOnAction((ActionEvent event1) -> {
            label.setText(tf.getText());
            tf.setVisible(false);
            label.setVisible(true);
            saveChanges();
        });
    }

    @FXML
    private void loadUserDetails() {
        User user = DatabaseManager.getLoggedUser();
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
        toolTip.setText(bundle.getString("UserDetailsTooltip"));
        lbDetails.forEach((label) -> {
            label.setTooltip(toolTip);
        });
    }

    // for firing event of last focused textfield
    private void checkFocus() {
        if (root.getScene().focusOwnerProperty().get() instanceof TextField
                && lastFocus == (TextField) root.getScene().focusOwnerProperty().get()) {

            lastFocus.fireEvent(new ActionEvent());
            lastFocus = null;

        }
    }
}
