package classpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by paul thomas on 17.03.2016.
 */

public class Order {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty customerRequests = new SimpleStringProperty(); //Additional info from customer
    private LocalDate actualDeliveryDate; //Is set when order is delivered. Default null
    private LocalDate deadline; //Consists of date and time
    private ObjectProperty<LocalDateTime> deadlineTime = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDateTime> actualDeliveryDateTime = new SimpleObjectProperty<>();
    private ObjectProperty<OrderStatus> status = new SimpleObjectProperty<>();
    private ObjectProperty<Address> address = new SimpleObjectProperty<>();
    private ObservableList<OrderLine> dishesInThisOrder = FXCollections.observableArrayList();
    private boolean changed = false;
    private boolean partOfSubscription = false;

    /**
     * Creates an Order object from database
     *
     * @param orderId the unique identifier of the order
     * @param customerRequests customer flexible requests
     * @param deadline deadline for production
     * @param actualDeliveryDate actual delivered date
     * @param status status of delivery
     * @param dishesInThisOrder all the dishes in the current order
     * @param address address for delivery
     */
    public Order(int orderId, String customerRequests, LocalDate deadline,
                 LocalDate actualDeliveryDate, double price,
                 OrderStatus status, ObservableList dishesInThisOrder, Address address) {
        this.orderId.set(orderId);
        this.customerRequests.set(customerRequests);
        this.deadline = deadline;
        this.price.set(price);
        this.status.set(status);
        this.actualDeliveryDate = actualDeliveryDate;
        this.dishesInThisOrder = dishesInThisOrder;
        this.address.set(address);
    }


    /**
     * Creates an Order object from database (Revised)
     *
     * @param orderId the unique identifier of the order
     * @param customerRequests customer flexible requests
     * @param deadline deadline for production
     * @param actualDeliveryDate actual delivered date
     * @param price price of the order
     * @param status status of delivery
     * @param dishesInThisOrder all the dishes in the current order
     * @param address address for delivery
     */
    public Order(int orderId, String customerRequests, LocalDateTime deadlineTime,
                 LocalDateTime actualDeliveryDateTime, double price,
                 OrderStatus status, ObservableList dishesInThisOrder, Address address) {
        this.orderId.set(orderId);
        this.customerRequests.set(customerRequests);
        this.deadlineTime.set(deadlineTime);
        this.price.set(price);
        this.status.set(status);
        this.actualDeliveryDateTime.set(actualDeliveryDateTime);
        this.dishesInThisOrder = dishesInThisOrder;
        this.address.set(address);
    }

    /**
     * Creates an Order object to database
     *
     * @param customerRequests customer flexible requests
     * @param deadline deadline for production
     * @param price price of the order
     * @param status status of delivery
     * @param dishesInThisOrder all the dishes in the current order
     */
    public Order(String customerRequests, LocalDate deadline, double price,
                 OrderStatus status, ObservableList dishesInThisOrder) {
        this.customerRequests.set(customerRequests);
        this.deadline = deadline;
        this.price.set(price);
        this.status.set(status);
        actualDeliveryDate = null;
        this.dishesInThisOrder = dishesInThisOrder;
    }

    /**
     * Creates an Order object to database (with "LocalDateTime deadlineTime" instead of "LocalDate deadline")
     *
     * @param customerRequests customer flexible requests
     * @param deadline deadline for production
     * @param price price of the order
     * @param status status of delivery
     * @param dishesInThisOrder all the dishes in the current order
     */
    public Order(String customerRequests, LocalDateTime deadlineTime, double price,
                 OrderStatus status, ObservableList dishesInThisOrder, Address address) {
        this.customerRequests.set(customerRequests);
        this.deadlineTime.set(deadlineTime);
        this.price.set(price);
        this.status.set(status);
        actualDeliveryDate = null;
        this.dishesInThisOrder = dishesInThisOrder;
        this.address.set(address);
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

    public OrderStatus getStatus() {
        return status.get();
    }

    public ObjectProperty<OrderStatus> statusProperty() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status.set(status);
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

    public ObservableList<OrderLine> getDishesInThisOrder() {
        return dishesInThisOrder;
    }

    public void setDishesInThisOrder(ObservableList<OrderLine> dishesInThisOrder) {
        this.dishesInThisOrder = dishesInThisOrder;
    }

    public LocalDateTime getDeadlineTime() {
        return deadlineTime.get();
    }

    public ObjectProperty<LocalDateTime> deadlineTimeProperty() {
        return deadlineTime;
    }

    public void setDeadlineTime(LocalDateTime deadlineTime) {
        this.deadlineTime.set(deadlineTime);
    }

    public String deadlineTimeToString() {
        LocalDateTime ldt = deadlineTime.get();
        return "Date " + ldt.getYear() + "/" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "\nTime: "
                + String.format("%02d", ldt.getHour()) + ":" + String.format("%02d", ldt.getMinute());
    }

    /**
     * Returns the actual delivered date
     *
     * @return  the actual delivered date
     */
    public LocalDateTime getActualDeliveryDateTime() {
        return actualDeliveryDateTime.get();
    }

    /**
     * Returns the actual delivered date in style for JavaFX (ObjectProperty)
     *
     * @return  the actual delivered date
     */
    public ObjectProperty<LocalDateTime> actualDeliveryDateTimeProperty() {
        return actualDeliveryDateTime;
    }

    public void setActualDeliveryDateTime(LocalDateTime actualDeliveryDateTime) {
        this.actualDeliveryDateTime.set(actualDeliveryDateTime);
    }

    /**
     * Returns the actual delivered date in a nice string format
     *
     * @return  the actual delivered date in a nice string format
     */
    public String actualDeliveryDateTimeToString() {
        LocalDateTime ldt = actualDeliveryDateTime.get();
        return "Date " + ldt.getYear() + "/" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "\nTime: "
                + String.format("%02d", ldt.getHour()) + ":" + String.format("%02d", ldt.getMinute());
    }

    /**
     * Checks if the order has been changed
     *
     * @return true if the order was changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Set if the order has been changed
     *
     * @param bln sets if the order was changed
     */
    public void setChanged(boolean bln) {
        changed = bln;
    }

    public boolean isPartOfSubscription() {
        return partOfSubscription;
    }

    public void setPartOfSubscription(boolean bln) {
        partOfSubscription = bln;
    }


}