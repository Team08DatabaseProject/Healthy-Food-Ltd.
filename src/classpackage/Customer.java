package classpackage;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Customer {
    private int customerId;
    private boolean isBusiness;
    // desides if its a business client or not, set as a tinyInt (0 = false, 1 = true) has to be converted in fetching from database
    private String email;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private Address address;
    private String businessName = "";
    private Subscription subscription;



   /* private IntegerProperty customerId = new SimpleIntegerProperty();
    private BooleanProperty isBusiness = new SimpleBooleanProperty();
    // desides if its a business client or not, set as a tinyInt (0 = false, 1 = true) has to be converted in fetching from database
    private StringProperty email = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private IntegerProperty phoneNumber = new SimpleIntegerProperty();
    private Address address;
    private StringProperty businessName = new SimpleStringProperty();
    private Subscription subscription;

    // Fra database
    public Customer(int customerId, boolean isBusiness, String email, String firstName, String lastName,
                    int phoneNumber, String businessName) {
        this.customerId.set(customerId);
        this.isBusiness.set(isBusiness);
        this.email.set(email);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNumber.set(phoneNumber);
        this.businessName.set(businessName);
    }

    // To database
    public Customer(boolean isBusiness, String email, String firstName, String lastName, int phoneNumber,
                    Address address, String businessName, Subscription subscription) {
        this.isBusiness.set(isBusiness);
        this.email.set( email);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNumber.set(phoneNumber);
        this.address = address;
        this.businessName.set(businessName);
        this.subscription = subscription;
    }*/

 // Fra database
    public Customer(int customerId, boolean isBusiness, String email, String firstName, String lastName,
                    int phoneNumber, String businessName, Address address) {
        this.customerId = customerId;
        this.isBusiness = isBusiness;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.businessName = businessName;
        this.address = address;
    }

    // To database
    public Customer(boolean isBusiness, String email, String firstName, String lastName, int phoneNumber,
                    Address address, String businessName, Subscription subscription) {
        this.isBusiness = isBusiness;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.businessName = businessName;
        this.subscription = subscription;
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "businessName='" + businessName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}' + "\tAddress: " + address.toString();
    }
}
