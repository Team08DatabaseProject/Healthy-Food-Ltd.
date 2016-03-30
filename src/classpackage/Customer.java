package classpackage;

/**
 * Created by paul thomas on 16.03.2016.
 */
class Customer {
    private int customerId;
    private boolean isBusiness; // desides if its a business client or not, set as a tinyInt (0 = false, 1 = true) has to be converted in fetching from database
    private String email;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private Address address;
    private String businessName = "";

    public Customer(boolean isBusiness, String email, String firstName, String lastName, int phoneNumber, Address address, String businessName) {
        this.customerId = customerId;
        this.isBusiness = isBusiness;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.businessName = businessName;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
