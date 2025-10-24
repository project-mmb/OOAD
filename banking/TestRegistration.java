package banking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Test application to verify registration form loads correctly
 */
public class TestRegistration extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Testing registration form loading...");
            
            // Load the registration form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../registration.fxml"));
            Scene scene = new Scene(loader.load());
            
            System.out.println("✓ Registration form loaded successfully!");
            
            primaryStage.setTitle("Test - Registration Form");
            primaryStage.setScene(scene);
            primaryStage.setWidth(800);
            primaryStage.setHeight(700);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("✗ Failed to load registration form: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
