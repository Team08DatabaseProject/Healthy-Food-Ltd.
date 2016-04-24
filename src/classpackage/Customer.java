package classpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Customer {

    private IntegerProperty customerId = new SimpleIntegerProperty();
    private BooleanProperty isBusiness = new SimpleBooleanProperty();
    // desides if its a business client or not, set as a tinyInt (0 = false, 1 = true) has to be converted in fetching from database
    private StringProperty email = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private IntegerProperty phoneNumber = new SimpleIntegerProperty();
    private ObjectProperty<Address> address = new SimpleObjectProperty<>();
    private StringProperty businessName = new SimpleStringProperty();
    private ObjectProperty<Subscription> subscription = new SimpleObjectProperty<>();
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    // From database
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
                    Address address, String businessName, Subscription subscription, ObservableList orders) {
        this.isBusiness.set(isBusiness);
        this.email.set( email);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNumber.set(phoneNumber);
        this.address.set(address);
        this.businessName.set(businessName);
        this.subscription.set(subscription);
        this.orders = orders;
    }

    // constructor for Customers without subs
    public Customer(boolean isBusiness, String email, String firstName, String lastName, int phoneNumber,
                    Address address, String businessName){
        this.isBusiness.set(isBusiness);
        this.email.set( email);
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNumber.set(phoneNumber);
        this.address.set(address);
        this.businessName.set(businessName);
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ObservableList<Order> orders) {
        this.orders = orders;
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public boolean getIsBusiness() {
        return isBusiness.get();
    }

    public BooleanProperty isBusinessProperty() {
        return isBusiness;
    }

    public void setIsBusiness(boolean isBusiness) {
        this.isBusiness.set(isBusiness);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public int getPhoneNumber() {
        return phoneNumber.get();
    }

    public IntegerProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public Address getAddress() {
        return address.get();
    }

    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    public void setAddress(Address address) {
        this.address.set(address);
    }

    public String getBusinessName() {
        return businessName.get();
    }

    public StringProperty businessNameProperty() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName.set(businessName);
    }

    public Subscription getSubscription() {
        return subscription.get();
    }

    public ObjectProperty<Subscription> subscriptionProperty() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription.set(subscription);
    }

    @Override
    public String toString() {
        return firstName.get() + " " + lastName.get();
    }
    /*public String toString() {
        return "Customer{" +
                "businessName='" + businessName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}' + "\tAddress: " + address.toString();
    }*/
}
