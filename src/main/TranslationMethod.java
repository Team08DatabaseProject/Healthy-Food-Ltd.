package main;

import classpackage.MenuLine;
import classpackage.OrderLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by axelkvistad on 4/15/16.
 */
public class TranslationMethod {

    public ObservableList<OrderLine> getOrderListFromMenuList(ObservableList<MenuLine> menuLines) {
        OrderLine orderLine;
        ObservableList<OrderLine> orderLineList = FXCollections.observableArrayList();
        for (MenuLine m : menuLines) {
            orderLine = new OrderLine(m.getDish(), m.getAmount());
            orderLineList.add(orderLine);
        }
        return orderLineList;
    }

    public double getOrderPriceFromMenuList(ObservableList<MenuLine> menuLines) {
        double price = 0;
        for (MenuLine m : menuLines) {
            price += (m.getDish().getPrice() * m.getPriceFactor());
        }
        return price;
    }

}
