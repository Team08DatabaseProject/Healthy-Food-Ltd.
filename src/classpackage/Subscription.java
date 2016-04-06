package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by paul thomas on 17.03.2016.
 */
public class Subscription {
    private int subscriptionId;
    // LocalDate.of(2012, Month.DECEMBER, 12); // from values
    private LocalDate startSubscription;
    private LocalDate endSubscription;
    private ObservableList ordersOnThisSubscription = FXCollections.observableArrayList();


    public Subscription(int subscriptionId, LocalDate startSubscription, LocalDate endSubscription, ObservableList ordersOnThisSubscription) {
        this.subscriptionId = subscriptionId;
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }
    public Subscription(LocalDate startSubscription, LocalDate endSubscription) {
        this.startSubscription = startSubscription;
        this.endSubscription = endSubscription;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public ObservableList getOrdersOnThisSubscription() {
        return ordersOnThisSubscription;
    }

    public void setOrdersOnThisSubscription(ObservableList ordersOnThisSubscription) {
        this.ordersOnThisSubscription = ordersOnThisSubscription;
    }
}
