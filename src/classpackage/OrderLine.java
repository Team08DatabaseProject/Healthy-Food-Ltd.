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

    public OrderLine(Dish dish, int amount) {
        this.dish.set(dish);
        this.amount.set(amount);
        total = this.amount.multiply(dish.getPrice());
    }

    public OrderLine(Dish dish) {
        this.dish.set(dish);
        this.amount.set(1);
        total = this.amount.multiply(dish.getPrice());
    }

    public Dish getDish() {
        return dish.get();
    }

    public ObjectProperty<Dish> dishProperty() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish.set(dish);
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
