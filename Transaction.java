import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId;
    private final String accountNo;
    private final LocalDateTime timestamp;
    private final String type; 
    private final double amount;
    private final double postBalance;

    public Transaction(String transactionId, String accountNo, String type, double amount, double postBalance) {
        this.transactionId = transactionId;
        this.accountNo = accountNo;
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.postBalance = postBalance;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s on Account %s: $%.2f. New Balance: $%.2f",
                timestamp.format(formatter), type, accountNo, amount, postBalance);
    }
}