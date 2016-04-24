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

    // From database
    public Ingredient(int ingredientId, String ingredientName, String unit, double quantityOwned, double price, Supplier supplier) {
        this.ingredientId.set(ingredientId);
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
        this.supplier.set(supplier);
    }

    // From database without Supplier object
    public Ingredient(int ingredientId, String ingredientName, String unit, double quantityOwned, double price) {
        this.ingredientId.set(ingredientId);
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
    }

    // to database
    public Ingredient(String ingredientName, String unit, double quantityOwned, double price, Supplier supplier) {
        this.ingredientName.set(ingredientName);
        this.unit.set(unit);
        this.quantityOwned.set(quantityOwned);
        this.price.set(price);
        this.supplier.set(supplier);
    }

    public void setChanged(boolean bln) {
        changed = bln;
    }

    public boolean isChanged() {
        return changed;
    }

    public int getIngredientId() {
        return ingredientId.get();
    }

    public IntegerProperty ingredientIdProperty() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId.set(ingredientId);
    }

    public String getIngredientName() {
        return ingredientName.get();
    }

    public StringProperty ingredientNameProperty() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName.set(ingredientName);
    }

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public double getQuantityOwned() {
        return quantityOwned.get();
    }

    public DoubleProperty quantityOwnedProperty() {
        return quantityOwned;
    }

    public void setQuantityOwned(double quantityOwned) {
        this.quantityOwned.set(quantityOwned);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getSupplierId() {
        return supplierId.get();
    }

    public IntegerProperty supplierIdProperty() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId.set(supplierId);
    }

    public Supplier getSupplier() {
        return supplier.get();
    }

    public ObjectProperty<Supplier> supplierProperty() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier.set(supplier);
    }

    public String getQuantityAndUnit() {
        return quantityOwned.get() + " " + unit.get();
    }

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