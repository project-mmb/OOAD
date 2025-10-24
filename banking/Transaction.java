package banking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Transaction class representing a bank transaction
 */
public class Transaction {
    private String transactionID;
    private double transactionAmount;
    private LocalDateTime transactionDate;
    private String transactionType; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"
    
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
    }
    
    public Transaction(String transactionID, double transactionAmount, String transactionType) {
        this.transactionID = transactionID;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getTransactionID() {
        return transactionID;
    }
    
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    
    public double getTransactionAmount() {
        return transactionAmount;
    }
    
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public String transactionRecord() {
        return String.format("Transaction ID: %s | Amount: $%.2f | Type: %s | Date: %s",
                transactionID, transactionAmount, transactionType, transactionDate.toString());
    }
    
    @Override
    public String toString() {
        return transactionRecord();
    }
}
