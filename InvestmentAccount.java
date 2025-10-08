public class InvestmentAccount extends Account {
  
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
                System.out.println("Withdrawal failed: Maximum monthly withdrawals exceeded.");
                return false;
            }
        }
        return false;
    }

}