package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesOrders implements Initializable{

    public Button createOrderButton; //Button for creating an order
    public Button deleteOrderButton; //Button for deleting an order
    public TableView ordersTable;
    public BorderPane rootPaneOrders;

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

    public GridPane salesTextField;

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SalesTextField.fxml"));
                salesTextField = loader.load();
                rootPaneOrders.setCenter(salesTextField);
                rootPaneOrders.setBottom(null);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                int selectedIndex = ordersTable.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0){
                    ordersTable.getItems().remove(selectedIndex);
                }else{
                    //Nothing selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No order selected");
                    alert.setContentText("Select an order to delete it");

                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("deleteOrderEvent" + e);
            }
        }
    };

    EventHandler<ActionEvent> infoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{

            }catch (Exception e){
                System.out.println("infoEvent " + e);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        ordersTable.setEditable(true);
        ObservableList<TableColumn> columns = ordersTable.getColumns();
        TableColumn<Customer,Order> orderId = columns.get(0);
        TableColumn<Customer,Integer> customerId = columns.get(1);
        TableColumn<Customer,Subscription> subscriptionId = columns.get(2);
        TableColumn<Customer,String> fname = columns.get(3);
        TableColumn<Customer,String> lname = columns.get(4);
        TableColumn<Customer,String> businessName = columns.get(5);
        TableColumn<Customer,String> email = columns.get(6);
        TableColumn<Customer,Integer> phoneNumber = columns.get(7);
        TableColumn<Customer,Order> customerRequests = columns.get(8);
        TableColumn<Customer,Order> deadline = columns.get(9);
        TableColumn<Customer,Order> price = columns.get(10);
        TableColumn<Customer,Order> address = columns.get(11);
        TableColumn<Customer,Order> status = columns.get(12);


        columns.get(0).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //orderId
        columns.get(1).setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId")); //customerId
        columns.get(2).setCellValueFactory(new PropertyValueFactory<Customer,Subscription>("subscription")); //subscriptionId
        columns.get(3).setCellValueFactory(new PropertyValueFactory<Customer,String>("customer")); //fName
        columns.get(4).setCellValueFactory(new PropertyValueFactory<Customer,String>("customer")); //lName
        columns.get(5).setCellValueFactory(new PropertyValueFactory<Customer,String>("customer")); //businessName
        columns.get(6).setCellValueFactory(new PropertyValueFactory<Customer,String>("customer")); //email
        columns.get(7).setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customer")); //phoneNumber
        columns.get(8).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //customerRequests
        columns.get(9).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //deadline
        columns.get(10).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //price
        columns.get(11).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //address
        columns.get(12).setCellValueFactory(new PropertyValueFactory<Customer,Order>("order")); //status

        // Order ID
        /*orderId.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Order> cell = new TextFieldTableCell();
            StringConverter<Order> converter = new StringConverter<Order>() {
                @Override
                public String toString(Order order) {
                    Integer orderId = order.getOrderId();
                    return orderId.toString();
                }

                @Override
                public Order fromString(String orderIdString) {
                    Order order = cell.getItem();
                    if (order == null) {
                        return null;
                    } else {
                        Integer orderId = Integer.parseInt(orderIdString);
                        order.setOrderId(orderId);
                        return order;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        orderId.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Order> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).getOrders().get(t.getTablePosition().
                            getRow()).setOrderId(Integer.parseInt(t.getNewValue().toString()));
                }
        );*/

        // Customer ID
        /*customerId.setCellFactory(TextFieldTableCell.<Customer, Integer>forTableColumn(new IntegerStringConverter()));
        customerId.setCellFactory(
                (TableColumn.CellEditEvent<Customer, Integer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomerId(t.getNewValue());
                }
        );*/

        // Subscription ID
        /*subscriptionId.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Subscription> cell = new TextFieldTableCell();
            StringConverter<Subscription> converter = new StringConverter<Subscription>() {
                @Override
                public String toString(Subscription subscription) {
                    Integer subscriptionId = subscription.getSubscriptionId();
                    return subscriptionId.toString();
                }

                @Override
                public Subscription fromString(String subscriptionIdString) {
                    Subscription subscription = cell.getItem();
                    if (subscription == null) {
                        return null;
                    } else {
                        Integer subscriptionId = Integer.parseInt(subscriptionIdString);
                        subscription.setSubscriptionId(subscriptionId);
                        return subscription;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });*/

        subscriptionId.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Subscription> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSubscription(t.getNewValue());
                }
        );
        /*
        // First name
        fname.setCellFactory(lv -> {
            TextFieldTableCell<Customer, String> cell = new TextFieldTableCell();
            StringConverter<String> converter = new StringConverter<String>() {
                @Override
                public String toString(Customer customer) {
                    return customer.getFirstName();
                }

                @Override
                public Customer fromString(String name) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        customer.setFirstName(name);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        fname.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Customer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Last name
        lname.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Customer> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
                @Override
                public String toString(Customer customer) {
                    return customer.getLastName();
                }

                @Override
                public Customer fromString(String name) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        customer.setLastName(name);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        lname.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Customer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );


        // Business name
        businessName.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Customer> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
                @Override
                public String toString(Customer customer) {
                    return customer.getBusinessName();
                }

                @Override
                public Customer fromString(String name) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        customer.setBusinessName(name);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        businessName.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Customer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Email
        email.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Customer> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
                @Override
                public String toString(Customer customer) {
                    return customer.getEmail();
                }

                @Override
                public Customer fromString(String email) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        customer.setEmail(email);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        email.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Customer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );
*/
        // Phone number
        /*phoneNumber.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Inte> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
                @Override
                public String toString(Customer customer) {
                    Integer phoneNr =  customer.getPhoneNumber();
                    return phoneNr.toString();
                }

                @Override
                public Customer fromString(String phoneNrString) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        Integer phoneNr = Integer.parseInt(phoneNrString);
                        customer.setPhoneNumber(phoneNr);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        phoneNumber.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Customer> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );
*/
        // Address
        address.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Order> cell = new TextFieldTableCell();
            StringConverter<Order> converter = new StringConverter<Order>() {
                @Override
                public String toString(Order order) {
                    return order.getAddress().getAddress();
                }

                @Override
                public Order fromString(String orderString) {
                    Order order = cell.getItem();
                    if (order == null) {
                        return null;
                    } else {
                        order.getAddress().setAddress(orderString);
                        return order;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        address.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Order> t) -> {
                    ((Customer) t.getTableView().getItems().get(t.getTablePosition().getRow())).
                            getAddress().setAddress(t.getNewValue().toString());
                }
        );

        // Customer requests
        customerRequests.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Order> cell = new TextFieldTableCell<Customer, Order>();
            StringConverter<Order> converter = new StringConverter<Order>() {
                @Override
                public String toString(Order order) {
                    return order.getCustomerRequests();
                }

                @Override
                public Order fromString(String orderString) {
                    Order order = cell.getItem();
                    if (order == null) {
                        return null;
                    } else {
                        order.setCustomerRequests(orderString);
                        return order;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });
        customerRequests.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Order> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).getOrders().get(t.getTablePosition().
                            getRow()).setCustomerRequests(t.getNewValue().toString());
                }
        );

        // Deadline
        deadline.setCellFactory(new Callback<TableColumn<Customer, Order>, TableCell<Customer, Order>>() {
            @Override
            public TableCell<Customer, Order> call(TableColumn<Customer, Order> param) {
                DatePickerCellOrder datePick = new DatePickerCellOrder(order);
                return datePick;
            }
        });

        // Price
        price.setCellFactory(lv -> {
            TextFieldTableCell<Customer, Order> cell = new TextFieldTableCell();
            StringConverter<Order> converter = new StringConverter<Order>() {
                @Override
                public String toString(Order order) {
                    Double price = order.getPrice();
                    return price.toString();
                }

                @Override
                public Order fromString(String priceString) {
                    Order order = cell.getItem();
                    if (order == null) {
                        return null;
                    } else {
                        Double price = Double.parseDouble(priceString);
                        order.setPrice(price);
                        return order;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        price.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Order> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).getOrders().get(t.getTablePosition().
                            getRow()).setPrice(Double.parseDouble(t.getNewValue().toString()));
                }
        );

        // Status
        ObservableList<String> statusComboBoxValues = FXCollections.observableArrayList(
                "Created", "In preperation", "Ready for delivery", "Under delivery", "Delivered"
        );

        status.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<Order>() {
            @Override
            public String toString(Order order) {
                return null;
            }

            @Override
            public Order fromString(String statusString) {
                return null;
            }
        }));

        status.setOnEditCommit(
                (TableColumn.CellEditEvent<Customer, Order> t) -> {
                    t.getTableView().getItems().get(t.getTablePosition().getRow()).getOrders().get(t.getTablePosition().
                            getRow()).setStatus(t.getNewValue().toString());
                }
        );

        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
        ordersTable.setItems(order);
    }
}
