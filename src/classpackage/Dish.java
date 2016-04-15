package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Dish {
    private int dishId;
    private double price;
    private String dishName;
    private ObservableList<DishLine> dishLines = FXCollections.observableArrayList();

    public Dish(int dishId, double price, String dishName, ObservableList dishLines) {
        this.dishId = dishId;
        this.price = price;
        this.dishName = dishName;
        this.dishLines = dishLines;
    }

    public Dish(double price, String dishName, ObservableList dishLines) {
        this.price = price;
        this.dishName = dishName;
        this.dishLines = dishLines;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String toString() {
        return dishName;
    }

    public ObservableList<DishLine> getAllDishLinesForThisDish() {
        return dishLines;
    }

    public void setAllDishLinesForThisDish(ObservableList<DishLine> dishLines) {
        this.dishLines = dishLines;
    }

}
