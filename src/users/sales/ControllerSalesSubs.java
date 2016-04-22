package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 14/04/2016.
 */


public class ControllerSalesSubs extends ControllerSales implements Initializable {

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
    public BorderPane rootPaneSubs; //Root pane for subsTable.fxml
    public TableView subsTable; // Retrieves Tableview with fx:id="subsTable"
    public TableColumn customerIdCol;
    public TableColumn subscriptionIdCol;
    public TableColumn fNameCol;
    public TableColumn lNameCol;
    public BorderPane rootPaneSales; //RootPane for SalesWindow.fxml

    private SqlQueries query = new SqlQueries();
    //final ObservableList<Order> subsTest = query.getOrders(4);

    EventHandler<MouseEvent> infoSubsEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    selectedSubscription = (Subscription) subsTable.getSelectionModel().getSelectedItem();
                    boolean success = false;
                    for (Customer customer : customers) {
                        if (customer.getSubscription() != null) {
                            Subscription subscription = customer.getSubscription();

                            if (subscription.getSubscriptionId() == selectedSubscription.getSubscriptionId() &&
                                    subscription.getCustomerId() == selectedSubscription.getCustomerId()) {
                                selectedCustomer = customer;
                                success = true;
                            }
                        }
                    }
                    if (success) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("SubsInfoTable.fxml"));
                        GridPane subsInfoGrid = loader.load();
                        rootPaneSubs.setBottom(subsInfoGrid);
                    }
                }
            } catch (Exception exc) {
                System.out.println("infoSubsEvent: " + exc);
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

        subsTable.setEditable(true);


        subscriptionIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("subscription")); //subscriptionId
        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId")); //customerId
        fNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName")); //firstName
        lNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName")); //lastName

        subscriptionIdCol.setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                protected void updateItem(Subscription item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item.getSubscriptionId()));
                    }
                }
            };
        });

        subsTable.getColumns().setAll(subscriptionIdCol, customerIdCol, fNameCol, lNameCol);
        subsTable.setOnMousePressed(infoSubsEvent);
        subsTable.setItems(subscriptions);
    }
}