package users.sales;

import classpackage.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * Created by Trym Todalshaug on 06/04/2016.
 */
/*public class TrymsMain extends Application {
    public static void main(String[] args) {
    launch(args);
        ControllerSales sales = new ControllerSales();
        ControllerSalesEdit salesEdit = new ControllerSalesEdit();

        ZipCode zip = new ZipCode(7031, "Trondheim");
        ZipCode zip1 = new ZipCode(7042, "Trondheim");

        Address address = new Address(1, "Bugata 5", zip);
        Address address1 = new Address(2, "Bugata 7", zip1);

        LocalDate subStart = LocalDate.of(2015, 02, 11);
        LocalDate subEnd = LocalDate.of(2016, 02, 11);
        Subscription subscription = new Subscription(subStart, subEnd);

        Customer customer = new Customer(false, "arne@gmail.com", "Arne", "Knudsen",
                                        41333183, address, "", subscription);
        Customer customer1 = new Customer(false, "arne@gmail.com", "Arne", "Knudsen",
                41333183, address1, "", subscription);

        ObservableList<Dish> dishes = FXCollections.observableArrayList();
        Dish dish = new Dish(20, "Ravioli");

        LocalDate deadline = LocalDate.of(2016, 04, 10);
        Order order = new Order(subscription, "Hot Ravioli", deadline, 300, "CREATED", customer, dishes);
    }

    public void start(Stage primaryStage){
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("SalesWindow.fxml"));
        }catch (Exception exc){
            System.out.println("");
        }
    }
}
*/