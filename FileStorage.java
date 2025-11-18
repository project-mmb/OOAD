import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileStorage {

    private static final String CREDENTIALS_FILE = "usepas.txt";
    private static final String CUSTOMERS_FILE = "customer.txt";
    private static final String TRANSACTIONS_FILE = "transact.txt";

    private static Path path(String fileName) {
        return Paths.get(fileName).toAbsolutePath();
    }

    private static void ensureFile(Path p) throws IOException {
        File f = p.toFile();
        if (!f.exists()) {
            Files.createFile(p);
        }
    }

    // ---------- Credentials ----------

    public static void appendCredential(String username, String password) throws IOException {
        Path p = path(CREDENTIALS_FILE);
        ensureFile(p);
        try (BufferedWriter w = new BufferedWriter(new FileWriter(p.toFile(), true))) {
            w.write(username + "|" + password);
            w.newLine();
        }
    }

    public static boolean validateCredential(String username, String password) throws IOException {
        Path p = path(CREDENTIALS_FILE);
        if (!Files.exists(p)) return false;
        try (BufferedReader r = new BufferedReader(new FileReader(p.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    // ---------- Customers ----------

    public static void appendCustomer(Customer c, String classification, String businessName) throws IOException {
        Path p = path(CUSTOMERS_FILE);
        ensureFile(p);
        String type = (classification != null ? classification.toUpperCase() : "PERSONAL");
        String biz = businessName == null ? "" : businessName;
        try (BufferedWriter w = new BufferedWriter(new FileWriter(p.toFile(), true))) {
            w.write(String.join("|",
                    nullSafe(c.getUsername()),
                    type,
                    biz,
                    nullSafe(c.getFirstName()),
                    nullSafe(c.getLastName()),
                    nullSafe(c.getAddress()),
                    nullSafe(c.getNextOfKinName()),
                    nullSafe(c.getNextOfKinContact())));
            w.newLine();
        }
    }

    public static Customer loadCustomer(String username) throws IOException {
        Path p = path(CUSTOMERS_FILE);
        if (!Files.exists(p)) return null;
        try (BufferedReader r = new BufferedReader(new FileReader(p.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 8 && parts[0].equalsIgnoreCase(username)) {
                    String type = parts[1];
                    String biz = parts[2];
                    String first = parts[3];
                    String last = parts[4];
                    String address = parts[5];
                    String kin = parts[6];
                    String kinContact = parts[7];
                    Customer c;
                    if ("BUSINESS".equalsIgnoreCase(type)) {
                        c = new BusinessCustomer(first, last, biz);
                    } else {
                        c = new PersonalCustomer(first, last);
                    }
                    c.setUsername(username);
                    c.setAddress(address);
                    c.setNextOfKinName(kin);
                    c.setNextOfKinContact(kinContact);
                    return c;
                }
            }
        }
        return null;
    }

    private static String nullSafe(String v) {
        return v == null ? "" : v;
    }

    // ---------- Transactions ----------

    public static void appendTransaction(Customer customer, Account account, String type, double amount, double balanceAfter) {
        try {
            Path p = path(TRANSACTIONS_FILE);
            ensureFile(p);
            String username = customer.getUsername();
            String accNo = account.getAccountNumber();
            String accType = account.getClass().getSimpleName();
            String ts = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            try (BufferedWriter w = new BufferedWriter(new FileWriter(p.toFile(), true))) {
                w.write(String.join("|",
                        nullSafe(username),
                        nullSafe(accNo),
                        nullSafe(accType),
                        nullSafe(type),
                        String.valueOf(amount),
                        String.valueOf(balanceAfter),
                        ts));
                w.newLine();
            }
        } catch (IOException e) {
            // For this assignment we silently ignore logging failures
        }
    }

    public static List<String> loadTransactionsFor(String username) throws IOException {
        List<String> result = new ArrayList<>();
        Path p = path(TRANSACTIONS_FILE);
        if (!Files.exists(p)) return result;
        try (BufferedReader r = new BufferedReader(new FileReader(p.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length >= 7 && parts[0].equalsIgnoreCase(username)) {
                    result.add(line);
                }
            }
        }
        return result;
    }
}
