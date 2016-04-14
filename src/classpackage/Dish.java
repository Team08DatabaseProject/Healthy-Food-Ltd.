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
    private ObservableList<Ingredient> allIngredientsForThisDish = FXCollections.observableArrayList();

    public Dish(int dishId, double price, String dishName, ObservableList allIngredientsForThisDish) {
        this.dishId = dishId;
        this.price = price;
        this.dishName = dishName;
        this.allIngredientsForThisDish = allIngredientsForThisDish;
    }

    public Dish(double price, String dishName, ObservableList allIngredientsForThisDish) {
        this.price = price;
        this.dishName = dishName;
        this.allIngredientsForThisDish = allIngredientsForThisDish;
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

    public ObservableList<Ingredient> getAllIngredientsForThisDish() {
        return allIngredientsForThisDish;
    }

    public void setAllIngredientsForThisDish(ObservableList<Ingredient> allIngredientsForThisDish) {
        this.allIngredientsForThisDish = allIngredientsForThisDish;
    }

}
