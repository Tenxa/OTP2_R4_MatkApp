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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class RootSceneController extends AbstractSceneController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private MenuButton loggedUserBox;
    @FXML
    private VBox groupList;
    @FXML
    private MenuItem userDetailsButton, userLogOutButton;
    @FXML
    private Button naviHomeButton, naviNewGrpBtn, naviJoinGrpBtn;

    private Set<Group> userGroups;
    private ResourceBundle bundle;

    private Button selected;

    public RootSceneController() {
        super(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("properties.default", MainApp.getLocale());

        userDetailsButton.setText(bundle.getString("UserDetailsDropDown"));
        userLogOutButton.setText(bundle.getString("UserLogOut"));
        naviHomeButton.setText(bundle.getString("NaviHome"));
        naviNewGrpBtn.setText(bundle.getString("NaviNewGroup"));
        naviJoinGrpBtn.setText(bundle.getString("NaviJoinGroup"));

        loggedUserBox.setText(DatabaseManager.getLoggedUser().getEmail());
        generateGroupList();
    }

    @FXML
    public void setHomeScene() throws IOException {
        Parent fxmlRoot = loadFXML("HomeScene", new HomeSceneController());
        setCenter(fxmlRoot);
        if (selected != null) {
            selected.setStyle(null);
            selected.getGraphic().setStyle(null);
        }
        selected = naviHomeButton;
        selected.setStyle("-fx-background-color: #4f6c9b;\n"
            + "-fx-text-fill: white;");
    }

    @FXML
    private void setDetailScene() throws IOException {
        Parent fxmlRoot = loadFXML("UserDetailScene", new UserDetailSceneController());
        setCenter(fxmlRoot);
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            DatabaseManager.setLoggedUser(null);
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScene.fxml"));
            //Scene scene = new Scene(root);
            Stage window = MainApp.getWindow();
            window.setMinHeight(400);
            window.setMinWidth(600);
            window.setResizable(false);
            //window.setScene(scene);
            BorderPane p = (BorderPane) ((BorderPane)window.getScene().getRoot()).getCenter();
            p.setCenter(root);
            window.sizeToScene();
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

    @Override
    protected void update() {
        updateGroupList();
    }

    public void updateGroupList() {
        //Node n = groupList.getChildren().get(0);
        //Node n1 = groupList.getChildren().get(1);
        groupList.getChildren().clear();
        //groupList.getChildren().addAll(n, n1);
        generateGroupList();
    }

    private void generateGroupList() {
        DatabaseManager<User> manager = new UserManager();
        manager.refresh(DatabaseManager.getLoggedUser());
        userGroups = DatabaseManager.getLoggedUser().getGroup();
        List<Group> groups = new ArrayList<>(userGroups);
        Collections.sort(groups);
        for (Group g : groups) {
            createGroupButton(g);
        }
    }

    private void createGroupButton(Group g) {
        Button b = new Button(g.getGroup_name());
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.GROUP);
        b.setOnAction((ActionEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AltGroupScene.fxml"));
                loader.setController(new AltGroupSceneController(this, g));
                setCenter(loader.load());
                if (selected != null) {
                    selected.setStyle(null);
                    selected.getGraphic().setStyle(null);
                }
                selected = b;
                selected.setStyle("-fx-background-color: #4f6c9b;\n"
                        + "-fx-text-fill: white;");
                icon.setStyle("-fx-fill: white;");
            } catch (IOException ex) {

            }
        });   
        icon.setSize("30");
        icon.getStyleClass().add("icon");
        b.setGraphic(icon);
        b.setGraphicTextGap(6);
        b.setAlignment(Pos.BASELINE_LEFT);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setMinHeight(40);
        groupList.getChildren().add(b);
    }

    // kysy tiedot ryhmälle eli vain ryhmän nimi -> palauttaa luodun ryhmän
    // vois joskus toteuttaa paremmin
    private Group inputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("NewGroupHeaderLabel"));
        dialog.setContentText(bundle.getString("NewGroupHelpText"));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            UserController u = new UserController();
            Group g = u.createGroup(result.get());
            if (g.getInvite() != null) {
                AlertFactory f = new InformationAlert();
                f.createAlert(null, bundle.getString("NewGroupSuccess"), g.getInvite());
                return g;
            }
        }
        return null;
    }

    @FXML
    private void JoinGroup() {
        if (JoinGroupInputDialog() != null) {
            updateGroupList();
        }
    }

    private Group JoinGroupInputDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(bundle.getString("JoinGroupHeader"));
        dialog.setContentText(bundle.getString("JoinGroupMsg"));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            DatabaseManager<Group> gManager = new GroupManager();
            Group g = gManager.find(result.get());
            if (g != null) {
                User us = DatabaseManager.getLoggedUser();
                g.getUsers().add(us);
                gManager.update(g);
                AlertFactory f = new InformationAlert();
                f.createAlert(null, bundle.getString("JoinGroupSuccess"), g.getGroup_name());
                return g;
            }
        }
        return null;
    }

    public void setCenter(Node node) {
        root.setCenter(node);
    }

}
