package com.r4.matkapp;

import com.r4.matkapp.mvc.controller.UserController;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

// TODO:
// - Ryhmään liittyminen (invite)
// - PopUp message siistiminen / parempi toteutus.

public class FXMLController implements Initializable {

    UserController uController;

    @FXML
    private Button users_button, groups_button, mainpage_button, mainpage2_button;

    @FXML
    private TextField first_name, last_name, email, password;

    @FXML
    private TextField login_email, login_password;

    @FXML
    private TextField create_grp_name, join_grp_invite, add_grp_member; // TODO: invite
    
    @FXML
    private TextField group_inv_code;

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

    @FXML
    private void login(ActionEvent event) {
        if (uController.checkLogin(
                login_email.getText(), login_password.getText())) {

            // kirjautminen ok tästä etiäpäi
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("OK!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText("Kirjautuminen epäonnistui!");
            alert.setContentText("Käyttäjä tunnus tai salasana väärin.");
            alert.showAndWait();
        }

    }

    @FXML
    private void createGroup(ActionEvent event) {
        Group result = uController.createGroup(create_grp_name.getText());
        if (result != null) {
            String content = "Liitymis koodi: " + result.getInvite();
            popCopyableMessage(Alert.AlertType.INFORMATION, "Ryhmän luonti onnistui", content);
        } else {
            popMessage(Alert.AlertType.WARNING, "Ryhmän luonti epäonnistui!", null);
        }
    }
    
    private void addGroupMember(ActionEvent event){
        User addUser = uController.getUserbyEmail(email.getText());
        
        Group group = uController.getGroupbyInvitation(group_inv_code.getText());
        if (group != null){
            uController.sendInvitation(group, addUser);
            String content = "Osoitteeseen: " + addUser.getEmail();
            popMessage(Alert.AlertType.INFORMATION, "Liittymis koodi on lähetetty", content);
        } else {
            popMessage(Alert.AlertType.WARNING, "Liittymis koodin lähetys epäonnistui!", null);
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        uController = new UserController();

    }

    private void popMessage(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(header);
        alert.setContentText(content);  
        alert.showAndWait();
    }
    
    private void popCopyableMessage(Alert.AlertType type, String header, String content){
        TextField text = new TextField(content);
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(text, 0, 0);
        
        Alert alert = new Alert(type);
        alert.setTitle(null);
        alert.setHeaderText(header);
        alert.getDialogPane().setContent(text);
        alert.showAndWait();
    }
}
