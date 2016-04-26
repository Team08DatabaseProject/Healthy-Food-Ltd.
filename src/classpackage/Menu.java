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

    /**
     * Creates a menu object from the database
     *
     * @param menuId unique identifier
     * @param name name of the menu
     * @param mealType meal type
     * @param menuLines object with menu lines in style for JavaFX
     */
    public Menu(int menuId, String name, MealType mealType, ObservableList menuLines) {
        this.menuId = menuId;
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    /**
     * Creates a menu object to the database
     *
     * @param name name of the menu
     * @param mealType meal type
     * @param menuLines object with menu lines in style for JavaFX
     */
    public Menu(String name, MealType mealType, ObservableList menuLines) {
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    /**
     * Returns the unique identifier
     *
     * @return int ID
     */
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    /**
     * Returns the name of the menu
     *
     * @return name of menu
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the name of the menu in style for JavaFX (StringProperty)
     *
     * @return name of menu
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Sets the name of the menu
     *
     * @param name name of the menu
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Returns the meal type of the menu
     *
     * @return meal type of menu
     */
    public MealType getMealType() {
        return mealType.get();
    }

    /**
     * Returns the meal type of the menu in style for JavaFX (ObjectProperty)
     *
     * @return meal type of menu
     */
    public ObjectProperty<MealType> mealTypeProperty() {
        return mealType;
    }

    /**
     * Sets the meal Type of the menu
     *
     * @param mealType meal Type of the menu
     */
    public void setMealType(MealType mealType) {
        this.mealType.set(mealType);
    }


    /**
     * Returns the menu lines of the menu in style for JavaFX (ObservableList)
     *
     * @return menu lines of menu
     */
    public ObservableList<MenuLine> getMenuLines() {
        return menuLines;
    }

    /**
     * Sets the Menu Lines  of the menu
     *
     * @param menuLines MenuLines of the menu
     */
    public void setMenuLines(ObservableList<MenuLine> menuLines) {
        this.menuLines = menuLines;
        this.setTotalPrice();
    }

    /**
     * Sets the total price of the menu
     */
    private void setTotalPrice() {
        double price = 0;
        if (menuLines != null && !menuLines.isEmpty()) {
            for (MenuLine mL : menuLines) {
                price += mL.getTotalPrice();
            }
        }
        totalPrice.set(price);
    }

    /**
     * Returns the total price the menu
     *
     * @return total price of the menu
     */
    public double getTotalPrice() {
        return totalPrice.doubleValue();
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }


}