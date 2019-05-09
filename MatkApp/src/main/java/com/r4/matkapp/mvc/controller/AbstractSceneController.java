/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author Eero
 */
public abstract class AbstractSceneController implements SceneController {

    protected SceneController owner;
    
    /**
     * Create SceneController with a specified owner Controller.
     * @param owner 
     */
    public AbstractSceneController(SceneController owner) {
        this.owner = owner;
    }
    
    /**
     * Loads FXML document with a specified file name and controller.
     * 
     * @param fileName File name without file format. e.g "FXMLDocument".
     * @param controller Controller to be set as owner.
     * @return Root Element from FXML document.
     * @throws IOException File not found.
     */
    protected Parent loadFXML(String fileName, SceneController controller) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fileName + ".fxml"));
        
        if(controller != null) {
            loader.setController(controller);
        }

        return loader.load();
    }
    
    public abstract void update();
}
