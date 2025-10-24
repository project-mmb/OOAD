package banking;

import java.util.UUID;

/**
 * Cheque Account class extending Account
 */
public class Cheque extends Account {
    private String companyName;
    private String companyId;
    private double overdraftLimit;
    
    public Cheque() {
        super();
        this.accountType = "CHEQUE";
        this.overdraftLimit = 1000.0; // Default overdraft limit
    }
    
    public Cheque(String accountID, int accountBranch, double accountBalance, Customer customer,
                 String companyName, String companyId, double overdraftLimit) {
        super(accountID, accountBranch, "CHEQUE", accountBalance, customer);
        this.companyName = companyName;
        this.companyId = companyId;
        this.overdraftLimit = overdraftLimit;
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
        if (amount > 0 && (accountBalance - amount) >= -overdraftLimit) {
            updateBalance(-amount);
            String transactionId = "WTH_" + UUID.randomUUID().toString().substring(0, 8);
            Transaction transaction = new Transaction(transactionId, amount, "WITHDRAWAL");
            addTransaction(transaction);
            return true;
        }
        return false;
    }
    
    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyId() {
        return companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
