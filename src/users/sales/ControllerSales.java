package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
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


    @FXML
    public BorderPane rootPaneSales; //RootPane

    public TableView ordersTable; // Retrieves TableView with fx:id="ordersTable"
    public TableView subsTable; // Retrieves Tableview with fx:id="subsTable"

    public Button ordersButton; //Button for showing orders
    public Button subsButton;
    //public Button deleteOrderButton; //Button for deleting an order

    private SqlQueries query = new SqlQueries();
   // final ObservableList<Order> orderTest = query.getOrders(4);


    EventHandler<ActionEvent> subsEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SubsTable.fxml"));
                ordersTable = loader.load();
                rootPaneSales.setCenter(subsTable);
                ObservableList<TableColumn> columns = subsTable.getColumns();
                columns.get(0).setCellValueFactory(new PropertyValueFactory<Subscription, Integer>("subscriptionId"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer, String>("businessName"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
                columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
                columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));
                subsTable.setItems(orderTest);
            }catch (Exception e){
                System.out.println("subsEvent: " + e);
            }
        }
    };

    // Shows a list of orders and their status.


    EventHandler<ActionEvent> orderEvent = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("OrdersTable.fxml"));
                ordersTable = loader.load();
                rootPaneSales.setCenter(ordersTable);
                ObservableList<TableColumn> columns = ordersTable.getColumns();
                columns.get(0).setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Subscription,Integer>("subscriptionId"));
                columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer, String>("businessName"));
                columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
                columns.get(7).setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
                columns.get(8).setCellValueFactory(new PropertyValueFactory<Order,String>("customerRequests"));
                columns.get(9).setCellValueFactory(new PropertyValueFactory<Order,LocalDate>("deadline"));
                columns.get(10).setCellValueFactory(new PropertyValueFactory<Order,Double>("price"));
                columns.get(11).setCellValueFactory(new PropertyValueFactory<Address,String>("address"));
                columns.get(12).setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
                ordersTable.setItems(orderTest);

            } catch(Exception exc) {
                System.out.println("orderEvent: " + exc);
            }
        }
    };

    //fungerer når jeg kommenterer ut denne!


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

    // Shows list of orders with the option to change their status from "Not delivered" to "Delivered".
    /*EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                ordersTable = loader.load();
                gridPaneOrders.setCenter(ordersTable);
            } catch(Exception exc) {
                System.out.println("deleteOrderEvent: " + exc);
            }
        }
    };*/

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // Required method for Initializable, runs at program launch

        ordersButton.setOnAction(orderEvent);
        subsButton.setOnAction(subsEvent);
        //deleteOrderButton.setOnAction(deleteOrderEvent);

    }
}
