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

    protected static TestObjects testObjects = new TestObjects();
    protected static ObservableList<Order> orders = testObjects.allOrders;
    protected static ObservableList<Dish> dishes = testObjects.dishList;
    protected static ObservableList<Customer> customers = testObjects.allCustomers;
    protected static ObservableList<Subscription> subscriptions = testObjects.allSubscriptions;
    protected static Dish selectedDish;

    protected static Order selectedOrder;
    protected static Customer selectedCustomer;
    protected static Subscription selectedSubscription;

    @FXML
    public BorderPane rootPaneSales; //RootPane
    public Button ordersButton; //Button for showing orders
    public Button subsButton;
    public Button customersButton;
    private GridPane ordersTable; // Retrieves TableView with fx:id="ordersTable"
    private GridPane subsTable; // Retrieves Tableview with fx:id="subsTable"
    private GridPane customersTable;

    private SqlQueries query = new SqlQueries();
    //final ObservableList<Order> subsTest = query.getOrders(4);

    EventHandler<ActionEvent> orderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("OrdersTable.fxml"));
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
                loader.setLocation(getClass().getResource("SubsTable.fxml"));
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
                loader.setLocation(getClass().getResource("customersTable.fxml"));
                customersTable = loader.load();
                rootPaneSales.setCenter(customersTable);
            }catch (Exception exc){
                System.out.println("customersEvent " + exc);
            }
        }
    };

    public ObservableList<Order> getAllOrdersForSales() {
        return allOrdersForSales;
    }

    public void setAllOrdersForSales(ObservableList<Order> allOrdersForSales) {
        this.allOrdersForSales = allOrdersForSales;
    }

    public ObservableList<Subscription> getAllSubscriptions() {
        return allSubscriptions;
    }

    public void setAllSubscriptions(ObservableList<Subscription> allSubscriptions) {
        this.allSubscriptions = allSubscriptions;
    }

    public ObservableList<Dish> getAllDishes() {
        return allDishes;
    }

    public void setAllDishes(ObservableList<Dish> allDishes) {
        this.allDishes = allDishes;
    }

    public ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(ObservableList<Customer> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public ObservableList<Address> getAllAdresses() {
        return allAdresses;
    }

    public void setAllAdresses(ObservableList<Address> allAdresses) {
        this.allAdresses = allAdresses;
    }

    public ObservableList<Menu> getAllMenus() {
        return allMenus;
    }

    public void setAllMenus(ObservableList<Menu> allMenus) {
        this.allMenus = allMenus;
    }


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ordersButton.setOnAction(orderEvent);
        subsButton.setOnAction(subsEvent);
        customersButton.setOnAction(customersEvent);

    }
}
