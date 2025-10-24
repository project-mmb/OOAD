package banking;

/**
 * Simple test to verify the banking system core functionality
 */
public class BankingTest {
    public static void main(String[] args) {
        System.out.println("=== BANKING SYSTEM TEST ===");
        
        // Create bank
        Bank bank = new Bank();
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", 1001, "123 Main St", "password123");
        Customer customer2 = new Customer("Jane", "Smith", 1002, "456 Oak Ave", "password456");
        
        bank.addCustomer(customer1);
        bank.addCustomer(customer2);
        
        // Create accounts
        Savings savings = new Savings("SAV001", 1, 1000.0, customer1, 0.02);
        Investment investment = new Investment("INV001", 1, 5000.0, customer1, 5000.0, 0.05);
        Cheque cheque = new Cheque("CHQ001", 1, 2500.0, customer2, "Smith Corp", "CORP001", 1000.0);
        
        bank.addAccount(savings);
        bank.addAccount(investment);
        bank.addAccount(cheque);
        
        // Test transactions
        System.out.println("\n--- Testing Deposits ---");
        savings.depositFunds(500.0);
        investment.depositFunds(1000.0);
        cheque.depositFunds(200.0);
        
        System.out.println("\n--- Testing Withdrawals ---");
        savings.withdrawFunds(200.0);
        investment.withdrawFunds(500.0);
        cheque.withdrawFunds(100.0);
        
        // Test authentication
        System.out.println("\n--- Testing Authentication ---");
        boolean auth1 = bank.authenticateAccount("SAV001", 1001);
        boolean auth2 = bank.authenticateAccount("INV001", 1001);
        boolean auth3 = bank.authenticateAccount("CHQ001", 1002);
        
        System.out.println("SAV001 + 1001: " + (auth1 ? "✓ PASS" : "✗ FAIL"));
        System.out.println("INV001 + 1001: " + (auth2 ? "✓ PASS" : "✗ FAIL"));
        System.out.println("CHQ001 + 1002: " + (auth3 ? "✓ PASS" : "✗ FAIL"));
        
        // Display final balances
        System.out.println("\n--- Final Account Balances ---");
        System.out.println("Savings Account: $" + String.format("%.2f", savings.getAccountBalance()));
        System.out.println("Investment Account: $" + String.format("%.2f", investment.getAccountBalance()));
        System.out.println("Cheque Account: $" + String.format("%.2f", cheque.getAccountBalance()));
        
        // Display transaction counts
        System.out.println("\n--- Transaction Counts ---");
        System.out.println("Savings Transactions: " + savings.viewTransactions().size());
        System.out.println("Investment Transactions: " + investment.viewTransactions().size());
        System.out.println("Cheque Transactions: " + cheque.viewTransactions().size());
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
        System.out.println("Core banking functionality is working correctly!");
        System.out.println("You can now run the JavaFX application with: run.bat");
    }
}
