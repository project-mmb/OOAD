public class InvestmentAccount extends Account implements Interest {
    private static final double INTEREST_RATE = 0.05;
    private static final int MAX_MONTHLY_WITHDRAWALS = 4;

    private int monthlyWithdrawalCount = 0; 

    public InvestmentAccount(String accountNumber, String customerId, double initialDeposit) {
        super(accountNumber, customerId, initialDeposit);
    }

    @Override
    public String getAccountType() {
        return "Investment";
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            if (monthlyWithdrawalCount < MAX_MONTHLY_WITHDRAWALS) {
                balance -= amount;
                monthlyWithdrawalCount++;
                return true;
            } else {
                System.out.println("Withdrawal failed: Maximum monthly withdrawals exceeded (limit: " + MAX_MONTHLY_WITHDRAWALS + ").");
                return false;
            }
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
    
    public void resetMonthlyWithdrawals() {
        this.monthlyWithdrawalCount = 0;
    }
}