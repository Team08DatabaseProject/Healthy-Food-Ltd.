package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by paul thomas on 16.03.2016.
 */

public class Menu {
    private int menuId;
    private String name;
    private String mealType;
    private Map<Dish, Integer> dishes = new HashMap<>();

    public Menu(int menuId, String name, String mealType) {
        this.menuId = menuId;
        this.name = name;
        this.mealType = mealType;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String toString() {
        return name;
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Dish, Integer> dishes) {
        this.dishes = dishes;
    }

}