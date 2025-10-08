public class SavingsAccount extends Account  {
    
    private static final double MIN_BALANCE_THRESHOLD = 100.00;

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

            if (balance < MIN_BALANCE_THRESHOLD) {

                }
            return true;
        }
        return false;
    }

    
}