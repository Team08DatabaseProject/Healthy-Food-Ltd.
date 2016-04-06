package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesEdit implements Initializable{

    public Button createOrderButton; //Button for creating an order
    public Button deleteOrderButton;

    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subcriptionIdField;
    public TextField startSubscription;
    public TextField endSubscription;
    public TextField fNameField;
    public TextField lNameField;
    public ComboBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField customerRequestsField;
    public DatePicker deadlinePicker;
    public TextField priceField;
    public TextField statusField;

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
                int zipCode = Integer.parseInt(zipCodeField.getText());
                String customerRequests = customerRequestsField.getText();
                LocalDate deadline = deadlinePicker.getValue();
                double price = Double.parseDouble(priceField.getText());
                String status = statusField.getText();
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

                /*FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneSales.setBottom(readyOrderBottom);*/

            } catch(Exception exc) {
                System.out.println("createOrderEvent: " + exc);
            }
        }
    };

    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>(){
        @Override
        public void handle(ActionEvent e){
            try{

            }catch (Exception exc){
                System.out.println("deleteOrderEvent: " + e);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
    }
}
