package views.sales;

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
import classpackage.OrderStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 18/04/2016.
 */
public class SalesInfoController extends SalesController implements Initializable{

    @FXML
    public GridPane subMenuGP;
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
    public TextField deadlineField;
    public TextField priceField;
    public ComboBox<OrderStatus> statusBox;
    public Button editOrderButton;

    EventHandler<ActionEvent> editInfoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                if (selectedOrder != null) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("SalesForm.fxml"));
                    GridPane editOrderGP = loader.load();
                    Scene formScene = new Scene(editOrderGP);
                    Stage formStage = new Stage();
                    formStage.setTitle("Edit order");
                    formStage.setScene(formScene);
                    formStage.show();
                }
            } catch(Exception exc) {
                System.out.println(exc);
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

        orderIdField.setText("Order ID: " + String.valueOf(selectedOrder.getOrderId()));
        customerIdField.setText("Customer ID: " + String.valueOf(selectedCustomer.getCustomerId()));
        if (selectedOrder.isPartOfSubscription()) {
            subscriptionIdField.setText("Subscription ID: " + String.valueOf(selectedCustomer.getSubscription().getSubscriptionId()));
            startSubscription.setValue(selectedCustomer.getSubscription().getStartSubscription());
            endSubscription.setValue(selectedCustomer.getSubscription().getEndSubscription());
        }

        if (selectedCustomer != null){
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
            deadlineField.setText(selectedOrder.getDeadlineTime().format(formatter));
            priceField.setText(String.valueOf(selectedOrder.getPrice()));
            statusBox.setValue(selectedOrder.getStatus());
            editOrderButton.setOnAction(editInfoEvent);
        }
    }
}