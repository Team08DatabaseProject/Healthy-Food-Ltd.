package classpackage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * Created by paul thomas on 16.03.2016.
 */

class Menu {
    private int menuId;
    private String name;
    private String mealType;
    private ObservableList<Dish> dishesForThisMenu = FXCollections.observableArrayList();

    public Menu(int menuId, String name, String mealType) {
        this.menuId = menuId;
        this.name = name;
        this.mealType = mealType;
    }
public Menu(String name, String mealType) {
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

    public ObservableList<Dish> getDishesForThisMenu() {
        return dishesForThisMenu;
    }

    public void setDishesForThisMenu(ObservableList<Dish> dishesForThisMenu) {
        this.dishesForThisMenu = dishesForThisMenu;
    }
}
