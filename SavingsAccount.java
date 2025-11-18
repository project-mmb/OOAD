public class SavingsAccount extends Account implements InterestBearing {
    // 0.05% monthly = 0.0005
    private static final double MONTHLY_RATE = 0.0005;

    public SavingsAccount(Customer owner, double initialDeposit, AccountClassification classification, String businessName) {
        super(owner, initialDeposit, classification, businessName);
    }

    @Override
    public void withdraw(double amount) {
        throw new IllegalStateException("Withdrawals are not allowed from SavingsAccount");
    }

    @Override
    public void payMonthlyInterest() {
        creditInterest(MONTHLY_RATE);
    }
}
