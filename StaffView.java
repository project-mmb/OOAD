import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StaffView {
    @FXML private Label staffWelcomeLabel;
    @FXML private Button logoutButton;

    private final Stage stage;
    private Parent root;

    public StaffView(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("staff_view.fxml"));
        loader.setController(this);
        this.root = loader.load();
    }

    public Parent getView() { return root; }

    @FXML
    private void initialize() {
        staffWelcomeLabel.setText("Staff Dashboard");
        logoutButton.setOnAction(e -> doLogout());
    }

    private void doLogout() {
        try {
            LoginView loginView = new LoginView(stage);
            stage.getScene().setRoot(loginView.getView());
            stage.setTitle("Fairgrounds Banking");
        } catch (Exception ex) {
            // If failing to navigate, keep on staff page; optionally show an alert in future
        }
    }
}
