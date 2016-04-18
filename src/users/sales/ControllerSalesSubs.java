package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 14/04/2016.
 */

public class ControllerSalesSubs implements Initializable{

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

    /*EventHandler<ActionEvent> subsEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SubsTable.fxml"));
                subsTable = loader.load();
                rootPaneOrders.setCenter(subsTable);
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

    Subscription subs = new Subscription(subStart, subEnd, order);
    Subscription subs2 = new Subscription(subStart, subEnd, order1);

    final ObservableList<Customer> customersSinSub = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, ""),
            new Customer(true, "truls@gmail.com", "Truls", "Knudsen", 41333183, addressTest2, "Business1"),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, ""),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest2, "")
    );

    final ObservableList<Customer> customersConSub = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, "", subs, order),
            new Customer(true, "truls@gmail.com", "Truls", "Ola", 41333183, addressTest2, "Business1", subs2, order1),
            new Customer(false, "pål@gmail.com", "Pål", "Arnfinnsson", 41333183, addressTest, "", subs, order),
            new Customer(false, "knut@gmail.com", "Knut", "Ludvigsen", 41333183, addressTest2, "", subs2, order1)
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
        TableColumn<Customer, Integer> customerId = columns.get(0);
        TableColumn<Customer, Subscription> subscriptionId = columns.get(1);
        TableColumn<Customer, Subscription> startSub = columns.get(2);
        TableColumn<Customer, Subscription> endSub = columns.get(3);
        TableColumn<Customer, String> businessName = columns.get(4);
        TableColumn<Customer, String> fname = columns.get(5);
        TableColumn<Customer, String> lname = columns.get(6);
        TableColumn<Customer, Address> address = columns.get(7);
        TableColumn<Customer, String> email = columns.get(8);
        TableColumn<Customer, Integer> phoneNumber = columns.get(9);

        columns.get(0).setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("subscriptionId"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("startSubscription"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer, Subscription>("endSubscription"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
        columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer, String>("businessName"));
        columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
        columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
        columns.get(7).setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));
        columns.get(8).setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        columns.get(9).setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNumber"));

        columns.get(1).setCellFactory(column -> {
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
        /*startSub.setCellFactory(new Callback<TableColumn<Subscription, LocalDate>, TableCell<Subscription, LocalDate>>() {
            @Override
            public TableCell<Subscription, LocalDate> call(TableColumn<Subscription, LocalDate> param) {
                DatePickerCellOrder datePick = new DatePickerCellOrder(subs);
                return datePick;
            }
        });
        columns.get(2).setCellFactory(column -> {
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
        columns.get(3).setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                protected void updateItem(Subscription item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getEndSubscription());
                    }
                }
            };
        });*/

        address.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Address> cell = new TextFieldTableCell();
            StringConverter<Address> converter = new StringConverter<Address>() {
                @Override
                public String toString(Address address1) {
                    return address1.getAddress();
                }

                @Override
                public Address fromString(String addressString) {
                    Address address1 = cell.getItem();
                    if (address1 == null) {
                        return null;
                    } else {
                        address1.setAddress(addressString);
                        return address1;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        address.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Address> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
                }
        );

        /*ordersButton.setOnAction(orderEvent);
        subsButton.setOnAction(subsEvent);*/
        subsTable.setItems(customersConSub);
        subsTable.setItems(customersSinSub);

    }
}
