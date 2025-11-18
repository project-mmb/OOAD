public class PersonalCustomer extends Customer {

    public PersonalCustomer(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public PersonalCustomer(String firstName, String lastName, String address, String nextOfKinName, String nextOfKinContact) {
        super(firstName, lastName);
        setAddress(address);
        setNextOfKinName(nextOfKinName);
        setNextOfKinContact(nextOfKinContact);
    }
}
