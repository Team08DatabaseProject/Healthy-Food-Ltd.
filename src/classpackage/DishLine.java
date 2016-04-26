package classpackage;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Created by paul thomas on 10.04.2016.
 */
public class DishLine {
    private ObjectProperty<Ingredient> ingredient = new SimpleObjectProperty<>();
    private DoubleProperty amount = new SimpleDoubleProperty();
    private NumberBinding total;
    private String unitAndAmount;

    /**
     * Creates the DishLine object
     *
     * @param ingredient the ingredient
     * @param amount the amount of ingredient
     */
    public DishLine(Ingredient ingredient, double amount) {
        this.ingredient.set(ingredient);
        this.amount.set(amount);
        total = this.amount.multiply(ingredient.priceProperty());
    }

    /**
     * Creates the DishLine object
     *
     * @param ingredient the ingredient
     */
    public DishLine(Ingredient ingredient) {
        this.ingredient.set(ingredient);
        this.amount.set(1);
        total = this.amount.multiply(ingredient.priceProperty());
    }

    /**
     * Returns the ingredient object
     *
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient.get();
    }

    /**
     * Returns the ingredient object in style for JavaFX (ObjectProperty)
     *
     * @return the ingredient
     */
    public ObjectProperty<Ingredient> ingredientProperty() {
        return ingredient;
    }

    /**
     * Sets the ingredient object to the DishLine
     *
     * @param ingredient the ingredient object
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient.set(ingredient);
    }

    /**
     * Returns the amount of ingredient
     *
     * @return the amount of ingredient
     */
    public double getAmount() {
        return amount.get();
    }

    /**
     * Returns the amount of ingredient in style for JavaFX (DoubleProperty)
     *
     * @return the amount of ingredient
     */
    public DoubleProperty amountProperty() {
        return amount;
    }

    /**
     * Sets the amount of ingredient to the DishLine
     *
     * @param amount the amount of ingredient
     */
    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    /**
     * Returns the total price of the DishLine by multiplying the price and the amount
     *
     * @return the total price of the DishLine by multiplying the price and the amount
     */
    public double getTotal() {
        return total.getValue().doubleValue();
    }

    /**
     * Returns the amount of ingredient and its units
     *
     * @return the amount of ingredient and its units
     */
    public String getUnitAndAmount() {
        unitAndAmount = getAmount() + " " + getIngredient().getUnit();
        return unitAndAmount;
    }


    /**
     * String representation of the object (returns the ingredient and amount in an array like string)
     */
    @Override
    public String toString() {
        return "DishLine{" +
                "ingredient=" + ingredient +
                ", amount=" + amount +
                '}';
    }
}
