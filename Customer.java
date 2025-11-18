import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private String nextOfKinName;
    private String nextOfKinContact;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNextOfKinName() { return nextOfKinName; }
    public void setNextOfKinName(String nextOfKinName) { this.nextOfKinName = nextOfKinName; }

    public String getNextOfKinContact() { return nextOfKinContact; }
    public void setNextOfKinContact(String nextOfKinContact) { this.nextOfKinContact = nextOfKinContact; }

    public List<Account> getAccounts() { return accounts; }

    public void addAccount(Account account) {
        if (account == null) throw new IllegalArgumentException("account is null");
        accounts.add(account);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
