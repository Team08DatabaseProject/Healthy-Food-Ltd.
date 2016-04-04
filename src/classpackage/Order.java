package classpackage;

import java.time.LocalDate;
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
    private LocalDate deadline; //Consists of date and time
    private String status;

    public Order(int orderId, int customerId, int subscriptionId, String customerRequests, LocalDate deadline,
                 double price, String address, String status) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.subscriptionId = subscriptionId;
        this.customerRequests = customerRequests;
        this.deadline = deadline;
        this.price = price;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}