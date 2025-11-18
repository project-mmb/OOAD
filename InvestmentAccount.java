public class InvestmentAccount extends Account implements InterestBearing {
    // 5% monthly = 0.05
    private static final double MONTHLY_RATE = 0.05;

    public InvestmentAccount(Customer owner, double initialDeposit, AccountClassification classification, String businessName) {
        super(owner, initialDeposit, classification, businessName);
        if (initialDeposit < 500.0) {
            throw new IllegalArgumentException("Investment account requires initial deposit of at least BWP 500.00");
        }
    }

    @Override
    public void payMonthlyInterest() {
        creditInterest(MONTHLY_RATE);
    }
}
