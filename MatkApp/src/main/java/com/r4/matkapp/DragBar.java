/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp;

import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Eero
 */
public class DragBar {

    private double xOffset = 0;
    private double yOffset = 0;

    public HBox create(Stage stage) {
        HBox bar = new HBox();
        bar.setMinHeight(26);
        bar.setMaxHeight(26);
        bar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        bar.styleProperty().setValue("-fx-background-color: #32486b");

        Button exitButton = createExitButton();
        Button maxButton = createMaximizeButton();
        Button minButton = createMinimizeButton();

        bar.getChildren().addAll(exitButton, maxButton, minButton);

        //grab your root here
        bar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (ResizeHelper.getCanMove()) {
                    if (MainApp.getWindow().isMaximized()) {
                        maxButton.fire();
                    }
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }

            }
        });
        HBox textBox = new HBox();
        textBox.setMaxWidth(Double.MAX_VALUE);
        textBox.setMaxHeight(26);
        textBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        textBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textBox, Priority.ALWAYS);
        //ImageView image = new ImageView(new Image("/pictures/matkapp_text.png"));
        //image.fitHeightProperty().set(26);
        Label label = new Label("MatkApp");
        label.getStyleClass().add("dragBarText");
        textBox.getChildren().add(label);
        bar.getChildren().add(textBox);
        
        return bar;
    }

    private Button createExitButton() {
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_CLOSE);
        icon.setFill(Color.WHITE);
        icon.setSize("16");

        Button button = createButton(icon);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseManager.close();
                Platform.exit();
                System.exit(0);
            }
        });

        return button;
    }

    private Button createMaximizeButton() {
        MaterialDesignIconView iconMaximize = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MAXIMIZE);
        MaterialDesignIconView iconRestore = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_RESTORE);
        iconMaximize.setFill(Color.WHITE);
        iconRestore.setFill(Color.WHITE);
        iconMaximize.setSize("16");
        iconRestore.setSize("16");

        Button button = createButton(iconMaximize);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage window = MainApp.getWindow();
                if (window.isMaximized()) {
                    window.setMaximized(false);
                    window.setResizable(true);
                    setStageCenter(window);
                    button.setGraphic(iconMaximize);
                } else {
                    window.setMaximized(true);
                    customBounds(window);
                    button.setGraphic(iconRestore);
                }
                window.show();
            }
        });
        return button;
    }

    private Button createMinimizeButton() {
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.WINDOW_MINIMIZE);
        icon.setFill(Color.WHITE);
        icon.setSize("16");

        Button button = createButton(icon);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainApp.getWindow().setIconified(true);
            }
        });

        return button;
    }

    private Button createButton(Node icon) {
        Button button = new Button();
        button.setGraphic(icon);
        button.getStylesheets().add("styles/DragBarButton.css");
        return button;
    }

    private void customBounds(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX() - 10);
        stage.setY(bounds.getMinY() - 10);
        stage.setWidth(bounds.getWidth() + 20);
        stage.setHeight(bounds.getHeight() + 20);

    }

    private void setStageCenter(Stage stage) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        final double offset = 400;
        stage.setX(bounds.getWidth()/2 - offset);
        stage.setY(bounds.getHeight()/2 - offset);
    }
}
