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
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

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

    private LocalDate startSubscription = LocalDate.of(2015, 12, 1);
    private LocalDate endSubscription = LocalDate.of(2016, 1, 2);
    private LocalDate startSubscription1 = LocalDate.of(2015, 3, 13);
    private LocalDate endSubscription1 = LocalDate.of(2016, 2, 2);

    Subscription subscription = new Subscription(startSubscription, endSubscription);
    final ObservableList<Subscription> subs = FXCollections.observableArrayList(
            new Subscription(startSubscription, endSubscription),
            new Subscription(startSubscription1, endSubscription1)
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

        subsTable.setEditable(true);
        ObservableList<TableColumn> columns = subsTable.getColumns();
        TableColumn<Customer, Subscription> subscriptionId = columns.get(0);
        TableColumn<Customer, Subscription> startSub = columns.get(1);
        TableColumn<Customer, Subscription> endSub = columns.get(2);
        TableColumn<Customer, Integer> customerId = columns.get(3);
        TableColumn<Customer, String> businessName = columns.get(4);
        TableColumn<Customer, String> fname = columns.get(5);
        TableColumn<Customer, String> lname = columns.get(6);
        TableColumn<Customer, Address> address = columns.get(7);
        TableColumn<Customer, String> email = columns.get(8);
        TableColumn<Customer, Integer> phoneNumber = columns.get(9);

        columns.get(0).setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("subscription"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer, String>("businessName"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));
        columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        columns.get(7).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));

        columns.get(0).setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                protected void updateItem(Subscription item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item.getSubscriptionId()));
                    }
                }
            };
        });
        startSub.setCellFactory(new Callback<TableColumn<Subscription, LocalDate>, TableCell<Subscription, LocalDate>>() {
            @Override
            public TableCell<Subscription, LocalDate> call(TableColumn<Subscription, LocalDate> param) {
                DatePickerCell datePick = new DatePickerCell(subs);
                return datePick;
            }
        });
        columns.get(1).setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                protected void updateItem(Subscription item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item.getStartSubscription()));
                    }
                }
            };
        });
        columns.get(2).setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                protected void updateItem(Subscription item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getStartSubscription());
                    }
                }
            };
        });
        columns.get(5).setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                protected void updateItem(Address item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getAddress());
                    }
                }
            };
        });

        ordersButton.setOnAction(orderEvent);
        subsButton.setOnAction(subsEvent);
        subsTable.setItems(subs);

    }
}
