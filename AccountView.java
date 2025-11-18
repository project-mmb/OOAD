import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AccountView {
    @FXML private Label customerLabel;
    @FXML private ListView<Account> accountsList;
    @FXML private TextField amountField;
    @FXML private Button depositBtn;
    @FXML private Button withdrawBtn;
    @FXML private Button interestBtn;
    @FXML private Button logoutButton;

    @FXML private ComboBox<String> openTypeCombo;
    @FXML private ComboBox<String> classificationCombo;
    @FXML private TextField initialDepositField;
    @FXML private TextField businessNameField;
    @FXML private TextField employerNameField;
    @FXML private TextField employerAddressField;
    @FXML private Button openAccountBtn;
    @FXML private Label errorLabel;

    private final Stage stage;
    private final Customer customer;
    private final AccountController controller = new AccountController();

    private Parent root;
    private final ObservableList<Account> accountItems = FXCollections.observableArrayList();

    public AccountView(Stage stage, Customer customer) throws Exception {
        this.stage = stage;
        this.customer = customer;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("account_view.fxml"));
        loader.setController(this);
        this.root = loader.load();
    }

    public Parent getView() { return root; }

    @FXML
    private void initialize() {
        errorLabel.setText("");
        customerLabel.setText(customer.toString());
        accountItems.setAll(controller.listAccounts(customer));
        accountsList.setItems(accountItems);

        openTypeCombo.getItems().addAll("Savings", "Investment", "Cheque");
        openTypeCombo.getSelectionModel().selectFirst();
        classificationCombo.getItems().addAll("Personal", "Business");
        classificationCombo.getSelectionModel().selectFirst();

        classificationCombo.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            boolean isBiz = "Business".equalsIgnoreCase(n);
            businessNameField.setDisable(!isBiz);
            businessNameField.setPromptText(isBiz ? "Business legal name" : "Not required for Personal");
        });
        // initialize state
        boolean isBizInit = "Business".equalsIgnoreCase(classificationCombo.getSelectionModel().getSelectedItem());
        businessNameField.setDisable(!isBizInit);

        depositBtn.setOnAction(e -> doDeposit());
        withdrawBtn.setOnAction(e -> doWithdraw());
        interestBtn.setOnAction(e -> doInterest());
        openAccountBtn.setOnAction(e -> doOpenAccount());
        if (logoutButton != null) {
            logoutButton.setOnAction(e -> doLogout());
        }
    }

    private Account selectedAccountOrError() {
        Account acc = accountsList.getSelectionModel().getSelectedItem();
        if (acc == null) throw new IllegalStateException("Select an account");
        return acc;
    }

    private double parseAmount(TextField field) {
        try {
            return Double.parseDouble(field.getText());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Enter a valid amount");
        }
    }

    private void doDeposit() {
        try {
            Account acc = selectedAccountOrError();
            double amt = parseAmount(amountField);
            controller.deposit(acc, amt);
            accountsList.refresh();
            errorLabel.setText("");
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void doWithdraw() {
        try {
            Account acc = selectedAccountOrError();
            double amt = parseAmount(amountField);
            controller.withdraw(acc, amt);
            accountsList.refresh();
            errorLabel.setText("");
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void doInterest() {
        try {
            Account acc = selectedAccountOrError();
            controller.payMonthlyInterest(acc);
            accountsList.refresh();
            errorLabel.setText("");
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void doOpenAccount() {
        try {
            String type = openTypeCombo.getSelectionModel().getSelectedItem();
            String classStr = classificationCombo.getSelectionModel().getSelectedItem();
            AccountClassification cls = "Business".equalsIgnoreCase(classStr) ? AccountClassification.BUSINESS : AccountClassification.PERSONAL;
            String businessName = cls == AccountClassification.BUSINESS ? businessNameField.getText() : null;
            double initial = 0.0;
            if (!initialDepositField.getText().isBlank()) {
                initial = parseAmount(initialDepositField);
            }
            Account newAcc;
            switch (type) {
                case "Savings":
                    newAcc = controller.openSavings(customer, initial, cls, businessName);
                    break;
                case "Investment":
                    newAcc = controller.openInvestment(customer, initial, cls, businessName);
                    break;
                case "Cheque":
                    String emp = employerNameField.getText();
                    String addr = employerAddressField.getText();
                    newAcc = controller.openCheque(customer, initial, cls, businessName, emp, addr);
                    break;
                default:
                    throw new IllegalStateException("Unknown type");
            }
            accountItems.add(newAcc);
            accountsList.getSelectionModel().select(newAcc);
            errorLabel.setText("");
        } catch (Exception ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    private void doLogout() {
        try {
            LoginView loginView = new LoginView(stage);
            stage.getScene().setRoot(loginView.getView());
            stage.setTitle("Fairgrounds Banking");
        } catch (Exception ex) {
            errorLabel.setText("Failed to logout: " + ex.getMessage());
        }
    }
}
