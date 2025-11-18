import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginView {
    // Existing Users (Login)
    @FXML private TextField loginUsernameField;
    @FXML private PasswordField loginPasswordField;
    @FXML private Button loginButton;

    // New Users (Registration)
    @FXML private TextField regUsernameField;
    @FXML private PasswordField regPasswordField;
    @FXML private ComboBox<String> regClassificationCombo;
    @FXML private TextField regBusinessNameField;
    @FXML private TextField regFirstNameField;
    @FXML private TextField regLastNameField;
    @FXML private TextField regAddressField;
    @FXML private TextField regNextOfKinNameField;
    @FXML private TextField regNextOfKinContactField;
    @FXML private Button registerButton;

    @FXML private Label errorLabel;

    private final Stage stage;
    private final LoginController loginController = new LoginController();

    private Parent root;

    public LoginView(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_view.fxml"));
        loader.setController(this);
        this.root = loader.load();
    }

    public Parent getView() {
        return root;
    }

    @FXML
    private void initialize() {
        errorLabel.setText("");
        if (regClassificationCombo != null) {
            regClassificationCombo.getItems().addAll("Personal", "Business");
            regClassificationCombo.getSelectionModel().select("Personal");
        }
        loginButton.setOnAction(e -> onLogin());
        registerButton.setOnAction(e -> onRegister());
    }

    private void onLogin() {
        try {
            String username = loginUsernameField.getText();
            String password = loginPasswordField.getText();
            if (loginController.isStaff(username, password)) {
                openStaffView();
                return;
            }
            Customer customer = loginController.login(username, password);
            openAccountView(customer);
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void onRegister() {
        try {
            String classification = regClassificationCombo != null ? regClassificationCombo.getSelectionModel().getSelectedItem() : "Personal";
            String businessName = regBusinessNameField != null ? regBusinessNameField.getText() : null;
            Customer customer = loginController.register(
                regUsernameField.getText(),
                regPasswordField.getText(),
                classification,
                businessName,
                regFirstNameField.getText(),
                regLastNameField.getText(),
                regAddressField.getText(),
                regNextOfKinNameField.getText(),
                regNextOfKinContactField.getText()
            );
            openMainMenu(customer);
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void openAccountView(Customer customer) {
        try {
            AccountView accountView = new AccountView(stage, customer);
            stage.getScene().setRoot(accountView.getView());
            stage.setTitle("Fairgrounds Banking - " + customer);
        } catch (Exception ex) {
            errorLabel.setText("Failed to open account view: " + ex.getMessage());
        }
    }

    private void openMainMenu(Customer customer) {
        try {
            MainMenuView menuView = new MainMenuView(stage, customer);
            stage.getScene().setRoot(menuView.getView());
            stage.setTitle("Fairgrounds Banking - Main Menu");
        } catch (Exception ex) {
            errorLabel.setText("Failed to open main menu: " + ex.getMessage());
        }
    }

    private void openStaffView() {
        try {
            StaffView staffView = new StaffView(stage);
            stage.getScene().setRoot(staffView.getView());
            stage.setTitle("Fairgrounds Banking - Staff Portal");
        } catch (Exception ex) {
            errorLabel.setText("Failed to open staff view: " + ex.getMessage());
        }
    }
}
