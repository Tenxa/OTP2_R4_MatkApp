/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view.alertfactory;
import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import javafx.scene.control.Alert;
/**
 *
 * @author Eero
 */
public abstract class AlertMessage implements AlertFactory {
    
    private Alert alert;
    
    Alert.AlertType alertType;
    
    public AlertMessage() {
        alertType = Alert.AlertType.INFORMATION; 
    }
    
    @Override
    public void createAlert(String header, String content) {
        alert = new Alert(alertType);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void createAlert(String title, String header, String content) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public Alert getAlert() {
        return alert;
    }
    
}
