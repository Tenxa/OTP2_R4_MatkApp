/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller;

import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.assertions.api.Assertions;

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
    public void testSetCreatePane() {
        TextField tf = (TextField) parent.lookup("#loginEmail");
        tf.setText("teppo@testi.net");
        assertEquals("teppo@testi.net", tf.getText());                         
    }
  
    
}
