package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by trymrt 28.03.2016
 * Controller for the Sales personnel
 */

public class ControllerSales implements Initializable {

    /*
    ObservableList to get fetched  from database
    */
    private ObservableList<Order> allOrdersForSales = FXCollections.observableArrayList();
    private ObservableList<Subscription> allSubscriptions = FXCollections.observableArrayList();
    private ObservableList<Dish> allDishes = FXCollections.observableArrayList();
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Address> allAdresses = FXCollections.observableArrayList();
    private ObservableList<Menu> allMenus = FXCollections.observableArrayList();

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

    @FXML
    public BorderPane rootPaneSales; //RootPane
    public Button ordersButton; //Button for showing orders
    public Button subsButton;
    public Button customersButton;
    private GridPane ordersTable; // Retrieves TableView with fx:id="ordersTable"
    private GridPane subsTable; // Retrieves Tableview with fx:id="subsTable"
    private GridPane customersTable;

    EventHandler<ActionEvent> orderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("orders/OrdersTable.fxml"));
                ordersTable = loader.load();
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
                subsTable = loader.load();
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
                customersTable = loader.load();
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
