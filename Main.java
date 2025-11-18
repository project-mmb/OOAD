import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginView loginView = new LoginView(primaryStage);
        Scene scene = new Scene(loginView.getView());
        if (getClass().getResource("styles.css") != null) {
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        }
        primaryStage.setTitle("Fairgrounds Banking");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
