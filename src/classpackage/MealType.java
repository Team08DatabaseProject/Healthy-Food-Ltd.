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

    /**
     * Creates a MealType object, defining the type of meal (from the database)
     *
     * @param mealTypeId the mealtype unique ID
     * @param name name of the meal type
     */
    public MealType(int mealTypeId, String name) {
        this.mealTypeId.set(mealTypeId);
        this.name.set(name);
    }

    /**
     * Creates a MealType object, defining the type of meal
     *
     * @param name name of the meal type
     */
    public MealType(String name) {
        this.name.set(name);
    }

    /**
     * Returns the unique identifier
     *
     * @return int ID of the meal type
     */
    public int getMealTypeId() {
        return mealTypeId.get();
    }

    /**
     * Returns the unique identifier in style for JavaFX (IntegerProperty)
     *
     * @return int ID of the meal type
     */
    public IntegerProperty mealTypeIdProperty() {
        return mealTypeId;
    }

    /**
     * Sets the id of the meal type
     *
     * @param mealTypeId the id
     */
    public void setMealTypeId(int mealTypeId) {
        this.mealTypeId.set(mealTypeId);
    }

    /**
     * Returns the name of the meal type
     *
     * @return name of the meal type
     */
    public String getName() {
        return name.get();
    }

    /**
     * Returns the name of the meal type in style for JavaFX (StringProperty)
     *
     * @return name of the meal type
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * Sets the name of the meal type
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * String representation of the object (returns the name of the meal type)
     */
    @Override
    public String toString() {
        return name.get();
    }
}