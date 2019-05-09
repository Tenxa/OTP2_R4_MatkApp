/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;

import javafx.scene.control.Alert;

/**
 * Factory interface for creating javafx alerts
 * 
 * @see javafx.scene.control.Alert
 * @see javafx.scene.control.Dialog
 * 
 * @author Eero
 */
public interface AlertFactory {
    
    /**
     * Creates an Alert with given contextText.
     * 
     * @param header Header text
     * @param content Content text
     * @return {@link javafx.scene.control.Alert}
     */
    abstract public Alert createAlert(String header, String content);

    /**
     * Creates an Alert with given contextText.
     * 
     * @param title Title text
     * @param header Header text
     * @param content Content text
     * @return {@link javafx.scene.control.Alert}
     */
    abstract public Alert createAlert(String title, String header, String content);
}
