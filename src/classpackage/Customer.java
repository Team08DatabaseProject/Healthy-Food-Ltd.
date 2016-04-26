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
    private StringProperty email = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private IntegerProperty phoneNumber = new SimpleIntegerProperty();
    private ObjectProperty<Address> address = new SimpleObjectProperty<>();
    private StringProperty businessName = new SimpleStringProperty();
    private ObjectProperty<Subscription> subscription = new SimpleObjectProperty<>();
    private ObservableList<Order> orders = FXCollections.observableArrayList();

    /**
     * Creates a Customer object from the database
     *
     * @param customerId the unique identifier of the customer
     * @param isBusiness bool on if its a business client or not
     * @param email the email of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param phoneNumber cutomer's phone number
     * @param businessName business name if any
     */
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

    /**
     * Creates a Customer object
     *
     * @param isBusiness bool on if its a business client or not
     * @param email the email of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param phoneNumber cutomer's phone number
     * @param address address to deliver
     * @param businessName business name if any
     * @param subscription the subscription of the customer if any
     * @param orders the orders
     */
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

    /**
     * Creates a Customer object where the customer has no subscription
     *
     * @param isBusiness bool on if its a business client or not
     * @param email the email of the customer
     * @param firstName first name of the customer
     * @param lastName last name of the customer
     * @param phoneNumber cutomer's phone number
     * @param address address to deliver
     * @param businessName business name if any
     */
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


    public Customer(){  // needed for PopupDialog
        this.customerId.set(-1);
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

    /**
     * Return if its a business or not
     *
     * @return true if its a business
     */
    public boolean getIsBusiness() {
        return isBusiness.get();
    }

    /**
     * Return if its a business or not in style for JavaFX (BooleanProperty)
     *
     * @return true if its a business
     */
    public BooleanProperty isBusinessProperty() {
        return isBusiness;
    }

    /**
     * Sets if its a business or not
     *
     * @param isBusiness sets if its a business or not
     */
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

    /**
     * String representation of the object (returns the first name and last name of the customer)
     */
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
