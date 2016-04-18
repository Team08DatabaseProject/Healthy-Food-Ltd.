package classpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by paul thomas on 07.04.2016.
 */
public class MenuLine {
    private ObjectProperty<Dish> dish = new SimpleObjectProperty<>();
    private IntegerProperty amount = new SimpleIntegerProperty();
    private DoubleProperty priceFactor = new SimpleDoubleProperty();

    public MenuLine(Dish dish, int amount, double priceFactor) {
        this.dish.set(dish);
        this.amount.set(amount);
        this.priceFactor.set(priceFactor);
    }

    public MenuLine(Dish dish, int amount) {
        this.dish.set(dish);
        this.amount.set(amount);
        this.priceFactor.set(1);
    }

    public MenuLine(Dish dish) {
        this.dish.set(dish);
        this.amount.set(1);
        this.priceFactor.set(1);
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

    public double getPriceFactor() {
        return priceFactor.get();
    }

    public DoubleProperty priceFactorProperty() {
        return priceFactor;
    }

    public void setPriceFactor(double priceFactor) {
        this.priceFactor.set(priceFactor);
    }

    // TODO: 10.04.2016 Probably needs revision, due to GUI representation
    @Override
    public String toString() {
        return "MenuLine{" +
                "dish=" + dish +
                '}';
    }
}
