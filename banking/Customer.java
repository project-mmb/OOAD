package banking;

/**
 * Customer class representing a bank customer
 */
public class Customer {
    private String customerName;
    private String customerSurname;
    private int customerId;
    private String customerAddress;
    private String password;
    
    public Customer() {
        // Default constructor
    }
    
    public Customer(String customerName, String customerSurname, int customerId, String customerAddress, String password) {
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerId = customerId;
        this.customerAddress = customerAddress;
        this.password = password;
    }
    
    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerSurname() {
        return customerSurname;
    }
    
    public void setCustomerSurname(String customerSurname) {
        this.customerSurname = customerSurname;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getCustomerAddress() {
        return customerAddress;
    }
    
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return customerName + " " + customerSurname + " (ID: " + customerId + ")";
    }
}
