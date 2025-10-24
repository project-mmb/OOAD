package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Account class representing different types of bank accounts
 */
public abstract class Account {
    protected String accountID;
    protected int accountBranch;
    protected String accountType;
    protected double accountBalance;
    protected Customer customer;
    protected List<Transaction> transactions;
    
    public Account() {
        this.transactions = new ArrayList<>();
    }
    
    public Account(String accountID, int accountBranch, String accountType, double accountBalance, Customer customer) {
        this.accountID = accountID;
        this.accountBranch = accountBranch;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.customer = customer;
        this.transactions = new ArrayList<>();
    }
    
    // Abstract methods
    public abstract boolean depositFunds(double amount);
    public abstract boolean withdrawFunds(double amount);
    
    // Concrete methods
    public double viewBalance() {
        return accountBalance;
    }
    
    public List<Transaction> viewTransactions() {
        return new ArrayList<>(transactions);
    }
    
    protected void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
    
    protected void updateBalance(double amount) {
        this.accountBalance += amount;
    }
    
    // Getters and Setters
    public String getAccountID() {
        return accountID;
    }
    
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    
    public int getAccountBranch() {
        return accountBranch;
    }
    
    public void setAccountBranch(int accountBranch) {
        this.accountBranch = accountBranch;
    }
    
    public String getAccountType() {
        return accountType;
    }
    
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    @Override
    public String toString() {
        return String.format("Account ID: %s | Type: %s | Balance: $%.2f | Customer: %s",
                accountID, accountType, accountBalance, customer.toString());
    }
}
