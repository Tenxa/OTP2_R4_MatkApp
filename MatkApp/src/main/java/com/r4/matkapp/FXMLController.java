package com.r4.matkapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class FXMLController implements Initializable {
    
    @FXML
    private Button users_button, groups_button, mainpage_button, mainpage2_button;
    
    @FXML
    private StackPane users_pane, groups_pane, mainpage_pane;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == users_button) {
            users_pane.toFront();
        }
        else if (event.getSource() == groups_button) {
            groups_pane.toFront();
        }
        else if (event.getSource() == mainpage_button) {
            mainpage_pane.toFront();
        }
        else if (event.getSource() == mainpage2_button) {
            mainpage_pane.toFront();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
