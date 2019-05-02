/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Eero
 */
public class ShadowEffect { 
 
  public Scene getShadowScene(Parent p) { 
    Scene scene; 
    BorderPane outer = new BorderPane();
    outer.setCenter(p);
    outer.setPadding(new Insets(10.0d)); 
    outer.setBackground( new Background(new BackgroundFill( Color.rgb(0,0,0,0), new CornerRadii(0), new 
      Insets(0)))); 
 
    p.setEffect(new DropShadow()); 
    ((BorderPane)p).setBackground( new Background(new BackgroundFill( Color.WHITE, new CornerRadii(0), new Insets(0) 
    ))); 
 
    scene = new Scene( outer ); 
    scene.setFill( Color.rgb(0,255,0,0) ); 
    return scene; 
  } 
} 
