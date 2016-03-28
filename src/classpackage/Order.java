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
    private String address; //Delivery address
    private Date deadline; //Consists of date and time

    public Order(int orderId, int customerId, int subscriptionId, String customerRequests, Date deadline,
                 double price, String address) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.subscriptionId = subscriptionId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.address = address;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}