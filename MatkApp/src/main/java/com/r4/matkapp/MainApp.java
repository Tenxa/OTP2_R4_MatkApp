package com.r4.matkapp;

import com.r4.matkapp.mvc.controller.UserController;
import com.r4.matkapp.mvc.model.dbmanager.DatabaseManager;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainApp extends Application {
    
    private static Stage window;
    private static Locale locale;
    
    public static Stage getWindow() {
        return window;
    }

    public static void setLocale(Locale l) {
        locale = l;
    }
    
    public static Locale getLocale() {
        return locale;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/fxml/LoginScene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("MatkApp");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                DatabaseManager.close();
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
        window = stage;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if(prefs.get("language", null) == null) {
            prefs.put("language", "en");
            prefs.put("region", "US");
        }
        String lang = prefs.get("language", "root");
        String region = prefs.get("region", "root");   
        setLocale(new Locale(lang, region));
    }
}
