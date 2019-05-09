/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;
import javafx.scene.control.Alert;
/**
 * Abstract class for creating different alert types.
 * 
 * @see AlertFactory
 * @see javafx.scene.control.Alert
 * 
 * @author Eero
 */
public abstract class AlertMessage implements AlertFactory {
    
    private Alert.AlertType alertType;
    
    /**
     * Creates a Alert with a specified AlertType.
     * 
     * @param type AlertType
     */
    public AlertMessage(Alert.AlertType type) {
        alertType = type;
    }
    
    @Override
    public Alert createAlert(String header, String content) {
        return createAlert(null, header, content);
    }

    @Override
    public Alert createAlert(String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/styles/RootStyles.css").toExternalForm());
        return alert;
    }  
}
