package classpackage;

import javafx.beans.property.*;

/** Created by Axel
 * 11.03.2016
 */

public class Ingredient {
    private IntegerProperty ingredientId = new SimpleIntegerProperty();
    private final StringProperty ingredientName =new SimpleStringProperty();
    private final StringProperty unit = new SimpleStringProperty();
    private DoubleProperty quantityOwned = new SimpleDoubleProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private IntegerProperty supplierId = new SimpleIntegerProperty();
    private ObjectProperty<Supplier> supplier = new SimpleObjectProperty<>();
    private boolean changed = false;

    /**
     * Creates the ingredient object from the database
     *
     * @param ingredientId the unique identifier for the dish object
     * @param ingredientName the ingredient name
     * @param unit units used by the ingredient
     * @param quantityOwned quantity owned in the stock
     * @param price price of the ingredient
     * @param supplier the supplier (object supplier)
     */
    public Ingredient(int ingredientId, String ingredientName, String unit, double quantityOwned, double price, Supplier supplier) {
        this.ingredientId.set(ingredientId);
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
        this.supplier.set(supplier);
    }

    /**
     * Creates the ingredient object to the database
     *
     * @param ingredientId the unique identifier for the dish object
     * @param ingredientName the ingredient name
     * @param unit units used by the ingredient
     * @param quantityOwned quantity owned in the stock
     * @param price price of the ingredient
     */
    public Ingredient(int ingredientId, String ingredientName, String unit, double quantityOwned, double price) {
        this.ingredientId.set(ingredientId);
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
    }

    /**
     * Creates the ingredient object
     *
     * @param ingredientName the ingredient name
     * @param unit units used by the ingredient
     * @param quantityOwned quantity owned in the stock
     * @param price price of the ingredient
     * @param supplier the supplier (object supplier)
     */
    public Ingredient(String ingredientName, String unit, double quantityOwned, double price, Supplier supplier) {
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
        this.supplier.set(supplier);
    }
    /**
     * Sets if the object has been changed
     *
     * @param boolean true or false if it was changed
     */
    public void setChanged(boolean bln) {
        changed = bln;
    }

    /**
     * Returns if the object was changed
     *
     * @return true if it was changed
     */
    public boolean isChanged() {
        return changed;
    }

    /**
     * Returns the ingredient unique identifier
     *
     * @return true if it was changed
     */
    public int getIngredientId() {
        return ingredientId.get();
    }

    /**
     * Returns the ingredient unique identifier in style for JavaFX (IntegerProperty)
     *
     * @return true if it was changed
     */
    public IntegerProperty ingredientIdProperty() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId.set(ingredientId);
    }

    /**
     * Returns the ingredient name
     *
     * @return ingredient name
     */
    public String getIngredientName() {
        return ingredientName.get();
    }

    /**
     * Returns the ingredient name in style for JavaFX (StringProperty)
     *
     * @return ingredient name
     */
    public StringProperty ingredientNameProperty() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName.set(ingredientName);
    }

    /**
     * Returns the ingredient unit
     *
     * @return ingredient unit
     */
    public String getUnit() {
        return unit.get();
    }

    /**
     * Returns the ingredient unit in style for JavaFX (StringProperty)
     *
     * @return ingredient unit
     */
    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    /**
     * Returns the amount of ingredient owned
     *
     * @return amount of ingredient owned
     */
    public double getQuantityOwned() {
        return quantityOwned.get();
    }

    /**
     * Returns the amount of ingredient owned in style for JavaFX (DoubleProperty)
     *
     * @return amount of ingredient owned
     */
    public DoubleProperty quantityOwnedProperty() {
        return quantityOwned;
    }

    public void setQuantityOwned(double quantityOwned) {
        this.quantityOwned.set(quantityOwned);
    }

    /**
     * Returns the price of ingredient owned
     *
     * @return price of ingredient
     */
    public double getPrice() {
        return price.get();
    }

    /**
     * Returns the price of ingredient owned in style for JavaFX (DoubleProperty)
     *
     * @return price of ingredient
     */
    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    /**
     * Returns the id of the supplier
     *
     * @return id of supplier
     */
    public int getSupplierId() {
        return supplierId.get();
    }

    /**
     * Returns the id of the supplier in style for JavaFX (IntegerProperty)
     *
     * @return id of supplier
     */
    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    /**
     * Returns the the supplier
     *
     * @return supplier
     */
    public Supplier getSupplier() {
        return supplier.get();
    }

    /**
     * Returns the the supplier in style for JavaFX (ObjectProperty)
     *
     * @return supplier
     */
    public ObjectProperty<Supplier> supplierProperty() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    /**
     * Returns the id of the supplier in style for JavaFX (IntegerProperty)
     *
     * @return id of supplier
     */
    public String getQuantityAndUnit() {
        return quantityOwned.get() + " " + unit.get();
    }

    /**
     * String representation of the object (returns all its memebers in a array like string)
     */
    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", unit='" + unit + '\'' +
                ", quantityOwned=" + quantityOwned +
                ", price=" + price +
                ", supplierId=" + supplierId +
                ", supplier=" + supplier +
                '}';
    }

    /**
     * Compares if the current ingredient and the given ingredient are the same
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Ingredient) {
            return (ingredientId.get() == ((Ingredient) o).getIngredientId());
        } else if(o instanceof POrderLine) {
            System.out.println(((POrderLine) o).getIngredient().getIngredientId() + " " + ingredientId.get());
            return (ingredientId.get() == ((POrderLine) o).getIngredient().getIngredientId());
        }
        return false;
    }
}