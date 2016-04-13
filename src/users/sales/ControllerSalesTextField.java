package users.sales;

import classpackage.*;
import java.lang.String;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 07/04/2016.
 */
public class ControllerSalesTextField implements Initializable{

    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subcriptionIdField;
    public TextField startSubscription;
    public TextField endSubscription;
    public TextField fNameField;
    public TextField lNameField;
    public CheckBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField placeField;
    public TextField customerRequestsField;
    public DatePicker deadlinePicker;
    public TextField priceField;
    public Button createButton;

    ObservableList<String> statusComboBoxValues = FXCollections.observableArrayList(
        "Created", "In preparation", "Ready for delivery", "Under delivery", "Delivered"
    );

    public ComboBox statusBox = new ComboBox(statusComboBoxValues);

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                boolean isBusiness = businessBox.isArmed();
                String businessName = businessNameField.getText();
                String email = emailField.getText();
                int phoneNumber = Integer.parseInt(phoneField.getText());
                String address = addressField.getText();
                int zipCodeInt = Integer.parseInt(zipCodeField.getText());
                String place = placeField.getText();
                ZipCode zipCode = new ZipCode(zipCodeInt, place);
                String customerRequests = customerRequestsField.getText();
                LocalDate deadline = deadlinePicker.getValue();
                double price = Double.parseDouble(priceField.getText());
                String status = (String)statusBox.getValue();
                int orderId = Integer.parseInt(orderIdField.getText());
                int customerId = Integer.parseInt(customerIdField.getText());
                int subcriptionId = Integer.parseInt(subcriptionIdField.getText());
                LocalDate startSubscription = deadlinePicker.getValue();
                LocalDate endSubscription = deadlinePicker.getValue();
                String dishName = "";
                Address newAddress = new Address(address, zipCode);
                Subscription subscription = new Subscription(startSubscription, endSubscription);
                Customer customer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                        newAddress, businessName, subscription);
                Dish dishes = new Dish(price, dishName);
                ObservableList<Dish> dishesInThisOrder = FXCollections.observableArrayList();
                Order order = new Order(subscription, customerRequests, deadline,
                        price, status, customer, dishesInThisOrder);

            } catch(Exception exc) {
                System.out.println("createOrderEvent: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        createButton.setOnAction(createOrderEvent);
    }
}
