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

    public Dish(int dishId, double price, String dishName, ObservableList dishLines) {
        this.dishId.set(dishId);
        this.price.set(price);
        this.dishName.set(dishName);
        this.dishLines = dishLines;
    }

    public Dish(double price, String dishName, ObservableList dishLines) {
        this.price.set(price);
        this.dishName.set(dishName);
        this.dishLines = dishLines;
    }

    public int getDishId() {
        return dishId.get();
    }

    public IntegerProperty dishIdProperty() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId.set(dishId);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getDishName() {
        return dishName.get();
    }

    public StringProperty dishNameProperty() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName.set(dishName);
    }

    public ObservableList<DishLine> getAllDishLinesForThisDish() {
        return dishLines;
    }

    public void setAllDishLinesForThisDish(ObservableList<DishLine> dishLines) {
        this.dishLines = dishLines;
    }

}
