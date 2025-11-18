public class BusinessCustomer extends Customer {

    private String businessName;

    public BusinessCustomer(String firstName, String lastName, String businessName) {
        super(firstName, lastName);
        this.businessName = businessName;
    }

    public BusinessCustomer(String firstName, String lastName, String businessName, String address, String nextOfKinName, String nextOfKinContact) {
        super(firstName, lastName);
        this.businessName = businessName;
        setAddress(address);
        setNextOfKinName(nextOfKinName);
        setNextOfKinContact(nextOfKinContact);
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
