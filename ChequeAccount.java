public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;

    public ChequeAccount(Customer owner, double initialDeposit, AccountClassification classification, String businessName, String employerName, String employerAddress) {
        super(owner, initialDeposit, classification, businessName);
        if (employerName == null || employerName.isBlank()) {
            throw new IllegalArgumentException("Employer name required for ChequeAccount");
        }
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }

    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String employerAddress) { this.employerAddress = employerAddress; }
}
