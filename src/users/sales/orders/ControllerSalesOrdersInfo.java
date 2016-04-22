package users.sales.orders;

import classpackage.OrderStatus;
import classpackage.ZipCode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 18/04/2016.
 */
public class ControllerSalesOrdersInfo extends ControllerSalesOrders implements Initializable{

    @FXML
    public GridPane ordersInfoGrid;
    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subscriptionIdField;
    public DatePicker startSubscription;
    public DatePicker endSubscription;
    public TextField fNameField;
    public TextField lNameField;
    public CheckBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField placeField;
    public TextArea customerRequestsArea;
    public DatePicker deadlinePicker;
    public TextField priceField;
    public ComboBox statusBox;
    public Button applyButton;

    EventHandler<ActionEvent> editInfoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {

                if (selectedCustomer != null) {
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
                    String customerRequests = customerRequestsArea.getText();
                    LocalDate deadline = deadlinePicker.getValue();
                    double price = Double.parseDouble(priceField.getText());
                    OrderStatus status = (OrderStatus) statusBox.getValue();
                    LocalDate startSub = startSubscription.getValue();
                    LocalDate endSub = endSubscription.getValue();

                    selectedCustomer.setFirstName(firstName);
                    selectedCustomer.setLastName(lastName);
                    selectedCustomer.setIsBusiness(isBusiness);
                    selectedCustomer.setBusinessName(businessName);
                    selectedCustomer.setEmail(email);
                    selectedCustomer.setPhoneNumber(phoneNumber);
                    selectedCustomer.getAddress().setAddress(address);
                    selectedCustomer.getAddress().setZipCode(zipCodeInt);
                    selectedOrder.setCustomerRequests(customerRequests);
                    selectedOrder.setDeadline(deadline);
                    selectedOrder.setPrice(price);
                    selectedOrder.setStatus(status);
                    selectedCustomer.getSubscription().setStartSubscription(startSub);
                    selectedCustomer.getSubscription().setEndSubscription(endSub);
                }
            } catch(Exception exc) {
                System.out.println("editInfo(orders)Event: " + exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        statusBox.setItems(statusTypes);
        statusBox.setConverter(new StringConverter<OrderStatus>() {
            @Override
            public String toString(OrderStatus os) {
                if (os == null){
                    return null;
                } else {
                    return os.getName();
                }
            }

            @Override
            public OrderStatus fromString(String string) {
                return null;
            }
        });

        statusBox.valueProperty().addListener(new ChangeListener<OrderStatus>() {
            @Override
            public void changed(ObservableValue<? extends OrderStatus> observable, OrderStatus oldValue, OrderStatus newValue) {
                selectedStatus = newValue;
            }
        });

        orderIdField.setText("Order ID: " + String.valueOf(selectedOrder.getOrderId()));
        customerIdField.setText("Customer ID: " + String.valueOf(selectedCustomer.getCustomerId()));
        if (selectedCustomer.getSubscription() != null) {
            subscriptionIdField.setText("Subscription ID: " + String.valueOf(selectedCustomer.getSubscription().getSubscriptionId()));
            startSubscription.setValue(selectedCustomer.getSubscription().getStartSubscription());
            endSubscription.setValue(selectedCustomer.getSubscription().getEndSubscription());
        }

        orderIdField.setDisable(true);
        customerIdField.setDisable(true);
        subscriptionIdField.setDisable(true);
        fNameField.setText(selectedCustomer.getFirstName());
        lNameField.setText(selectedCustomer.getLastName());
        businessBox.setSelected(selectedCustomer.getIsBusiness());
        businessNameField.setText(selectedCustomer.getBusinessName());
        emailField.setText(selectedCustomer.getEmail());
        phoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
        addressField.setText(selectedCustomer.getAddress().getAddress());
        zipCodeField.setText(String.valueOf(selectedCustomer.getAddress().getZipCode()));
        placeField.setText(selectedCustomer.getAddress().getPlace());
        customerRequestsArea.setText(selectedOrder.getCustomerRequests());
        deadlinePicker.setValue(selectedOrder.getDeadline());
        priceField.setText(String.valueOf(selectedOrder.getPrice()));
        statusBox.setValue(selectedOrder.getStatus());
        applyButton.setOnAction(editInfoEvent);

    }
}
