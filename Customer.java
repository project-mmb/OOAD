import java.util.ArrayList;
import java.util.List;


public class Customer {
    private final String customerId;
    private final String name;
    private final String address;
    private final String phoneNumber;
    private final String password; 
    private final List<Account> accounts;

    public Customer(String customerId, String name, String address, String phoneNumber, String password) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accounts = new ArrayList<>();
    }

    

    public void addAccount(Account account) {
        if (account != null) {
            this.accounts.add(account);
        }
    }



    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public List<Account> getAccounts() { return accounts; }

}