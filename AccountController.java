import java.util.List;

public class AccountController {

    public SavingsAccount openSavings(Customer customer, double initialDeposit, AccountClassification classification, String businessName) {
        SavingsAccount sa = new SavingsAccount(customer, initialDeposit, classification, businessName);
        customer.addAccount(sa);
        FileStorage.appendTransaction(customer, sa, "OPEN_SAVINGS", initialDeposit, sa.getBalance());
        return sa;
    }

    public InvestmentAccount openInvestment(Customer customer, double initialDeposit, AccountClassification classification, String businessName) {
        InvestmentAccount ia = new InvestmentAccount(customer, initialDeposit, classification, businessName);
        customer.addAccount(ia);
        FileStorage.appendTransaction(customer, ia, "OPEN_INVESTMENT", initialDeposit, ia.getBalance());
        return ia;
    }

    public ChequeAccount openCheque(Customer customer, double initialDeposit, AccountClassification classification, String businessName, String employerName, String employerAddress) {
        ChequeAccount ca = new ChequeAccount(customer, initialDeposit, classification, businessName, employerName, employerAddress);
        customer.addAccount(ca);
        FileStorage.appendTransaction(customer, ca, "OPEN_CHEQUE", initialDeposit, ca.getBalance());
        return ca;
    }

    public void deposit(Account account, double amount) {
        double before = account.getBalance();
        account.deposit(amount);
        FileStorage.appendTransaction(account.getOwner(), account, "DEPOSIT", amount, account.getBalance());
    }

    public void withdraw(Account account, double amount) {
        double before = account.getBalance();
        account.withdraw(amount);
        FileStorage.appendTransaction(account.getOwner(), account, "WITHDRAW", amount, account.getBalance());
    }

    public void payMonthlyInterest(Account account) {
        if (account instanceof InterestBearing) {
            double before = account.getBalance();
            ((InterestBearing) account).payMonthlyInterest();
            double diff = account.getBalance() - before;
            FileStorage.appendTransaction(account.getOwner(), account, "INTEREST", diff, account.getBalance());
        }
    }

    public List<Account> listAccounts(Customer customer) {
        return customer.getAccounts();
    }
}
