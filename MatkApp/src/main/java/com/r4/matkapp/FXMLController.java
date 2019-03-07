package com.r4.matkapp;

import com.r4.matkapp.mvc.controller.UserController;
import com.r4.matkapp.mvc.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class FXMLController implements Initializable {

    UserController uController;

    @FXML
    private Button users_button, groups_button, mainpage_button, mainpage2_button;

    @FXML
    private TextField first_name, last_name, email, password;

    @FXML
    private StackPane users_pane, groups_pane, mainpage_pane;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == users_button) {
            users_pane.toFront();
        } else if (event.getSource() == groups_button) {
            groups_pane.toFront();
        } else if (event.getSource() == mainpage_button) {
            mainpage_pane.toFront();
        } else if (event.getSource() == mainpage2_button) {
            mainpage_pane.toFront();
        }
    }

    @FXML
    private void createUser(ActionEvent event) {
        // TODO Salasanan salaus (UserController hoitaa)
        if (uController.addUser(first_name.getText(), last_name.getText(),
                email.getText(), password.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Käyttäjän luonti onnistui!");
            alert.showAndWait();
            first_name.clear();
            last_name.clear();
            email.clear();
            password.clear();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        uController = new UserController();

    }
}
