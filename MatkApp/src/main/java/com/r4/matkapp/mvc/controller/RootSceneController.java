/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.InformationAlert;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class RootSceneController implements Initializable {

    @FXML
    HBox menuHBox;
    @FXML
    MenuButton loggedUserBox;
    @FXML
    VBox groupList;

    Set<Group> userGroups;

    private boolean groupSceneVisible = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUserBox.setText(UserController.getLoggedUser().getEmail());
        generateGroupList();
    }

    // vanha ei käytös
    @FXML
    public void setGroupScene(ActionEvent event) throws IOException {
        if (groupSceneVisible) {
            menuHBox.getChildren().remove(1);
            menuHBox.setMinWidth(165);
            groupSceneVisible = false;
        } else {
            menuHBox.getChildren().add((Node) FXMLLoader.load(getClass().getResource("/fxml/GroupListScene.fxml")));
            menuHBox.setMinWidth(165 + 150);
            groupSceneVisible = true;
        }
    }

    @FXML
    public void setHomeScene() {
        try {
            BorderPane root = (BorderPane) MainApp.getWindow().getScene().getRoot();
            root.setCenter((Node) FXMLLoader.load(getClass().getResource("/fxml/HomeScene.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(RootSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            UserController.setLoggedUser(null);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage window = MainApp.getWindow();
            window.setWidth(600);
            window.setHeight(400);
            window.setResizable(false);
            window.setScene(scene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(RootSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void createNewGroup() {
        if (inputDialog() != null) {
            updateGroupList();
        }
    }

    // en jaksanu miettii, aika sekava ja turhan paljon kyselyjä tietokantaan
    // aka parennttavaa..
    public void updateGroupList() {
        Node n = groupList.getChildren().get(0);
        groupList.getChildren().clear();
        groupList.getChildren().add(n);
        generateGroupList();
    }

    private void generateGroupList() {
        UserController.updateLoggedUser();
        userGroups = UserController.getLoggedUser().getGroup();
        List<Group> groups = new ArrayList<>(userGroups);
        Collections.sort(groups);
        for (Group g : groups) {
            createGroupButton(g);
        }
    }

    private void createGroupButton(Group g) {
        RootSceneController ctrl = this;
        Button b = new Button(g.getGroup_name());
        b.setOnAction((ActionEvent event) -> {
            BorderPane root = (BorderPane) ((Node) event.getSource()).getScene().getRoot();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AltGroupScene.fxml"));
                loader.setController(new AltGroupSceneController(ctrl, g.getId()));
                root.setCenter(loader.load());
            } catch (IOException ex) {
                Logger.getLogger(GroupListSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.GROUP);
        icon.setSize("16");
        b.setGraphic(icon);
        b.setAlignment(Pos.BASELINE_LEFT);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setMinHeight(40);
        groupList.getChildren().add(b);
    }

    // kysy tiedot ryhmälle eli vain ryhmän nimi -> palauttaa luodun ryhmän
    // vois joskus toteuttaa paremmin
    private Group inputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Luo uusi ryhmä");
        dialog.setContentText("Syötä ryhmän nimi:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            UserController u = new UserController();
            Group g = u.createGroup(result.get());
            if (g.getInvite() != null) {
                AlertFactory f = new InformationAlert();
                f.createAlert(null, "Ryhmän luonti onnistui!", g.getInvite());
                return g;
            }
        }
        return null;
    }

}
