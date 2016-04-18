package users.sales;

import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 18/04/2016.
 */
public class ControllerSalesInfo implements Initializable{

    public Label orderIdLabel;
    public Label customerIdLabel;
    public Label subscriptionIdLabel;
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
    public TextArea customerRequestsArea;
    public DatePicker deadlinePicker;
    public TextField priceField;
    ObservableList<String> statusComboBoxValues = FXCollections.observableArrayList(
            "Created", "In preparation", "Ready for delivery", "Under delivery", "Delivered"
    );

    public ComboBox statusBox = new ComboBox(statusComboBoxValues);
    public Button applyButton;

    private TestObjects testObjects = new TestObjects();
    ObservableList<Order> orders = testObjects.allOrders;

    @FXML
    public BorderPane rootPaneOrders;
    public GridPane infoGrid;
    public TableView<Order> ordersTable;

    protected static Order selectedOrder;
    protected static boolean ordersTableUpdate = false;

    EventHandler<ActionEvent> editInfoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                int orderId = Integer.parseInt(orderIdLabel.getText());
                int customerId = Integer.parseInt(customerIdLabel.getText());
                int subcriptionId = Integer.parseInt(subscriptionIdLabel.getText());
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
                String status = (String)statusBox.getValue();
                LocalDate startSubscription = deadlinePicker.getValue();
                LocalDate endSubscription = deadlinePicker.getValue();

            } catch(Exception exc) {
                System.out.println("createOrderEventField: " + exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        applyButton.setOnAction(editInfoEvent);
    }
}
