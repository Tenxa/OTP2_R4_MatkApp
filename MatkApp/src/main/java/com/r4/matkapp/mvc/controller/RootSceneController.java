/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.MainApp;
import com.r4.matkapp.mvc.model.Group;
import com.r4.matkapp.mvc.model.User;
import com.r4.matkapp.mvc.model.dbmanager.GroupManager;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import com.r4.matkapp.mvc.model.dbmanager.UserManager;
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
    BorderPane root;
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
        loggedUserBox.setText(DatabaseManager.getLoggedUser().getEmail());
        generateGroupList();
    }

    @FXML
    public void setHomeScene() {
        Node fxmlRoot = loadFXML("HomeScene", new HomeSceneController());
        setCenter(fxmlRoot);
    }

    @FXML
    private void setDetailScene() {
        Node fxmlRoot = loadFXML("UserDetailScene", new UserDetailSceneController());
        setCenter(fxmlRoot);
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            DatabaseManager.setLoggedUser(null);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage window = MainApp.getWindow();
            window.setMinHeight(400);
            window.setMinWidth(600);
            window.sizeToScene();
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
        Node n1 = groupList.getChildren().get(1);
        groupList.getChildren().clear();
        groupList.getChildren().addAll(n, n1);
        generateGroupList();
    }

    private void generateGroupList() {
        DatabaseManager<User> manager = new UserManager();
        manager.update(DatabaseManager.getLoggedUser());
        userGroups = DatabaseManager.getLoggedUser().getGroup();
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AltGroupScene.fxml"));
                loader.setController(new AltGroupSceneController(ctrl, g));
                setCenter(loader.load());
            } catch (IOException ex) {
                
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

    @FXML
    private void JoinGroup(){
        if (JoinGroupInputDialog() != null){
            updateGroupList();
        }
    }

    private Group JoinGroupInputDialog(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Liity ryhmään");
        dialog.setContentText("Syötä ryhmän liittymis koodi");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            DatabaseManager<Group> gManager = new GroupManager();
            Group g = gManager.find(result.get());
            if (g != null){
                User us = DatabaseManager.getLoggedUser();
                us.addGroup(g);
                DatabaseManager<User> uManager = new UserManager();
                uManager.update(us);
                AlertFactory f = new InformationAlert();
                f.createAlert(null, "Ryhmään liittyminen onnistui", g.getGroup_name());
                return g;
            }
        }
        return null;
    }

    public void setCenter(Node node) {
        root.setCenter(node);
    }

    private Node loadFXML(String fileName, SceneController controller) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/"+ fileName +".fxml"));
            loader.setController(controller);
            return loader.load();
        } catch (IOException ex) {
            Logger.getLogger(RootSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
