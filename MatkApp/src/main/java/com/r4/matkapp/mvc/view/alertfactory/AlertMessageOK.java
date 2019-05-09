/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;

import javafx.scene.control.Alert;

/**
 * Abstract class for Alerts with just an OK button for user to click.
 * Only difference to {@link AlertMessage} is {@link javafx.scene.control.Dialog#showAndWait()}
 * method is invoked on creation.
 * @author Eero
 */
public abstract class AlertMessageOK extends AlertMessage {
    
    /**
     * {@inheritDoc}
     */
    public AlertMessageOK(Alert.AlertType type) {
        super(type);
    }
    
    @Override
    public Alert createAlert(String header, String content) {
        Alert alert = super.createAlert(null, header, content);
        alert.showAndWait();
        return alert;
    }
    
    @Override
    public Alert createAlert(String title, String header, String content) {
        Alert alert = super.createAlert(title, header, content);
        alert.showAndWait();
        return alert;
    }
}
