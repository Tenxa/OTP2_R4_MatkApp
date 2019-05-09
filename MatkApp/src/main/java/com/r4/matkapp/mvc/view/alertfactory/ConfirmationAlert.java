/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;

import javafx.scene.control.Alert;

/**
 * @see ConfirmationFactory
 * 
 * @author Eero
 */
public class ConfirmationAlert extends AlertMessageOK {

    /**
     * Creates a Alert with {@link Alert.AlertType.Confirmation);
     */
    public ConfirmationAlert() {
        super(Alert.AlertType.CONFIRMATION);
    }   
}
