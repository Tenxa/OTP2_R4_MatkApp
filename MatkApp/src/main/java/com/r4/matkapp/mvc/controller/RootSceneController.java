/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Eero
 */
public class RootSceneController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void setGroupScene(ActionEvent event) throws IOException {
        BorderPane root = (BorderPane)((Node)event.getSource()).getScene().getRoot();
        root.setCenter((Node) FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml")));
    }
    
    @FXML
    public void setHomeScene(ActionEvent event) throws IOException {
        BorderPane root = (BorderPane)((Node)event.getSource()).getScene().getRoot();
        root.setCenter((Node) FXMLLoader.load(getClass().getResource("/fxml/HomeScene.fxml")));
    }
    
    @FXML
    public void logout() {
        // TODO: 
    }
    
}
