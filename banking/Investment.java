package banking;

import java.util.UUID;

/**
 * Investment Account class extending Account and implementing Interest
 */
public class Investment extends Account implements Interest {
    private double initialDeposit;
    private double interestRate;
    
    public Investment() {
        super();
        this.accountType = "INVESTMENT";
        this.interestRate = 0.05; // 5% default interest rate for investments
    }
    
    public Investment(String accountID, int accountBranch, double accountBalance, Customer customer, 
                     double initialDeposit, double interestRate) {
        super(accountID, accountBranch, "INVESTMENT", accountBalance, customer);
        this.initialDeposit = initialDeposit;
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
    public double getInitialDeposit() {
        return initialDeposit;
    }
    
    public void setInitialDeposit(double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }
    
    public double getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
