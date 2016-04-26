package views.customers;

import classpackage.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainController;

import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 20/04/2016.
 */
public class CustomersController extends MainController implements Initializable {

    @FXML
    public TableView customersTable;
    public TableColumn customerIdCol;
    public TableColumn subscriptionIdCol;
    public TableColumn fNameCol;
    public TableColumn lNameCol;
    public TableColumn phoneNumberCol;
    public TableColumn emailCol;
    public TableColumn addressCol;
    public TableColumn placeCol;
    public TableColumn zipCodeCol;
    public Button addCustomerButton;
    public Button deleteCustomerButton;
    public Button refreshTableButton;

    protected static ObservableList<Supplier> suppliers = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredients = db.getAllIngredients(suppliers);
    protected static ObservableList<Dish> dishes = db.getAllDishes(ingredients);
    protected static ObservableList<Order> orders = db.getOrders(4, dishes);
    protected static ObservableList<Customer> customers = db.getAllCustomers(orders);
    protected static ObservableList<OrderStatus> statusTypes = db.getStatusTypes();

    protected static Dish selectedDish;
    protected static Customer selectedCustomer;
    protected static Subscription selectedSubscription;
    protected static Order selectedOrder;

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy \n HH:mm");

    protected static final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    //protected static ObservableList<Customer> customer;

    protected static boolean customerFormUpdate = false;

    EventHandler<ActionEvent> createCustomerEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                customerFormUpdate = false;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CustomersForm.fxml"));
                GridPane addCustomerTable = loader.load();
                Scene formScene = new Scene(addCustomerTable, 300, 550);
                Stage formStage = new Stage();
                formStage.setTitle("New customer");
                formStage.setScene(formScene);
                formStage.show();
            }catch (Exception e){
                System.out.println("createCustomerEvent " + e);
            }
        }
    };

    private EventHandler<ActionEvent> refreshCustomers = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            customers = db.getAllCustomers(orders);
            customersTable.setItems(customers);
        }
    };

    EventHandler<ActionEvent> deleteCustomerEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0){
                    customersTable.getItems().remove(selectedIndex);
                }else{
                    //Nothing selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No customer selected");
                    alert.setContentText("Select a customer to delete it");

                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("deleteCustomerEvent" + e);
            }
        }
    };

    EventHandler<ActionEvent> refreshTableEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                suppliers = db.getAllSuppliers();
                ingredients = db.getAllIngredients(suppliers);
                dishes = db.getAllDishes(ingredients);
                orders = db.getOrders(4, dishes);
                customers = db.getAllCustomers(orders);
                customersTable.setItems(customers);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    //Axel:
    EventHandler<MouseEvent> editCustomerInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
                    if (selectedCustomer != null) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("EditCustomerInfo.fxml"));
                        GridPane customerInfoGP = loader.load();
                        Scene formScene = new Scene(customerInfoGP);
                        Stage formStage = new Stage();
                        formStage.setTitle("Edit customer");
                        formStage.setScene(formScene);
                        formStage.show();
                    }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        customersTable.setEditable(true);

        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId")); //customerId
        subscriptionIdCol.setCellValueFactory(new PropertyValueFactory<Customer,Subscription>("subscription")); //subscriptionId
        fNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName")); //firstName
        lNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName")); //lastName
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phoneNr"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));
        placeCol.setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));
        zipCodeCol.setCellValueFactory(new PropertyValueFactory<Customer, Address>("address"));

        addressCol.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                public void updateItem(Address address, boolean empty) {
                    if (address == null || empty) {
                        setText(null);
                    } else {
                        setText(address.getAddress());
                    }
                }
            };
        });

        placeCol.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                public void updateItem(Address address, boolean empty) {
                    if (address == null || empty) {
                        setText(null);
                    } else {
                        setText(address.getPlace());
                    }
                }
            };
        });

        zipCodeCol.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                public void updateItem(Address address, boolean empty) {
                    if (address == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(address.getZipCode()));
                    }
                }
            };
        });


        subscriptionIdCol.setCellFactory(column -> {
            return new TableCell<Customer, Subscription>() {
                @Override
                public void updateItem(Subscription subscription, boolean empty) {
                    if (empty) {
                        setText(null);
                    } else if (subscription == null) {
                        setText("No subscription");
                    } else  {
                        setText(String.valueOf(subscription.getSubscriptionId()));
                    }
                }
            };
        });

        customersTable.getColumns().setAll(customerIdCol, subscriptionIdCol, fNameCol, lNameCol, phoneNumberCol, emailCol,
                addressCol, placeCol, zipCodeCol);
        customersTable.setItems(customers);
        addCustomerButton.setOnAction(createCustomerEvent);
        deleteCustomerButton.setOnAction(deleteCustomerEvent);
        refreshTableButton.setOnAction(refreshTableEvent);

        customersTable.setOnMousePressed(editCustomerInfoEvent);
    }
}
