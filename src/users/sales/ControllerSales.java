package users.sales;

import classpackage.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by trymrt 28.03.2016
 * Controller for the Sales personnel
 */

public class ControllerSales implements Initializable {

    protected static final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    protected static SqlQueries db = new SqlQueries();
    protected static ObservableList<Supplier> suppliers = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredients = db.getAllIngredients(suppliers);
    protected static ObservableList<Dish> dishes = db.getAllDishes(ingredients);
    protected static ObservableList<Order> orders = db.getOrders(4, dishes);
    protected static ObservableList<Customer> customers = db.getAllCustomers(orders);
    protected static ObservableList<Subscription> subscriptions = db.getAllSubscriptions();
    protected static ObservableList<OrderStatus> statusTypes = db.getStatusTypes();

    protected static Dish selectedDish;
    protected static Order selectedOrder;
    protected static Customer selectedCustomer;
    protected static Subscription selectedSubscription;
    protected static OrderStatus selectedStatus;
    protected static OrderLine selectedOrderLine;
    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy \n HH:mm");

    @FXML
    public BorderPane rootPaneSales; //RootPane
    public Button ordersButton; //Button for showing orders
    public Button subsButton;
    public Button customersButton;




    EventHandler<ActionEvent> orderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("orders/OrdersTable.fxml"));
                GridPane ordersTable = loader.load();
                rootPaneSales.setCenter(ordersTable);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> subsEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("subscriptions/SubsTable.fxml"));
                GridPane subsTable = loader.load();
                rootPaneSales.setCenter(subsTable);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> customersEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("customers/CustomersTable.fxml"));
                GridPane customersTable = loader.load();
                rootPaneSales.setCenter(customersTable);
            }catch (Exception exc){
                System.out.println("customersEvent " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ordersButton.setOnAction(orderEvent);
        subsButton.setOnAction(subsEvent);
        customersButton.setOnAction(customersEvent);
    }
}
