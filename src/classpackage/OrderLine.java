package classpackage;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

/**
 * Created by paul thomas on 13.04.2016.
 */
public class OrderLine {
    private ObjectProperty<Dish> dish = new SimpleObjectProperty<>();
    private IntegerProperty amount = new SimpleIntegerProperty();
    private NumberBinding total;
    private boolean changed = false;
    private boolean original = true;

    /**
     * Create the OrderLine object
     *
     * @param dish sets the dish of the OrderLine
     * @param amount sets the amount of dishes
     */
    public OrderLine(Dish dish, int amount) {
        this.dish.set(dish);
        this.amount.set(amount);
        total = this.amount.multiply(dish.getPrice());
    }

    /**
     * Create the OrderLine object
     *
     * @param dish sets the dish of the OrderLine
     */
    public OrderLine(Dish dish) {
        this.dish.set(dish);
        this.amount.set(1);
        total = this.amount.multiply(dish.getPrice());
    }

    /**
     * Returns the dish associated with the OrderLine
     *
     * @return the dish associated with the OrderLine
     */
    public Dish getDish() {
        return dish.get();
    }

    /**
     * Returns the dish associated with the OrderLine in style for JavaFX (ObjectProperty)
     *
     * @return the dish associated with the OrderLine
     */
    public ObjectProperty<Dish> dishProperty() {
        return dish;
    }

    /**
     * Sets the dish of the OrderLine
     *
     * @param dish the dish
     */
    public void setDish(Dish dish) {
        this.dish.set(dish);
    }

    /**
     * Returns the amount of dishes associated with the OrderLine
     *
     * @return the amount of dishes associated with the OrderLine
     */
    public int getAmount() {
        return amount.get();
    }

    /**
     * Returns the amount of dishes associated with the OrderLine in style for JavaFX (IntegerProperty)
     *
     * @return the amount of dishes associated with the OrderLine
     */
    public IntegerProperty amountProperty() {
        return amount;
    }

    /**
     * Sets the amount of dish of the OrderLine
     *
     * @param amount amount of dish
     */
    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    /**
     * Returns the total price associated with the OrderLine
     *
     * @return the total price associated with the OrderLine
     */
    public double getTotal() {
        return total.doubleValue();
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean bln) {
        changed = bln;
    }

    public boolean isOriginal() {
        return original;
    }

    public void setOriginal(boolean bln) {
        original = bln;
    }
}
