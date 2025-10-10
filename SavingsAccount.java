public class SavingsAccount extends Account implements Interest {
    private static final double INTEREST_RATE = 0.035; 

    public SavingsAccount(String accountNumber, String customerId, double initialDeposit) {
        super(accountNumber, customerId, initialDeposit);
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public boolean withdraw(double amount) {
        
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void applyInterest() {
        if (balance > 0) {
            double interestAmount = balance * INTEREST_RATE;
            balance += interestAmount;
        }
    }
}
