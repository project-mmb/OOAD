package banking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * Controller for the login screen
 */
public class LoginController {
    @FXML
    private TextField customerIdField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Label errorLabel;
    
    private Bank bank;
    private Customer currentCustomer;
    private Account currentAccount;
    
    public void initialize() {
        // Initialize bank with sample data
        bank = new Bank();
        setupSampleData();
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    private void setupSampleData() {
        // Create sample customers with passwords
        Customer customer1 = new Customer("John", "Doe", 1001, "123 Main St", "password123");
        Customer customer2 = new Customer("Jane", "Smith", 1002, "456 Oak Ave", "password456");
        
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        
        // Create sample accounts
        Savings savings1 = new Savings("SAV001", 1, 1000.0, customer1, 0.02);
        Investment investment1 = new Investment("INV001", 1, 5000.0, customer1, 5000.0, 0.05);
        Cheque cheque1 = new Cheque("CHQ001", 1, 2500.0, customer2, "Smith Corp", "CORP001", 1000.0);
        
        bank.addAccount(savings1);
        bank.addAccount(investment1);
        bank.addAccount(cheque1);
        
        // Add some sample transactions
        savings1.depositFunds(500.0);
        investment1.withdrawFunds(200.0);
        cheque1.depositFunds(1000.0);
    }
    
    @FXML
    private void handleLogin() {
        try {
            String password = passwordField.getText().trim();
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            
            if (bank.authenticateCustomer(customerId, password)) {
                currentCustomer = bank.findCustomer(customerId);
                // Get the first account for this customer (or show account selection)
                var customerAccounts = bank.getCustomerAccounts(customerId);
                if (!customerAccounts.isEmpty()) {
                    currentAccount = customerAccounts.get(0); // Use first account
                    openMainMenu();
                } else {
                    showError("No accounts found for this customer.");
                }
            } else {
                showError("Invalid credentials. Please check your Customer ID and Password.");
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid Customer ID (numbers only).");
        } catch (Exception e) {
            showError("Login failed. Please try again.");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
    
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
            Scene scene = new Scene(loader.load());
            
            RegistrationController controller = loader.getController();
            controller.setBank(bank);
            
            Stage stage = (Stage) registerButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Customer Registration");
            stage.setWidth(800);
            stage.setHeight(700);
        } catch (Exception e) {
            showError("Failed to open registration form.");
        }
    }
    
    private void openMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(loader.load());
            
            MainMenuController controller = loader.getController();
            controller.setAccount(currentAccount);
            controller.setBank(bank);
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Main Menu");
        } catch (Exception e) {
            showError("Failed to open main menu.");
        }
    }
}
