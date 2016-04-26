package classpackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by paul thomas on 17.03.2016.
 */

/**
 * Class defining Subscription objects.
 */
public class Subscription {
    // Each subscription has an id in the database:
    private IntegerProperty subscriptionId = new SimpleIntegerProperty();
    //LocalDate.of(2012, Month.DECEMBER, 12); // from values
    // The date the subscription started:
    private LocalDate startSubscription;
    // The date the subscription ended:
    private LocalDate endSubscription;
    // ObservableList of all orders in a Subscription:
    private ObservableList<Order> ordersOnThisSubscription = FXCollections.observableArrayList();
    // The id of the customer tied to a subscription
    private int customerId;

    /**
     * Constructor used when reading from the database
     * @param subscriptionId
     * @param startSubscription
     * @param endSubscription
     * @param ordersOnThisSubscription
     * @param customerId
     */
    public Subscription(int subscriptionId, LocalDate startSubscription, LocalDate endSubscription,
                        ObservableList ordersOnThisSubscription, int customerId) {
        this.subscriptionId.set(subscriptionId);
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.ordersOnThisSubscription = ordersOnThisSubscription;
        this.customerId = customerId;
    }

    // To database

    /**
     * Constructor used when writing to the database
     * @param startSubscription
     * @param endSubscription
     * @param ordersOnThisSubscription
     */
    public Subscription(LocalDate startSubscription, LocalDate endSubscription,
                        ObservableList ordersOnThisSubscription) {
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }

    /**
     * getter for subscriptionId
     * @return IntegerProperty subscriptionId
     */
    public int getSubscriptionId() {
        return subscriptionId.get();
    }

    /**
     *
     * @return subscriptionId
     */
    public IntegerProperty subscriptionIdProperty() {
        return subscriptionId;
    }

    /**
     * setter for subsriptionId
     * @param subscriptionId
     */
    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId.set(subscriptionId);
    }

    /**
     * getter for startSubscription
     * @return startSubscription
     */
    public LocalDate getStartSubscription() {
        return startSubscription;
    }

    /**
     * setter for startSubscription
     * @param startSubscription
     */
    public void setStartSubscription(LocalDate startSubscription) {
        this.startSubscription = startSubscription;
    }

    /**
     * getter for endSubscription
     * @return endSubscription
     */
    public LocalDate getEndSubscription() {
        return endSubscription;
    }

    /**
     * setter for endSubscription
     * @param endSubscription
     */
    public void setEndSubscription(LocalDate endSubscription) {
        this.endSubscription = endSubscription;
    }

    /**
     * getter for ordersOnThisSubscription
     * @return ordersOnThisSubscription
     */
    public ObservableList<Order> getOrdersOnThisSubscription() {
        return ordersOnThisSubscription;
    }

    /**
     * setter for ordersOnThisSubscription
     * @param ordersOnThisSubscription
     */
    public void setOrdersOnThisSubscription(ObservableList<Order> ordersOnThisSubscription) {
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }

    /**
     * getter for customerId
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }
}
