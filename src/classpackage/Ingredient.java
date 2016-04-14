package classpackage;

/** Created by Axel
 * 11.03.2016
 */

public class Ingredient {
    private int ingredientId;
    private final String description;
    private final String unit;
    private double quantityOwned;
    private double price;
    private int supplierId;
    private Supplier supplier;


    // TODO: 10.04.2016 Delete the first two constructors, only temporary because of other written classes
    // To database
    public Ingredient(String description, String unit, double quantityOwned, double price, int supplierId) {
        this.description = description;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplierId = supplierId;
    }

    // From database
    public Ingredient(int ingredientId, String description, String unit, double quantityOwned, double price, int supplierId) {
        this.ingredientId = ingredientId;
        this.description = description;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplierId = supplierId;
    }

    // Constructors to be used under here:
    // From database
    public Ingredient(int ingredientId, String description, String unit, double quantityOwned, double price, Supplier supplier) {
        this.ingredientId = ingredientId;
        this.description = description;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplier = supplier;
    }

    // to database
    public Ingredient(String description, String unit, double quantityOwned, double price, Supplier supplier) {
        this.description = description;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplier = supplier;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public double getQuantityOwned() {
        return quantityOwned;
    }

    public double getPrice() {
        return price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", description='" + description + '\'' +
                ", unit='" + unit + '\'' +
                ", quantityOwned=" + quantityOwned +
                ", price=" + price +
                ", supplierId=" + supplierId +
                ", supplier=" + supplier +
                '}';
    }
}