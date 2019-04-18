package com.r4.matkapp.mvc.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Heikki
 */
public class UserDetailSceneController implements Initializable {

    private Map<Label, TextField> laTe;
    Label jarmo;
    TextField t;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initLaTe();
    }    
    
    //La = Label, Te = TextField
    private void initLaTe() {
        laTe = new HashMap();
        laTe.put(jarmo, t);
    }
    
}
