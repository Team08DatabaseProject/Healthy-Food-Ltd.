package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class Order {
    private int orderId;
    private Customer customer;
    private Subscription subscription;
    private double price;
    private String customerRequests; //Additional info from customer
    private Date actualDeliveryDate; //Is set when order is delivered. Default null
    private Date deadline; //Consists of date and time
    private String status;
    private Address address;
    private ObservableList<Dish> dishesInThisOrder = FXCollections.observableArrayList();

    // From the database
    public Order(int orderId, String customerRequests, Date deadline,
                 double price, String status) {
        this.orderId = orderId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.status = status;
        actualDeliveryDate = null;
    }

    // To the database
    public Order(Subscription subscription, String customerRequests, Date deadline,
                 double price, String status, Customer customer, ObservableList<Dish> dishesInThisOrder) {
        this.subscription = subscription;
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

    public Date getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(Date actualDeliveryDate) {
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

}