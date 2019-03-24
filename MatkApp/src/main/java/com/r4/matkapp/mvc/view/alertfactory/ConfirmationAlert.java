/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Eero
 */
public class ConfirmationAlert implements ConfirmationFactory {
    private Alert alert;
    private Alert.AlertType alertType;
    
    public ConfirmationAlert() {
        alertType = Alert.AlertType.CONFIRMATION;
    }
    
    @Override
    public boolean createAlert(String header, String content) {
        alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createAlert(String title, String header, String content) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
    
}
