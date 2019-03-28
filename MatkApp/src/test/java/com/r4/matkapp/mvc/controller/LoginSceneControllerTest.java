/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import com.r4.matkapp.mvc.view.alertfactory.AlertFactory;
import com.r4.matkapp.mvc.view.alertfactory.AlertMessage;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Eero
 */
public class LoginSceneControllerTest extends ApplicationTest{
    
    Parent parent;
    Scene scene;
    LoginSceneController controller;
    
    public LoginSceneControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of initialize method, of class LoginSceneController.
     */
    @Test
    public void testInitialize() {
        
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScene.fxml"));
        
        parent = loader.load();
        controller = loader.getController();
        
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();    
    }
    
    
    @Test
    public void testLogin() {
        TextField emailField = (TextField) parent.lookup("#loginEmail");
        TextField passField = (TextField) parent.lookup("#loginPassword");
        emailField.setText("teppo@testi.net");
        
        passField.setText("pass");
        assertEquals("teppo@testi.net", emailField.getText());   
        assertEquals("pass", passField.getText());
        
        clickOn("#loginButton");
        AlertMessage alert = (AlertMessage)controller.getAlert();
        
        assertEquals("Kirjautuminen epäonnistui!", "Käyttäjä tunnus tai salasana väärin.", alert.getAlert().getContentText());
        
        
        
    }
  
    
}
