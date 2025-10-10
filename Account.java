import java.util.ArrayList;
import java.util.List;

interface Interest {
    
    void applyInterest();
}

public abstract class Account {
    protected final String accountNumber;
    protected double balance;
    protected final String customerId;
    protected final List<Transaction> transactions;

    public Account(String accountNumber, String customerId, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = initialDeposit;
        this.transactions = new ArrayList<>();
    }

    public abstract String getAccountType();


    public abstract boolean withdraw(double amount);



    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }


    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getCustomerId() { return customerId; }
    public List<Transaction> getTransactions() { return transactions; }
}
