/** Created by Axel
 * 11.03.2016
 */

public class Ingredient {
    private final String ingName;
    private final String unit;
    private double storage;
    private double price;

    public Ingredient(String ingName, String unit, double storage, double price) {
        this.ingName = ingName;
        this.unit = unit;
        this.storage = storage;
        this.price = price;
    }

    public String getIngName() {
        return ingName;
    }

    public String getUnit() {
        return unit;
    }

    public double getStorage() {
        return storage;
    }

    public double getPrice() {
        return price;
    }
}