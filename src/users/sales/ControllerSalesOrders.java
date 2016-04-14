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
    public BorderPane rootPaneCreate;

    ZipCode zip = new ZipCode(7031, "Trondheim");
    ZipCode zip1 = new ZipCode(7042, "Trondheim");

    Address addressTest = new Address(1, "Bugata 5", zip);
    Address addressTest2 = new Address(2, "Bugata 7", zip1);

    LocalDate subStart = LocalDate.of(2015, 2, 11);
    LocalDate subEnd = LocalDate.of(2016, 2, 11);
    Subscription subscription = new Subscription(subStart, subEnd, order);

    ObservableList<Dish> dishes = FXCollections.observableArrayList();
    Dish dish = new Dish(20, "Ravioli");

    final ObservableList<Customer> customerTest = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, "", subscription),
            new Customer(true, "truls@gmail.com", "Truls", "Knudsen", 41333183, addressTest2, "Business1", subscription),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, "", subscription),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest2, "", subscription)
    );

    LocalDate deadline = LocalDate.of(2016, 9, 11);
    Customer customer = new Customer(true, "bæsj@poop.com", "Bæsj", "Knudsen", 26069512, addressTest, "Rør og kloakk AS", subscription);
    final ObservableList<Order> order = FXCollections.observableArrayList(
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "Less pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes)
    );


    final ObservableList<Customer> subsTest = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, addressTest, "", subscription),
            new Customer(true, "truls@gmail.com", "Truls", "Arnfinnsson", 41333183, addressTest2, "Business1", subscription),
            new Customer(false, "pål@gmail.com", "Pål", "Juega", 41333183, addressTest, "", subscription),
            new Customer(false, "knut@gmail.com", "Knut", "Ludvigsen", 41333183, addressTest2, "", subscription)
    );

    public GridPane salesTextField;

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SalesTextField.fxml"));
                salesTextField = loader.load();
                rootPaneCreate.setCenter(salesTextField);
                rootPaneCreate.setBottom(null);
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

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        ordersTable.setEditable(true);
        ObservableList<TableColumn> columns = ordersTable.getColumns();
        TableColumn<Order, Integer> orderId = columns.get(0);
        TableColumn<Order, Customer> customerId = columns.get(1);
        TableColumn<Order, Subscription> subscriptionId = columns.get(2);
        TableColumn<Order, Customer> fname = columns.get(3);
        TableColumn<Order, Customer> lname = columns.get(4);
        TableColumn<Order, Customer> businessName = columns.get(5);
        TableColumn<Order, Customer> email = columns.get(6);
        TableColumn<Order, Customer> phoneNumber = columns.get(7);
        TableColumn<Order, String> customerRequests = columns.get(8);
        TableColumn<Order, LocalDate> deadline = columns.get(9);
        TableColumn<Order, Double> price = columns.get(10);
        TableColumn<Order, Customer> address = columns.get(11);
        TableColumn<Order, String> status = columns.get(12);


        columns.get(0).setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //customerId
        columns.get(2).setCellValueFactory(new PropertyValueFactory<Order,Subscription>("subscription")); //subscriptionId
        columns.get(3).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //fName
        columns.get(4).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //lName
        columns.get(5).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //businessName
        columns.get(6).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //email
        columns.get(7).setCellValueFactory(new PropertyValueFactory<Order,Customer>("customer")); //phoneNumber
        columns.get(8).setCellValueFactory(new PropertyValueFactory<Order,String>("customerRequests"));
        columns.get(9).setCellValueFactory(new PropertyValueFactory<Order,LocalDate>("deadline"));
        columns.get(10).setCellValueFactory(new PropertyValueFactory<Order,Double>("price"));
        columns.get(11).setCellValueFactory(new PropertyValueFactory<Order,Address>("address")); //address
        columns.get(12).setCellValueFactory(new PropertyValueFactory<Order,String>("status"));

        // Deadline
        deadline.setCellFactory(new Callback<TableColumn<Order, LocalDate>, TableCell<Order, LocalDate>>() {
            @Override
            public TableCell<Order, LocalDate> call(TableColumn<Order, LocalDate> param) {
                DatePickerCellOrder datePick = new DatePickerCellOrder(order);
                return datePick;
            }
        });
/*
                deadline.setCellFactory(TextFieldTableCell.<Order, LocalDate>forTableColumn(new LocalDateStringConverter()));
        deadline.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, LocalDate> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDeadline(t.getNewValue());
                }
        );
        */

        // Order ID
        orderId.setCellFactory(TextFieldTableCell.<Order, Integer>forTableColumn(new IntegerStringConverter()));
        orderId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Integer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOrderId(t.getNewValue());
                }
        );

        // Customer ID
        customerId.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
                @Override
                public String toString(Customer customer) {
                    Integer customerId = customer.getCustomerId();
                    return customerId.toString();
                }

                @Override
                public Customer fromString(String customerIdString) {
                    Customer customer = cell.getItem();
                    if (customer == null) {
                        return null;
                    } else {
                        Integer customerId = Integer.parseInt(customerIdString);
                        customer.setCustomerId(customerId);
                        return customer;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        customerId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Subscription ID
        subscriptionId.setCellFactory(lv -> {
            TextFieldTableCell<Order, Subscription> cell = new TextFieldTableCell();
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
        });

        subscriptionId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Subscription> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSubscription(t.getNewValue());
                }
        );

        // First name
        fname.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
            StringConverter<Customer> converter = new StringConverter<Customer>() {
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
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        /* Last name */
        lname.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
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
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );


        // Business name
        businessName.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
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
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Email
        email.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
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
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Phone number
        phoneNumber.setCellFactory(lv -> {
            TextFieldTableCell<Order, Customer> cell = new TextFieldTableCell();
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
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        // Address
        address.setCellFactory(lv -> {
            TextFieldTableCell<Order, Address> cell = new TextFieldTableCell();
            StringConverter<Address> converter = new StringConverter<Address>() {
                @Override
                public String toString(Address address) {
                    return address.getAddress();
                }

                @Override
                public Address fromString(String addressString) {
                    Address address = cell.getItem();
                    if (address == null) {
                        return null;
                    } else {
                        address.setAddress(addressString);
                        return address;
                    }
                }
            };
            cell.setConverter(converter);
            return cell;
        });

        address.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Address> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
                }
        );


        // Customer requests
        customerRequests.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        customerRequests.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, String> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomerRequests(t.getNewValue());
                }
        );

        price.setCellFactory(TextFieldTableCell.<Order, Double>forTableColumn(new DoubleStringConverter()));
        price.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Double> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPrice(t.getNewValue());
                }
        );

        // Status
        ObservableList<String> statusComboBoxValues = FXCollections.observableArrayList(
                "Created", "In preperation", "Ready for delivery", "Under delivery", "Delivered"
        );

        status.setCellFactory(ComboBoxTableCell.forTableColumn(statusComboBoxValues));
        status.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, String> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStatus(t.getNewValue());
                }
        );

        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
        ordersTable.setItems(order);
    }
}
