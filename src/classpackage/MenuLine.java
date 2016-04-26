package classpackage;

import javafx.beans.binding.NumberBinding;
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
    private NumberBinding totalPrice;   // dish price * dish amount * menuLine price factor
    private boolean changed = false;
    private boolean newlyCreated = false;

    /**
     * Creates a MenuLine object
     *
     * @param dish the dish
     * @param amount the amount of the dish
     * @param priceFactor the price factor
     */
    public MenuLine(Dish dish, int amount, double priceFactor) {
        this.dish.set(dish);
        this.amount.set(amount);
        this.priceFactor.set(priceFactor);
        totalPrice = this.amount.multiply(this.getDish().getPrice()).multiply(this.priceFactor);
    }

    /**
     * Creates a MenuLine object
     *
     * @param dish the dish
     * @param amount the amount of the dish
     */
    public MenuLine(Dish dish, int amount) {
        this.dish.set(dish);
        this.amount.set(amount);
        this.priceFactor.set(1);
        totalPrice = this.amount.multiply(this.getDish().getPrice()).multiply(priceFactor);
    }

    /**
     * Creates a MenuLine object
     *
     * @param dish the dish
     */
    public MenuLine(Dish dish) {
        this.dish.set(dish);
        this.amount.set(1);
        this.priceFactor.set(1);
        totalPrice = this.amount.multiply(this.getDish().getPrice()).multiply(priceFactor);
    }

    /**
     * Returns the dish object associated witht he MenuLine
     *
     * @return the dish object associated witht he MenuLine
     */
    public Dish getDish() {
        return dish.get();
    }

    /**
     * Returns the dish object associated witht he MenuLine
     *
     * @return the dish object associated witht he MenuLine
     */
    public ObjectProperty<Dish> dishProperty() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish.set(dish);
    }

    /**
     * Returns the amount of dish
     *
     * @return the amount of dish
     */
    public int getAmount() {
        return amount.get();
    }

    /**
     * Returns the amount of dish
     *
     * @return the amount of dish
     */
    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    /**
     * Returns the price factor of the MenuLine
     *
     * @return the price factor of the MenuLine
     */
    public double getPriceFactor() {
        return priceFactor.get();
    }

    /**
     * Returns the price factor of the MenuLine
     *
     * @return the price factor of the MenuLine
     */
    public DoubleProperty priceFactorProperty() {
        return priceFactor;
    }

    public void setPriceFactor(double priceFactor) {
        this.priceFactor.set(priceFactor);
    }

    /**
     * Returns the total price for the MenuLine
     *
     * @return the total price for the MenuLine
     */
    public double getTotalPrice() {
        return totalPrice.doubleValue();
    }

    /**
     * Checks if the MenuLine was just created
     *
     * @return true if it has just been created
     */
    public boolean isNewlyCreated() {
        return newlyCreated;
    }

    public void setNewlyCreated(boolean bln) {
        newlyCreated = bln;
    }

    /**
     * Checks if the MenuLine was changed
     *
     * @return true if it has just been changed
     */
    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean bln) {
        changed = bln;
    }

    /**
     * Compares the current MenuLine with the given MenuLine
     *
     * @return true if both MenuLines are the same
     */
    @Override
    public boolean equals(Object ml) {
        if (!(ml instanceof MenuLine)) {
            return false;
        } else if (this.getDish().getDishId() == ((MenuLine) ml).getDish().getDishId() && this.getAmount() == ((MenuLine) ml).getAmount()) {
            return true;
        }
        return false;
    }

    // TODO: 10.04.2016 Probably needs revision, due to GUI representation
    /**
     * String representation of the object (MenuLine with the dish's name)
     */
    @Override
    public String toString() {
        return "MenuLine{" +
                "dish=" + dish +
                '}';
    }
}
