package classpackage;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
    private StringProperty name = new SimpleStringProperty();
    private ObjectProperty<MealType> mealType = new SimpleObjectProperty<>();
    private ObservableList<MenuLine> menuLines = FXCollections.observableArrayList();
    private DoubleProperty totalPrice = new SimpleDoubleProperty();

    // from database
    public Menu(int menuId, String name, MealType mealType, ObservableList menuLines) {
        this.menuId = menuId;
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    // to database
    public Menu(String name, MealType mealType, ObservableList menuLines) {
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public MealType getMealType() {
        return mealType.get();
    }

    public ObjectProperty<MealType> mealTypeProperty() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType.set(mealType);
    }


    public ObservableList<MenuLine> getMenuLines() {
        return menuLines;
    }

    public void setMenuLines(ObservableList<MenuLine> menuLines) {
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    private void setTotalPrice() {
        double price = 0;
        if (menuLines != null && !menuLines.isEmpty()) {
            for (MenuLine mL : menuLines) {
                price += mL.getTotalPrice();
            }
        }
        totalPrice.set(price);
    }

    public double getTotalPrice() {
        return totalPrice.doubleValue();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }


}