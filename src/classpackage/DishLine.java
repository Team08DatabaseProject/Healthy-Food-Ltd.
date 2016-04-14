package classpackage;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by paul thomas on 10.04.2016.
 */
public class DishLine {
    private Ingredient ingredient;
    private DoubleProperty amount = new SimpleDoubleProperty();

    public DishLine(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount.set(amount);
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    // TODO: 10.04.2016 Probably needs revision do to GUI

    @Override
    public String toString() {
        return "DishLine{" +
                "ingredient=" + ingredient +
                ", amount=" + amount +
                '}';
    }
}
