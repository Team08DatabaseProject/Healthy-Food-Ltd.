package classpackage;

import java.util.Date;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class Order {
    private int orderId;
    private int customerId;
    private int subscriptionId;
    private double price;
    private String customerRequests; //Additional info from customer
    private Date actualDeliveryDate; //Is set when order is delivered. Default null
    private Date deadline; //Consists of date and time
    private String status;
    private Address address;

    public Order(int orderId, int customerId, int subscriptionId, String customerRequests, Date deadline,
                 double price, String address, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.subscriptionId = subscriptionId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.status = status;
        actualDeliveryDate = null;
    }

    public Order(int customerId, int subscriptionId, String customerRequests, Date deadline,
                 double price, String address, String status) {
        this.customerId = customerId;
        this.subscriptionId = subscriptionId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.status = status;
        actualDeliveryDate = null;
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