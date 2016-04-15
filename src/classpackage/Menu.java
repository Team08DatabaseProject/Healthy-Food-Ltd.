package classpackage;

import javafx.beans.property.SimpleStringProperty;
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
    private SimpleStringProperty name;
    private SimpleStringProperty mealType;
    private ObservableList<MenuLine> menuLines = FXCollections.observableArrayList();

    // from database
    public Menu(int menuId, String name, String mealType, ObservableList menuLines) {
        this.menuId = menuId;
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
    }

    // to database
    public Menu(String name, String mealType, ObservableList menuLines) {
        this.name.set(name);
        this.mealType.set(mealType);
        this.menuLines = menuLines;
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

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMealType() {
        return mealType.get();
    }

    public SimpleStringProperty mealTypeProperty() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType.set(mealType);
    }

    public ObservableList<MenuLine> getMenuLines() {
        return menuLines;
    }

    public void setMenuLines(ObservableList<MenuLine> menuLines) {
        this.menuLines = menuLines;
    }
}