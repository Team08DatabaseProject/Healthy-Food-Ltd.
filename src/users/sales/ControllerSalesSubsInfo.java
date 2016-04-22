package users.sales;

import classpackage.ZipCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 20/04/2016.
 */
public class ControllerSalesSubsInfo extends ControllerSales implements Initializable{
    @FXML

    public Label customerIdLabel;
    public Label subscriptionIdLabel;
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
    public Button applyButton;

    EventHandler<ActionEvent> editInfoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {

                if (selectedCustomer != null) {
                    int customerId = Integer.parseInt(customerIdLabel.getText());
                    int subscriptionId = Integer.parseInt(subscriptionIdLabel.getText());
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
                    LocalDate startSub = startSubscription.getValue();
                    LocalDate endSub = endSubscription.getValue();

                    selectedCustomer.setCustomerId(customerId);
                    selectedCustomer.getSubscription().setSubscriptionId(subscriptionId);
                    selectedCustomer.setFirstName(firstName);
                    selectedCustomer.setLastName(lastName);
                    selectedCustomer.setIsBusiness(isBusiness);
                    selectedCustomer.setBusinessName(businessName);
                    selectedCustomer.setEmail(email);
                    selectedCustomer.setPhoneNumber(phoneNumber);
                    selectedCustomer.getAddress().setAddress(address);
                    selectedCustomer.getAddress().setZipCode(zipCodeInt);
                    selectedCustomer.getSubscription().setStartSubscription(startSub);
                    selectedCustomer.getSubscription().setEndSubscription(endSub);
                }
            } catch(Exception exc) {
                System.out.println("createOrderFieldEvent: " + exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        customerIdLabel.setText("Customer ID: " + String.valueOf(selectedCustomer.getCustomerId()));
        if (selectedCustomer.getSubscription() != null) {
            subscriptionIdLabel.setText("Subscription ID: " + String.valueOf(selectedCustomer.getSubscription().getSubscriptionId()));
            startSubscription.setValue(selectedCustomer.getSubscription().getStartSubscription());
            endSubscription.setValue(selectedCustomer.getSubscription().getEndSubscription());
        }


        fNameField.setText(selectedCustomer.getFirstName());
        lNameField.setText(selectedCustomer.getLastName());
        businessBox.setSelected(selectedCustomer.getIsBusiness());
        businessNameField.setText(selectedCustomer.getBusinessName());
        emailField.setText(selectedCustomer.getEmail());
        phoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
        addressField.setText(selectedCustomer.getAddress().getAddress());
        zipCodeField.setText(String.valueOf(selectedCustomer.getAddress().getZipCode()));
        placeField.setText(selectedCustomer.getAddress().getPlace());
        applyButton.setOnAction(editInfoEvent);

    }
}