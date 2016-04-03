package classpackage;

import classpackage.*;

/**
 * Created by paul thomas on 16.03.2016.
 */
class Dish {
    private int dishId;
    private double price;
    private String dishName;

    public Dish(double price, String dishName) {
        this.price = price;
        this.dishName = dishName;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
}
