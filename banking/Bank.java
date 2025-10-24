package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank class managing accounts and customers
 */
public class Bank {
    private List<Account> accounts;
    private List<Customer> customers;
    private String bankName;
    
    public Bank() {
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bankName = "Professional Banking System";
    }
    
    public Bank(String bankName) {
        this.accounts = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bankName = bankName;
    }
    
    public boolean authenticateCustomer(int customerId, String password) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId && 
                customer.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean authenticateAccount(String accountId, int customerId) {
        for (Account account : accounts) {
            if (account.getAccountID().equals(accountId) && 
                account.getCustomer().getCustomerId() == customerId) {
                return true;
            }
        }
        return false;
    }
    
    public List<Transaction> transactionHistory(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountID().equals(accountId)) {
                return account.viewTransactions();
            }
        }
        return new ArrayList<>();
    }
    
    public Account findAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountID().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
    
    public Customer findCustomer(int customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public List<Account> getCustomerAccounts(int customerId) {
        List<Account> customerAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getCustomer().getCustomerId() == customerId) {
                customerAccounts.add(account);
            }
        }
        return customerAccounts;
    }
    
    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
    
    public List<Customer> getCustomers() {
        return new ArrayList<>(customers);
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
