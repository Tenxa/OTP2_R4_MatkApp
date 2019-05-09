/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;

import javafx.scene.control.Alert;

/**
 *
 * @author Eero
 */
public class ErrorAlert extends AlertMessageOK {
    
    /**
     * Creates a ErrorAlert with {@link Alert.AlertType.ERROR}.
     */
    public ErrorAlert() {
        super(Alert.AlertType.ERROR);
    }
}
