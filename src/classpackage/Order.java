package classpackage;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by paul thomas on 17.03.2016.
 */
class Order {
    private double price;
    private int orderId;
    private String customerRequest;
    private LocalDateTime deliverDate;

    public Order(int orderId, String customerRequest, LocalDateTime deliverDate, LocalDateTime datDelivered, double price, String adress) {
        this.orderId = orderId;
        this.customerRequest = customerRequest;
        this.deliverDate = deliverDate;
        this.datDelivered = datDelivered;
        this.price = price;
        this.adress = adress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerRequest() {
        return customerRequest;
    }

    public void setCustomerRequest(String customerRequest) {
        this.customerRequest = customerRequest;
    }

    public LocalDateTime getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }

    public LocalDateTime getDatDelivered() {
        return datDelivered;
    }

    public void setDatDelivered(LocalDateTime datDelivered) {
        this.datDelivered = datDelivered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    private LocalDateTime datDelivered;

    private String adress; //delivery adress
}
