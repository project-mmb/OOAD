import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainMenuView {
    @FXML private Label customerLabel;
    @FXML private Button accountButton;
    @FXML private Button closeAccountButton;
    @FXML private Button logoutButton;

    private final Stage stage;
    private final Customer customer;
    private Parent root;

    public MainMenuView(Stage stage, Customer customer) throws Exception {
        this.stage = stage;
        this.customer = customer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        loader.setController(this);
        this.root = loader.load();
    }

    public Parent getView() { return root; }

    @FXML
    private void initialize() {
        customerLabel.setText(customer.toString());
        accountButton.setOnAction(e -> openAccountView());
        closeAccountButton.setOnAction(e -> handleCloseAccount());
        logoutButton.setOnAction(e -> doLogout());
    }

    private void openAccountView() {
        try {
            AccountView accountView = new AccountView(stage, customer);
            stage.getScene().setRoot(accountView.getView());
            stage.setTitle("Fairgrounds Banking - " + customer);
        } catch (Exception ex) {
            showAlert("Unable to open account view: " + ex.getMessage());
        }
    }

    private void handleCloseAccount() {
        showAlert("Account closure requires staff assistance.\nPlease visit a branch or contact support.");
    }

    private void doLogout() {
        try {
            LoginView loginView = new LoginView(stage);
            stage.getScene().setRoot(loginView.getView());
            stage.setTitle("Fairgrounds Banking");
        } catch (Exception ex) {
            showAlert("Unable to logout: " + ex.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fairgrounds Banking");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

