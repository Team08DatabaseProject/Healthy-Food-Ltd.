package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesEdit implements Initializable{

    public Button createOrderButton; //Button for creating an order
    public Button deleteOrderButton; //Button for deleting an order
    public TableView ordersTable;
    public BorderPane rootPaneCreate;

    ZipCode zip = new ZipCode(7031, "Trondheim");
    ZipCode zip1 = new ZipCode(7042, "Trondheim");

    Address address = new Address(1, "Bugata 5", zip);
    Address address1 = new Address(2, "Bugata 7", zip1);

    LocalDate subStart = LocalDate.of(2015, 02, 11);
    LocalDate subEnd = LocalDate.of(2016, 02, 11);
    Subscription subscription = new Subscription(subStart, subEnd);

    ObservableList<Dish> dishes = FXCollections.observableArrayList();
    Dish dish = new Dish(20, "Ravioli");

    final ObservableList<Customer> customerTest = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, address, "", subscription),
            new Customer(true, "truls@gmail.com", "Truls", "Knudsen", 41333183, address1, "Business1", subscription),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, address1, "", subscription),
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, address1, "", subscription)
    );

    LocalDate deadline = LocalDate.of(2016, 9, 11);
    Customer customer = new Customer(true, "bæsj@poop.com", "Bæsj", "Knudsen", 26069512, address, "Rør og kloakk AS", subscription);
    final ObservableList<Order> order = FXCollections.observableArrayList(
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "Less pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes),
            new Order(subscription, "More pickles", deadline, 367.00, "CREATED", customer, dishes)
    );


    final ObservableList<Customer> subsTest = FXCollections.observableArrayList(
            new Customer(false, "arne@gmail.com", "Arne", "Knudsen", 41333183, address, "", subscription),
            new Customer(true, "truls@gmail.com", "Truls", "Arnfinnsson", 41333183, address1, "Business1", subscription),
            new Customer(false, "pål@gmail.com", "Pål", "Juega", 41333183, address1, "", subscription),
            new Customer(false, "knut@gmail.com", "Knut", "Ludvigsen", 41333183, address1, "", subscription)
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
        columns.get(11).setCellValueFactory(new PropertyValueFactory<Order,Customer>("address")); //address
        columns.get(12).setCellValueFactory(new PropertyValueFactory<Order,String>("status"));

        orderId.setCellFactory(TextFieldTableCell.<Order, Integer>forTableColumn(new IntegerStringConverter()));
        orderId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Integer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOrderId(t.getNewValue());
                }
        );
// TODO: 13/04/2016 int
      /*  customerId.setCellFactory(TextFieldTableCell.<Order, Customer>forTableColumn(
                new StringConverter<Customer>() {
                    @Override
                    public String toString(Customer value) {
                        return value.getCustomerId();
                    }
                    @Override
                    public Customer fromString(int customerId) {
                        customer.setCustomerId(customerId);
                        return customer;
                    }
                }));

        customerId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );*/
// TODO: 13/04/2016 int
        /*subscriptionId.setCellFactory(TextFieldTableCell.<Order, Subscription>forTableColumn(
                new StringConverter<Subscription>() {
                    @Override
                    public String toString(Subscription value) {
                        return value.getSubscriptionId();
                    }
                    @Override
                    public Subscription fromString(int subscriptionId) {
                        subscription.setSubscriptionId(subscriptionId);
                        return subscription;
                    }
                }));
*/
        subscriptionId.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Subscription> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSubscription(t.getNewValue());
                }
        );


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

        businessName.setCellFactory(TextFieldTableCell.<Order, Customer>forTableColumn(
                new StringConverter<Customer>() {
                    @Override
                    public String toString(Customer value) {
                        return value.getBusinessName();
                    }
                    @Override
                    public Customer fromString(String businessName) {
                        customer.setBusinessName(businessName);
                        return customer;
                    }
                }));

        businessName.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        email.setCellFactory(TextFieldTableCell.<Order, Customer>forTableColumn(
                new StringConverter<Customer>() {
                    @Override
                    public String toString(Customer value) {
                        return value.getEmail();
                    }
                    @Override
                    public Customer fromString(String email) {
                        customer.setEmail(email);
                        return customer;
                    }
                }));

        email.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );
// TODO: 13/04/2016 int
        phoneNumber.setCellFactory(TextFieldTableCell.<Order, Customer>forTableColumn(
                new StringConverter<Customer>() {
                    @Override
                    public String toString(Customer value) {
                        return value.getFirstName();
                    }
                    @Override
                    public Customer fromString(String firstName) {
                        customer.setFirstName(firstName);
                        return customer;
                    }
                }));

        phoneNumber.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        address.setCellFactory(TextFieldTableCell.<Order, Customer>forTableColumn(
                new StringConverter<Customer>() {
                    @Override
                    public String toString(Customer value) {
                        return value.getAddress().getAddress();
                    }
                    @Override
                    public Customer fromString(String address) {
                        customer.getAddress().setAddress(address);
                        return customer;
                    }
                }));

        address.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, Customer> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomer(t.getNewValue());
                }
        );

        customerRequests.setCellFactory(TextFieldTableCell.<Order>forTableColumn());
        customerRequests.setOnEditCommit(
                (TableColumn.CellEditEvent<Order, String> t) -> {
                    ((Order) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCustomerRequests(t.getNewValue());
                }
        );

        columns.get(1).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item.getCustomerId()));
                    }
                }
            };
        });
        columns.get(2).setCellFactory(column -> {
            return new TableCell<Order, Subscription>() {
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
        /*
        columns.get(3).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getFirstName());
                    }
                }
            };
        });*/
        /*
        columns.get(4).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getLastName());
                    }
                }
            };
        });*/
        /*
        columns.get(5).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getBusinessName());
                    }
                }
            };
        });*/
        /*
        columns.get(6).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getEmail());
                    }
                }
            };
        });*/
        /*
        columns.get(7).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(Integer.toString(item.getPhoneNumber()));
                    }
                }
            };
        });*/
        /*
        columns.get(11).setCellFactory(column -> {
            return new TableCell<Order, Customer>() {
                @Override
                protected void updateItem(Customer item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getAddress().getAddress());
                    }
                }
            };
        });*/
        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
        ordersTable.setItems(order);
    }
}
