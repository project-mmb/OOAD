import java.util.UUID;

public abstract class Account {
    private final String accountNumber;
    private double balance;
    private String branch;
    private final Customer owner;
    private final AccountClassification classification;
    private final String businessName; // optional, only when classification == BUSINESS

    protected Account(Customer owner, double initialDeposit, AccountClassification classification, String businessName) {
        if (owner == null) throw new IllegalArgumentException("owner is null");
        if (classification == null) throw new IllegalArgumentException("classification is null");
        this.owner = owner;
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = 0.0;
        this.classification = classification;
        this.businessName = classification == AccountClassification.BUSINESS ? (businessName == null ? "" : businessName) : null;
        if (initialDeposit > 0) {
            deposit(initialDeposit);
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public Customer getOwner() { return owner; }
    public AccountClassification getClassification() { return classification; }
    public String getBusinessName() { return businessName; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        if (amount > balance) throw new IllegalArgumentException("insufficient funds");
        balance -= amount;
    }

    protected void creditInterest(double rate) {
        if (rate < 0) throw new IllegalArgumentException("rate must be >= 0");
        double interest = balance * rate;
        balance += interest;
    }

    @Override
    public String toString() {
        String cls = classification == AccountClassification.BUSINESS ? "BUSINESS" : "PERSONAL";
        String name = (businessName != null && !businessName.isBlank()) ? (" - " + businessName) : "";
        return getClass().getSimpleName() + "{" + accountNumber.substring(0, 8) + "}" + " [" + cls + "]" + name + " - BWP " + String.format("%.2f", balance);
    }
}
