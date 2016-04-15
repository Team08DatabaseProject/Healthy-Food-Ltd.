package classpackage;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by paul thomas on 17.03.2016.
 */
public class Subscription {
    private IntegerProperty subscriptionId;
    //LocalDate.of(2012, Month.DECEMBER, 12); // from values
    private LocalDate startSubscription;
    private LocalDate endSubscription;
    private ObservableList<Order> ordersOnThisSubscription = FXCollections.observableArrayList();
    private int customerId;

    // From database
    public Subscription(int subscriptionId, LocalDate startSubscription, LocalDate endSubscription,
                        ObservableList ordersOnThisSubscription, int customerId) {
        this.subscriptionId.set(subscriptionId);
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.ordersOnThisSubscription = ordersOnThisSubscription;
        this.customerId = customerId;
    }

    // To database
    public Subscription(LocalDate startSubscription, LocalDate endSubscription,
                        ObservableList ordersOnThisSubscription) {
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }

    public int getSubscriptionId() {
        return subscriptionId.get();
    }

    public IntegerProperty subscriptionIdProperty() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId.set(subscriptionId);
    }

    public LocalDate getStartSubscription() {
        return startSubscription;
    }

    public void setStartSubscription(LocalDate startSubscription) {
        this.startSubscription = startSubscription;
    }

    public LocalDate getEndSubscription() {
        return endSubscription;
    }

    public void setEndSubscription(LocalDate endSubscription) {
        this.endSubscription = endSubscription;
    }

    public ObservableList<Order> getOrdersOnThisSubscription() {
        return ordersOnThisSubscription;
    }

    public void setOrdersOnThisSubscription(ObservableList<Order> ordersOnThisSubscription) {
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }

    public int getCustomerId() {
        return customerId;
    }
}
