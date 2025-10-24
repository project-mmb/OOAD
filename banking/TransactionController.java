package banking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the transaction history screen
 */
public class TransactionController {
    @FXML
    private Label accountInfoLabel;
    
    @FXML
    private ScrollPane transactionScrollPane;
    
    @FXML
    private VBox transactionContainer;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label statusLabel;
    
    private Account currentAccount;
    
    public void setAccount(Account account) {
        this.currentAccount = account;
        if (account != null) {
            accountInfoLabel.setText("Account: " + account.getAccountID() + " | Type: " + account.getAccountType());
        }
    }
    
    public void loadTransactions() {
        if (currentAccount != null) {
            transactionContainer.getChildren().clear();
            
            var transactions = currentAccount.viewTransactions();
            if (transactions.isEmpty()) {
                Label noTransactionsLabel = new Label("No transactions found");
                noTransactionsLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 14px;");
                transactionContainer.getChildren().add(noTransactionsLabel);
            } else {
                for (Transaction transaction : transactions) {
                    Label transactionLabel = createTransactionLabel(transaction);
                    transactionContainer.getChildren().add(transactionLabel);
                }
            }
            
            statusLabel.setText("Loaded " + transactions.size() + " transactions");
        }
    }
    
    private Label createTransactionLabel(Transaction transaction) {
        Label label = new Label();
        label.setText(String.format("%s | %s | $%.2f | %s",
                transaction.getTransactionID(),
                transaction.getTransactionType(),
                transaction.getTransactionAmount(),
                transaction.getTransactionDate().toString()));
        
        // Style based on transaction type
        String style = "-fx-padding: 8px; -fx-background-radius: 5px; -fx-font-size: 12px; ";
        
        switch (transaction.getTransactionType()) {
            case "DEPOSIT":
                style += "-fx-background-color: #d5f4e6; -fx-text-fill: #27ae60;";
                break;
            case "WITHDRAWAL":
                style += "-fx-background-color: #fadbd8; -fx-text-fill: #e74c3c;";
                break;
            case "INTEREST":
                style += "-fx-background-color: #e8f4fd; -fx-text-fill: #3498db;";
                break;
            default:
                style += "-fx-background-color: #f8f9fa; -fx-text-fill: #2c3e50;";
        }
        
        label.setStyle(style);
        return label;
    }
    
    @FXML
    private void handleRefresh() {
        loadTransactions();
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(loader.load());
            
            MainMenuController controller = loader.getController();
            controller.setAccount(currentAccount);
            
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Banking System - Main Menu");
        } catch (Exception e) {
            statusLabel.setText("Failed to return to main menu");
        }
    }
}
