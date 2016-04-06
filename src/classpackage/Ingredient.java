package classpackage;

/** Created by Axel
 * 11.03.2016
 */

public class Ingredient {
    private int ingredientId;
    private final String ingName;
    private final String unit;
    private double quantityOwned;
    private double price;
    private int supplierId;

    // To database
    public Ingredient(String ingName, String unit, double quantityOwned, double price, int supplierId) {
        this.ingName = ingName;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplierId = supplierId;
    }
    // From database
    public Ingredient(int ingredientId,String ingName, String unit, double quantityOwned, double price, int supplierId) {
        this.ingredientId = ingredientId;
        this.ingName = ingName;
        this.unit = unit;
        this.quantityOwned = quantityOwned;
        this.price = price;
        this.supplierId = supplierId;
    }
    public String getIngName() {
        return ingName;
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
}