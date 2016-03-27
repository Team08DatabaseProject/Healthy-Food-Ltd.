package classpackage;

import javafx.collections.ObservableList;

/**
 * Created by axelkvistad on 3/26/16.
 */

public class GetOrdersTest {
    public static void main(String[] args) {
        SqlQueries query = new SqlQueries();
        ObservableList<Order> orders = query.getOrders();
        for (Order o : orders) {
            System.out.println(o.getPrice());
        }
    }
}
