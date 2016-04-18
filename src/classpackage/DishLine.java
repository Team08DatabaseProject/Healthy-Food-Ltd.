package classpackage;

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

    public DishLine(Ingredient ingredient, double amount) {
        this.ingredient.set(ingredient);
        this.amount.set(amount);
    }

    public DishLine(Ingredient ingredient) {
        this.ingredient.set(ingredient);
        this.amount.set(1);
    }

    public Ingredient getIngredient() {
        return ingredient.get();
    }

    public ObjectProperty<Ingredient> ingredientProperty() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient.set(ingredient);
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
