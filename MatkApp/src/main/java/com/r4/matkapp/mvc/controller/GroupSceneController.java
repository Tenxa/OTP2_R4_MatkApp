/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;

/**
 *
 * @author Eero
 */
public class GroupSceneController implements Initializable{
    
    @FXML TitledPane titledPane;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: 
    }
    
    public void setTitleText(String s) {
        titledPane.setText(s);
    }
    
}
