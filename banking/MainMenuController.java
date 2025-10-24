package banking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

/**
 * Controller for the main menu screen
 */
public class MainMenuController {
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label accountInfoLabel;
    
    @FXML
    private Label balanceLabel;
    
    @FXML
    private Button viewBalanceButton;
    
    @FXML
    private Button depositButton;
    
    @FXML
    private Button withdrawButton;
    
    @FXML
    private Button transactionsButton;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Label statusLabel;
    
    private Account currentAccount;
    private Bank bank;
    
    public void setAccount(Account account) {
        this.currentAccount = account;
        updateDisplay();
    }
    
    public void setBank(Bank bank) {
        this.bank = bank;
    }
    
    private void updateDisplay() {
        if (currentAccount != null) {
            welcomeLabel.setText("Welcome, " + currentAccount.getCustomer().getCustomerName() + "!");
            accountInfoLabel.setText("Account: " + currentAccount.getAccountID() + " | Type: " + currentAccount.getAccountType());
            balanceLabel.setText(String.format("$%.2f", currentAccount.getAccountBalance()));
            statusLabel.setText("Account loaded successfully");
        }
    }
    
    @FXML
    private void handleViewBalance() {
        if (currentAccount != null) {
            balanceLabel.setText(String.format("$%.2f", currentAccount.getAccountBalance()));
            statusLabel.setText("Balance refreshed");
        }
    }
    
    @FXML
    private void handleDeposit() {
        if (currentAccount != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Deposit Funds");
            dialog.setHeaderText("Enter amount to deposit");
            dialog.setContentText("Amount:");
            
            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        boolean success = currentAccount.depositFunds(amount);
                        if (success) {
                            balanceLabel.setText(String.format("$%.2f", currentAccount.getAccountBalance()));
                            statusLabel.setText("Deposit successful: $" + String.format("%.2f", amount));
                        } else {
                            statusLabel.setText("Deposit failed: Invalid amount");
                        }
                    } else {
                        statusLabel.setText("Deposit failed: Amount must be positive");
                    }
                } catch (NumberFormatException e) {
                    statusLabel.setText("Deposit failed: Invalid number format");
                }
            });
        }
    }
    
    @FXML
    private void handleWithdraw() {
        if (currentAccount != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Withdraw Funds");
            dialog.setHeaderText("Enter amount to withdraw");
            dialog.setContentText("Amount:");
            
            dialog.showAndWait().ifPresent(amountStr -> {
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        boolean success = currentAccount.withdrawFunds(amount);
                        if (success) {
                            balanceLabel.setText(String.format("$%.2f", currentAccount.getAccountBalance()));
                            statusLabel.setText("Withdrawal successful: $" + String.format("%.2f", amount));
                        } else {
                            statusLabel.setText("Withdrawal failed: Insufficient funds or invalid amount");
                        }
                    } else {
                        statusLabel.setText("Withdrawal failed: Amount must be positive");
                    }
                } catch (NumberFormatException e) {
                    statusLabel.setText("Withdrawal failed: Invalid number format");
                }
            });
        }
    }
    
    @FXML
    private void handleViewTransactions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transactions.fxml"));
            Scene scene = new Scene(loader.load());
            
            TransactionController controller = loader.getController();
            controller.setAccount(currentAccount);
            controller.loadTransactions();
            
            Stage stage = (Stage) transactionsButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Transaction History");
        } catch (Exception e) {
            statusLabel.setText("Failed to open transaction history");
        }
    }
    
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Scene scene = new Scene(loader.load());
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Login");
        } catch (Exception e) {
            // Handle error
        }
    }
}
