import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface Bank {
    boolean addCustomer(Customer customer);
    Customer findCustomer(String customerId);
    boolean performTransaction(Account account, double amount, String type);
    List<Customer> getAllCustomers();
}

interface LoginUI {
    Customer login();
}

public class Main implements Bank, LoginUI {
    private final Map<String, Customer> customers;
    private final Map<String, Account> accounts;
    private long transactionCounter = 1000;
    private final Scanner scanner;

    public Main() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
        this.scanner = new Scanner(System.in);
    }

     @Override
    public boolean addCustomer(Customer customer) {
        if (!customers.containsKey(customer.getCustomerId())) {
            customers.put(customer.getCustomerId(), customer);
            for (Account account : customer.getAccounts()) {
                accounts.put(account.getAccountNumber(), account);
            }
            return true;
        }
        return false;
    }

 @Override
    public Customer findCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public boolean performTransaction(Account account, double amount, String type) {
        boolean success = false;
        double transactionAmount = amount; 

        switch (type.toLowerCase()) {
            case "deposit":
                success = account.deposit(amount);
                break;
            case "withdraw":
                success = account.withdraw(amount);
                break;
            case "interest":
                if (account instanceof Interest) {
                    double interestBefore = account.getBalance();
                    ((Interest) account).applyInterest();
                    transactionAmount = account.getBalance() - interestBefore;
                    success = (transactionAmount > 0); 
                } else {
                    return false;
                }
                break;
            default:
                return false;
        }

        if (success) {
            String transactionId = String.valueOf(transactionCounter++);
            Transaction transaction = new Transaction(
                transactionId,
                account.getAccountNumber(),
                type,
                transactionAmount, 
                account.getBalance()
            );
            account.addTransaction(transaction);
            System.out.printf("Transaction successful. New Balance: $%.2f%n", account.getBalance());
            return true;
        } else {
            System.out.println("Transaction failed.");
            return false;
        }
    }

    @Override
    public Customer login() {
        System.out.println("  --- Bank System Login ---");
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Customer customer = findCustomer(customerId); 

        if (customer != null && customer.getPassword().equals(password)) {
            System.out.println("Login successful. Welcome, " + customer.getName() + "!");
            return customer;
        } else {
            System.out.println("Login failed. Wrong ID or Password.");
            return null;
        }
    }
    
    private void showCustomerMenu(Customer customer) {
        boolean running = true;
        while (running) {
            System.out.println("Customer Menu: " + customer.getName() + " ---");
            System.out.println("1. View Accounts & Balances");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transactions");
            System.out.println("5. Apply Interest (Demo)");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            try {
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 

                    switch (choice) {
                        case 1: viewAccounts(customer); break;
                        case 2: handleDeposit(customer); break;
                        case 3: handleWithdraw(customer); break;
                        case 4: viewTransactions(customer); break;
                        case 5: handleInterest(customer); break;
                        case 6:
                            running = false;
                            System.out.println("Logging out. Goodbye!");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); 
                }
            } catch (Exception e) {
                System.out.println("An unexpected error occurred. Please try again.");
                scanner.nextLine(); 
            }
        }
    }

    private Account selectAccount(Customer customer) {
        List<Account> accounts = customer.getAccounts();
      
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return null;
        }

        System.out.println("\n--- Select Account ---");
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.printf("%d. %s Account (No: %s) - Balance: $%.2f%n",
                    i + 1, acc.getAccountType(), acc.getAccountNumber(), acc.getBalance());
        }
        System.out.print("Enter the number of the account: ");

        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            if (choice > 0 && choice <= accounts.size()) {
                return accounts.get(choice - 1);
            } else {
                System.out.println("Invalid account selection.");
                return null;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Returning to menu.");
            scanner.nextLine();
            return null;
        }
    }

    private void viewAccounts(Customer customer) {
        System.out.println("\n--- Account Balances ---");
        for (Account acc : customer.getAccounts()) {
            System.out.printf("  - %s Account (No: %s): $%.2f%n", acc.getAccountType(), acc.getAccountNumber(), acc.getBalance());
        }
    }

    private void handleDeposit(Customer customer) {
        Account account = selectAccount(customer);
        if (account == null) return;

        try {
            System.out.print("Enter deposit amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (amount > 0) performTransaction(account, amount, "Deposit");
            else System.out.println("Deposit amount must be positive.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount entered. Returning to menu.");
            scanner.nextLine();
        }
    }

    private void handleWithdraw(Customer customer) {
        Account account = selectAccount(customer);
        if (account == null) return;

        try {
            System.out.print("Enter withdrawal amount: $");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            if (amount > 0) performTransaction(account, amount, "Withdraw");
            else System.out.println("Withdrawal amount must be positive.");
        } catch (InputMismatchException e) {
            System.out.println("Invalid amount entered. Returning to menu.");
            scanner.nextLine();
        }
    }

    private void viewTransactions(Customer customer) {
        Account account = selectAccount(customer);
        if (account == null) return;

        System.out.println("\n--- Transactions for Account " + account.getAccountNumber() + " ---");
        List<Transaction> transactions = account.getTransactions();
        if (transactions.isEmpty()) { System.out.println("No transactions found."); return; }

        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i));
        }
    }

    private void handleInterest(Customer customer) {
        Account account = selectAccount(customer);
        if (account == null) return;

        if (account instanceof Interest) {
            System.out.println("Applying interest to " + account.getAccountType() + " account...");
            performTransaction(account, 0, "Interest");
        } else {
            System.out.println("This account type (" + account.getAccountType() + ") does not accrue interest.");
        }
    }

    public static void main(String[] args) {
        Main app = new Main();
        
        Customer cust1 = new Customer("C001", "Maatla M", "Kumakwane", "267-73724462", "siakoo123");
        cust1.addAccount(new SavingsAccount("SACC", cust1.getCustomerId(), 51234000.00));
        cust1.addAccount(new ChequeAccount("CACC-A", cust1.getCustomerId(), 90000.00));
        app.addCustomer(cust1);

        Customer cust2 = new Customer("C002", "Boipuso Chipa", "Mmankgodi", "267-71231098", "bobngwana12");
        cust2.addAccount(new InvestmentAccount("I002-A", cust2.getCustomerId(), 15000.00));
        app.addCustomer(cust2);
        
    
        while (true) {
            Customer loggedInCustomer = app.login();
            if (loggedInCustomer != null) {
                app.showCustomerMenu(loggedInCustomer);
            }
        }
    }
}