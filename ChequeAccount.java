public class ChequeAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500.00;

    public ChequeAccount(String accountNumber, String customerId, double initialDeposit) {
        super(accountNumber, customerId, initialDeposit);
    }

    @Override
    public String getAccountType() {
        return "Cheque";
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0) {
            // Calculate the potential new balance
            double potentialBalance = balance - amount;

            // Check if withdrawal is possible without exceeding the overdraft limit
            if (potentialBalance >= -OVERDRAFT_LIMIT) {
                balance = potentialBalance;
                return true;
            } else {
                System.out.println("Withdrawal failed: Exceeds overdraft limit of $" + OVERDRAFT_LIMIT);
                return false;
            }
        }
        return false;
    }
    
    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}