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

    public GridPane ordersTable; // Retrieves TableView with fx:id="ordersTable"
    public TableView subsTable; // Retrieves Tableview with fx:id="subsTable"

    public Button ordersButton; //Button for showing orders
    public Button subsButton;

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

    /*EventHandler<ActionEvent> subsEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SubsTable.fxml"));
                subsTable = loader.load();
                rootPaneSales.setCenter(subsTable);
                ObservableList<TableColumn> columns = subsTable.getColumns();
                columns.get(0).setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("subscription"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer, String>("businessName"));
                columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
                columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
                columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));
                columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
                columns.get(7).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));
                subsTable.setItems(subsTest);


            }catch (Exception e){
                System.out.println("subsEvent: " + e);
            }
        }
    };*/

    private LocalDate startSubscription = LocalDate.of(2015, 12, 1);
    private LocalDate endSubscription = LocalDate.of(2016, 1, 2);
    private LocalDate startSubscription1 = LocalDate.of(2015, 3, 13);
    private LocalDate endSubscription1 = LocalDate.of(2016, 2, 2);

    ZipCode zip = new ZipCode(7031, "Trondheim");
    ZipCode zip1 = new ZipCode(7042, "Trondheim");

    Address addressTest = new Address("Bugata 5", 7031, "Oslo");
    Address addressTest2 = new Address("Bugata 7", 7042, "Trondheim");

    LocalDate subStart = LocalDate.of(2015, 2, 11);
    LocalDate subEnd = LocalDate.of(2016, 2, 11);
    LocalDate deadline = LocalDate.of(2016, 9, 11);

    Dish dish = new Dish(20, "Ravioli", null);

    final ObservableList<OrderLine> orderLines = FXCollections.observableArrayList(
            new OrderLine(dish, 2)
    );

    final ObservableList<Order> order = FXCollections.observableArrayList(
            new Order("More pickles", deadline, 367.00, "Created", orderLines),
            new Order("Less pickles", deadline, 367.00, "Created", orderLines),
            new Order("More pickles", deadline, 367.00, "Created", orderLines),
            new Order("More pickles", deadline, 367.00, "Created", orderLines)
    );

    final ObservableList<Order> order1 = FXCollections.observableArrayList(
            new Order("More pickles", deadline, 367.00, "Created", orderLines),
            new Order("Less pickles", deadline, 507.00, "In preparation", orderLines),
            new Order("More pickles", deadline, 800.00, "Created", orderLines),
            new Order("More pickles", deadline, 1245.00, "In delivery", orderLines)
    );

    Subscription subscription = new Subscription(subStart, subEnd, order);
    Subscription subscription1 = new Subscription(subStart, subEnd, order1);

    final ObservableList<Customer> customersSinSub = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, ""),
            new Customer(true, "truls@gmail.com", "Truls", "Knudsen", 41333183, addressTest2, "Business1"),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, ""),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest2, "")
    );

    final ObservableList<Customer> customersConSub = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, "", subscription, order),
            new Customer(true, "truls@gmail.com", "Truls", "Ola", 41333183, addressTest2, "Business1", subscription1, order1),
            new Customer(false, "pål@gmail.com", "Pål", "Arnfinnsson", 41333183, addressTest, "", subscription, order),
            new Customer(false, "knut@gmail.com", "Knut", "Ludvigsen", 41333183, addressTest2, "", subscription1, order1)
    );

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
        //subsTable.setItems(subs);

    }
}
