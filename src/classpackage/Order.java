package classpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class Order {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty customerRequests = new SimpleStringProperty(); //Additional info from customer
    private LocalDate actualDeliveryDate; //Is set when order is delivered. Default null
    private LocalDate deadline; //Consists of date and time
    private StringProperty status = new SimpleStringProperty();
    private Address address;
    private ObservableList<OrderLine> dishesInThisOrder = FXCollections.observableArrayList();

    // From the database
    public Order(int orderId, String customerRequests, LocalDate deadline,
                 LocalDate actualDeliveryDate, double price,
                 String status, ObservableList dishesInThisOrder, Address address) {
        this.orderId.set(orderId);
        this.customerRequests.set(customerRequests);
        this.deadline = deadline;
        this.price.set(price);
        this.status.set(status);
        this.actualDeliveryDate = actualDeliveryDate;
        this.dishesInThisOrder = dishesInThisOrder;
        this.address = address;
    }

    // To the database
    public Order(String customerRequests, LocalDate deadline, double price,
                 String status, ObservableList dishesInThisOrder) {
        this.customerRequests.set(customerRequests);
        this.deadline = deadline;
        this.price.set(price);
        this.status.set(status);
        actualDeliveryDate = null;
        this.dishesInThisOrder = dishesInThisOrder;
    }



    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getCustomerRequests() {
        return customerRequests.get();
    }

    public StringProperty customerRequestsProperty() {
        return customerRequests;
    }

    public void setCustomerRequests(String customerRequests) {
        this.customerRequests.set(customerRequests);
    }

    public LocalDate getActualDeliveryDate() {
        return actualDeliveryDate;
    }

    public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
        this.actualDeliveryDate = actualDeliveryDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ObservableList<OrderLine> getDishesInThisOrder() {
        return dishesInThisOrder;
    }

    public void setDishesInThisOrder(ObservableList<OrderLine> dishesInThisOrder) {
        this.dishesInThisOrder = dishesInThisOrder;
    }
}