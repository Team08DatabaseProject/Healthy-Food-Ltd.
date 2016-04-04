package sales;

import classpackage.Address;
import classpackage.Customer;
import classpackage.Order;
import classpackage.Subscription;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesEdit implements Initializable{

    public Button createOrderButton; //Button for creating an order

    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subcriptionIdField;
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
                Address newAddress = new Address(address, zipCode);
                Subscription subscription = new Subscription();
                Customer customer = new Customer(isBusiness, email, firstName,
                        lastName, phoneNumber, newAddress, businessName);
                Order order = new Order(orderId, customerId, subcriptionId,
                        customerRequests, deadline, price, address, status);

                /*FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneSales.setBottom(readyOrderBottom);*/

            } catch(Exception exc) {
                System.out.println("createOrderEvent: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        createOrderButton.setOnAction(createOrderEvent);
    }
}
