package banking;

import java.util.UUID;

/**
 * Savings Account class extending Account and implementing Interest
 */
public class Savings extends Account implements Interest {
    private double interestRate;
    
    public Savings() {
        super();
        this.accountType = "SAVINGS";
        this.interestRate = 0.02; // 2% default interest rate
    }
    
    public Savings(String accountID, int accountBranch, double accountBalance, Customer customer, double interestRate) {
        super(accountID, accountBranch, "SAVINGS", accountBalance, customer);
        this.interestRate = interestRate;
    }
    
    @Override
    public boolean depositFunds(double amount) {
        if (amount > 0) {
            updateBalance(amount);
            String transactionId = "DEP_" + UUID.randomUUID().toString().substring(0, 8);
            Transaction transaction = new Transaction(transactionId, amount, "DEPOSIT");
            addTransaction(transaction);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean withdrawFunds(double amount) {
        if (amount > 0 && amount <= accountBalance) {
            updateBalance(-amount);
            String transactionId = "WTH_" + UUID.randomUUID().toString().substring(0, 8);
            Transaction transaction = new Transaction(transactionId, amount, "WITHDRAWAL");
            addTransaction(transaction);
            return true;
        }
        return false;
    }
    
    @Override
    public double calculateInterest(double amount) {
        return amount * interestRate;
    }
    
    public void applyInterest() {
        double interest = calculateInterest(accountBalance);
        updateBalance(interest);
        String transactionId = "INT_" + UUID.randomUUID().toString().substring(0, 8);
        Transaction transaction = new Transaction(transactionId, interest, "INTEREST");
        addTransaction(transaction);
    }
    
    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
