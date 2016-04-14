package classpackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by paul thomas on 13.04.2016.
 */
public class OrderLine {
    private Dish dish;
    private IntegerProperty amount = new SimpleIntegerProperty();

    public OrderLine(Dish dish, int amount) {
        this.dish = dish;
        this.amount.set(amount);
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }
}
