import java.util.HashMap;
import java.util.Map;

public class LoginController {
    // In-memory username -> Customer and username -> password
    private static final Map<String, Customer> users = new HashMap<>();
    private static final Map<String, String> passwords = new HashMap<>();

    private static final String STAFF_USERNAME = "123@123-000";
    private static final String STAFF_PASSWORD = "ffgroundsbankstaff123";

    public boolean isStaff(String username, String password) {
        return STAFF_USERNAME.equals(username) && STAFF_PASSWORD.equals(password);
    }

    public Customer login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }
        try {
            if (!FileStorage.validateCredential(username, password)) {
                throw new IllegalArgumentException("Invalid username or password");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to validate credentials: " + e.getMessage());
        }

        String key = username.toLowerCase();
        Customer existing = users.get(key);
        if (existing != null) {
            return existing;
        }
        try {
            Customer loaded = FileStorage.loadCustomer(username);
            if (loaded == null) {
                throw new IllegalArgumentException("User profile not found");
            }
            users.put(key, loaded);
            passwords.put(key, password);
            return loaded;
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to load customer: " + e.getMessage());
        }
    }

    public Customer register(String username, String password,
                             String classification,
                             String businessName,
                             String firstName, String lastName,
                             String address,
                             String nextOfKinName, String nextOfKinContact) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }
        if (firstName == null || firstName.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("First and last name are required");
        }
        String key = username.toLowerCase();
        if (users.containsKey(key)) {
            throw new IllegalArgumentException("Username already exists");
        }
        Customer c;
        boolean isBusiness = classification != null && classification.equalsIgnoreCase("Business");
        if (isBusiness) {
            if (businessName == null || businessName.isBlank()) {
                throw new IllegalArgumentException("Business name is required for Business accounts");
            }
            BusinessCustomer bc = new BusinessCustomer(firstName.trim(), lastName.trim(), businessName.trim());
            c = bc;
        } else {
            c = new PersonalCustomer(firstName.trim(), lastName.trim());
        }
        c.setUsername(username.trim());
        c.setAddress(address);
        c.setNextOfKinName(nextOfKinName);
        c.setNextOfKinContact(nextOfKinContact);
        users.put(key, c);
        passwords.put(key, password);
        try {
            FileStorage.appendCredential(username.trim(), password);
            FileStorage.appendCustomer(c, classification, businessName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to persist registration: " + e.getMessage());
        }
        return c;
    }
}
