package classpackage;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by paul thomas on 16.03.2016.
 */

public class Dish {
    private IntegerProperty dishId = new SimpleIntegerProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty dishName = new SimpleStringProperty();
    private ObservableList<DishLine> dishLines = FXCollections.observableArrayList();

    /**
     * Creates the Dish object from the database
     *
     * @param dishId the unique identifier for the dish object
     * @param price the price for the dish
     * @param dishName the name of the dish
     * @param dishLines
     */
    public Dish(int dishId, double price, String dishName, ObservableList dishLines) {
        this.dishId.set(dishId);
        this.price.set(price);
        this.dishName.set(dishName);
        this.dishLines = dishLines;
    }

    /**
     * Creates the Dish object to the database
     *
     * @param price the price for the dish
     * @param dishName the name of the dish
     * @param dishLines
     */
    public Dish(double price, String dishName, ObservableList dishLines) {
        this.price.set(price);
        this.dishName.set(dishName);
        this.dishLines = dishLines;
    }

    /**
     * Returns the unique dish identifier
     *
     * @return int ID of the dish
     */
    public int getDishId() {
        return dishId.get();
    }

    /**
     * Returns the unique dish identifier in style for JavaFX (IntegerProperty)
     *
     * @return IntegerProperty ID of the dish
     */
    public IntegerProperty dishIdProperty() {
        return dishId;
    }

    /**
     * Sets the unique dish identifier
     *
     * @param dishId the Id to be set
     */
    public void setDishId(int dishId) {
        this.dishId.set(dishId);
    }

    /**
     * Returns the price of the dish
     *
     * @return double price of the dish
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns the price of the dish in style for JavaFX (DoubleProperty)
     *
     * @return double price of the dish
     */
    public DoubleProperty priceProperty() {
        return price;
    }

    /**
     * Sets the price of the dish
     *
     * @param price the price to be set
     */
    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Returns the dish name
     *
     * @return string the dish name
     */
    public String getDishName() {
        return dishName.get();
    }

    /**
     * Returns the dish name in style for JavaFX (StringProperty)
     *
     * @return string the dish name
     */
    public StringProperty dishNameProperty() {
        return dishName;
    }

    /**
     * Sets the name of the dish
     *
     * @param dishName the name to be set
     */
    public void setDishName(String dishName) {
        this.dishName.set(dishName);
    }

    /**
     * Gets the dish line of the object
     *
     * @return the ObservableList<DishLine> of the object
     */
    public ObservableList<DishLine> getAllDishLinesForThisDish() {
        return dishLines;
    }

    /**
     * Set all dish lines for the current object
     *
     * @param dishLines dish line to be set
     */
    public void setAllDishLinesForThisDish(ObservableList<DishLine> dishLines) {
        this.dishLines = dishLines;
    }

    /**
     * String representation of the object (returns the name of the dish)
     */
    @Override
    public String toString() {
        return dishName.get();
    }

}
