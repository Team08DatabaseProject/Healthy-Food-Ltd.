package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class Order {
    private int orderId;
    private Customer customer;
    //private Subscription subscription; //Denne skal ikke være her på grunn av at sub tar inn mange orders. Ikke omvendt!
    private double price;
    private String customerRequests; //Additional info from customer
    private LocalDate actualDeliveryDate; //Is set when order is delivered. Default null
    private LocalDate deadline; //Consists of date and time
    private String status;
    private Address address;
    private ObservableList<OrderLine> dishesInThisOrder = FXCollections.observableArrayList();

    // From the database
    public Order(int orderId, String customerRequests, LocalDate deadline, double price, String status) {
        this.orderId = orderId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.status = status;
        actualDeliveryDate = null;
    }

    // To the database
    public Order(String customerRequests, LocalDate deadline,
                 double price, String status, Customer customer, ObservableList dishesInThisOrder) {
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.status = status;
        actualDeliveryDate = null;
        this.customer = customer;
        this.dishesInThisOrder = dishesInThisOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerRequests() {
        return customerRequests;
    }

    public void setCustomerRequests(String customerRequests) {
        this.customerRequests = customerRequests;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ObservableList<OrderLine> getDishesInThisOrder() {
        return dishesInThisOrder;
    }

    public void setDishesInThisOrder(ObservableList<OrderLine> dishesInThisOrder) {
        this.dishesInThisOrder = dishesInThisOrder;
    }
}