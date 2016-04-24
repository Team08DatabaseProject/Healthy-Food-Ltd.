package classpackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by paul thomas on 24.04.2016.
 */
public class MealType {
    private IntegerProperty mealTypeId= new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();

//    From database
    public MealType(int mealTypeId, String name) {
        this.mealTypeId.set(mealTypeId);
        this.name.set(name);
    }

//    To database
    public MealType(String name) {
        this.name.set(name);
    }

    public int getMealTypeId() {
        return mealTypeId.get();
    }

    public IntegerProperty mealTypeIdProperty() {
        return mealTypeId;
    }

    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId.set(mealTypeId);
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
}