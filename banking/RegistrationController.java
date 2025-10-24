package banking;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller for the customer registration screen
 */
public class RegistrationController {
    @FXML
    private TextField firstNameField;
    
    @FXML
    private TextField lastNameField;
    
    @FXML
    private TextField idNumberField;
    
    @FXML
    private TextField customerIdField;
    
    @FXML
    private TextArea addressField;
    
    @FXML
    private TextField employmentField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField confirmPasswordField;
    
    @FXML
    private ComboBox<String> accountTypeComboBox;
    
    @FXML
    private Button createAccountButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private Label errorLabel;
    
    private Bank bank;
    private int nextCustomerId = 1003; // Start from 1003 since we have 1001, 1002
    
    public void initialize() {
        // Initialize account type combo box
        accountTypeComboBox.setItems(FXCollections.observableArrayList(
            "Personal Account",
            "Business Account"
        ));
        
        // Auto-generate customer ID
        customerIdField.setText(String.valueOf(nextCustomerId));
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
        // Find the highest existing customer ID
        int maxId = 1000;
        for (Customer customer : bank.getCustomers()) {
            if (customer.getCustomerId() > maxId) {
                maxId = customer.getCustomerId();
            }
        }
        nextCustomerId = maxId + 1;
        customerIdField.setText(String.valueOf(nextCustomerId));
    }
    
    @FXML
    private void handleCreateAccount() {
        try {
            // Validate input
            if (!validateInput()) {
                return;
            }
            
            // Create new customer
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String idNumber = idNumberField.getText().trim();
            String address = addressField.getText().trim();
            String employment = employmentField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            String accountType = accountTypeComboBox.getValue();
            
            // Validate password
            if (!password.equals(confirmPassword)) {
                showError("Passwords do not match.");
                return;
            }
            
            Customer newCustomer = new Customer(firstName, lastName, nextCustomerId, address, password);
            bank.addCustomer(newCustomer);
            
            // Create account based on type
            Account newAccount = createAccountForCustomer(newCustomer, accountType);
            bank.addAccount(newAccount);
            
            // Show success message
            showStatus("Account created successfully! Customer ID: " + nextCustomerId + 
                      ", Account ID: " + newAccount.getAccountID());
            
            // Clear form for next registration
            clearForm();
            
        } catch (Exception e) {
            showError("Failed to create account: " + e.getMessage());
        }
    }
    
    private boolean validateInput() {
        if (firstNameField.getText().trim().isEmpty()) {
            showError("First name is required.");
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            showError("Last name is required.");
            return false;
        }
        if (idNumberField.getText().trim().isEmpty()) {
            showError("ID number is required.");
            return false;
        }
        if (addressField.getText().trim().isEmpty()) {
            showError("Address is required.");
            return false;
        }
        if (employmentField.getText().trim().isEmpty()) {
            showError("Employment information is required.");
            return false;
        }
        if (passwordField.getText().trim().isEmpty()) {
            showError("Password is required.");
            return false;
        }
        if (confirmPasswordField.getText().trim().isEmpty()) {
            showError("Please confirm your password.");
            return false;
        }
        if (accountTypeComboBox.getValue() == null) {
            showError("Please select an account type.");
            return false;
        }
        return true;
    }
    
    private Account createAccountForCustomer(Customer customer, String accountType) {
        String accountId;
        int branch = 1;
        double initialBalance = 0.0;
        
        if ("Personal Account".equals(accountType)) {
            // Create a Savings account for personal customers
            accountId = "SAV" + String.format("%03d", nextCustomerId);
            return new Savings(accountId, branch, initialBalance, customer, 0.02);
        } else {
            // Create a Cheque account for business customers
            accountId = "CHQ" + String.format("%03d", nextCustomerId);
            String companyName = employmentField.getText().trim();
            String companyId = "CORP" + String.format("%03d", nextCustomerId);
            return new Cheque(accountId, branch, initialBalance, customer, companyName, companyId, 1000.0);
        }
    }
    
    private void clearForm() {
        firstNameField.clear();
        lastNameField.clear();
        idNumberField.clear();
        addressField.clear();
        employmentField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        accountTypeComboBox.setValue(null);
        nextCustomerId++;
        customerIdField.setText(String.valueOf(nextCustomerId));
        hideMessages();
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            
            LoginController controller = loader.getController();
            controller.setBank(bank);
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Login");
            stage.setWidth(600);
            stage.setHeight(500);
        } catch (Exception e) {
            showError("Failed to return to login screen.");
        }
    }
    
    private void showStatus(String message) {
        statusLabel.setText(message);
        statusLabel.setVisible(true);
        errorLabel.setVisible(false);
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        statusLabel.setVisible(false);
    }
    
    private void hideMessages() {
        statusLabel.setVisible(false);
        errorLabel.setVisible(false);
    }
}
