package banking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main application class for the Professional Banking System
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            
            // Apply CSS styling
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            
            // Set up the primary stage
            primaryStage.setTitle("Fairgrounds Finance");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setWidth(600);
            primaryStage.setHeight(500);
            
            // Center the window on screen
            primaryStage.centerOnScreen();
            
            // Show the application
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start the application: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
