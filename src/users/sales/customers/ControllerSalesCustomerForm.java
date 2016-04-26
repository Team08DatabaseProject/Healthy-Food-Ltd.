package users.sales.customers;

import classpackage.Address;
import classpackage.Customer;
import classpackage.Order;
import classpackage.Subscription;
import javafx.application.Platform;
import main.IntField;
import main.PopupDialog;
import main.StringField;
import main.ZipCodeField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 20/04/2016.
 */
public class ControllerSalesCustomerForm extends ControllerSalesCustomer implements Initializable{

    @FXML
    public GridPane subWindowGP;
    public Button customerSubmitButton;
    public StringField fNameField;
    public StringField lNameField;
    public CheckBox businessBox;
    public StringField businessNameField;
    public IntField phoneField;
    public StringField emailField;
    public StringField addressField;
    public StringField placeField;
    public ZipCodeField zipCodeField;
    public Label emailFieldErrorMsg;
    public DatePicker startSubField;
    public DatePicker endSubField;

    private boolean attemptedValidation = false;

    /*protected static TestObjects testObjects = new TestObjects();
    protected static ObservableList<Customer> customerList = testObjects.allCustomers;*/

    ChangeListener<String> validatefName = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
            fNameField.validate(nameRules, attemptedValidation);
        }
    };

    ChangeListener<String> validatelName = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            lNameField.validate(nameRules, attemptedValidation);
        }
    };

    ChangeListener<CheckBox> validateBusinessBox = new ChangeListener<CheckBox>() {
        @Override
        public void changed(ObservableValue<? extends CheckBox> observable, CheckBox oldValue, CheckBox newValue) {

        }
    };

    ChangeListener<String> validateBusinessName = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            businessNameField.validate(nameRules, attemptedValidation);
        }
    };

    ChangeListener<String> validatePhoneNumber = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
            phoneField.validate(false, attemptedValidation);
        }
    };

    ChangeListener<String> validateEmail = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            emailField.validate(mandatoryRule, attemptedValidation);
        }
    };

    ChangeListener<String> validateAddress = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            addressField.validate(mandatoryRule, attemptedValidation);
        }
    };

    ChangeListener<String> validatePlace = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            placeField.validate(mandatoryRule, attemptedValidation);
        }
    };

    //With database
    /*ChangeListener<String> validateZipCode = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable,
                            String oldValue, String newValue) {
            if(zipCodeField.validate(false, attemptedValidation)) {
                int zipCode = Integer.parseInt(newValue);
                ZipCode zip = db.getZipcodeByZipInt(zipCode);
                if(zip != null && placeField.getText().length() == 0) {
                    placeField.setText(zip.getPlace());
                }
            }
        }
    };*/

    Map<Integer, Integer> nameRules = new HashMap<Integer, Integer>() {
        {
            put(StringField.CAN_BE_EMPTY, 0);
            put(StringField.MIN_LENGTH, 4);
            put(StringField.MAX_LENGTH, 64);
        }
    };

    Map<Integer, Integer> mandatoryRule = new HashMap<Integer, Integer>() {
        {
            put(StringField.CAN_BE_EMPTY, 0);
        }
    };

    EventHandler<ActionEvent> addCustomer = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                attemptedValidation = true;
                if(1==1) {
                    String firstName = fNameField.getText();
                    String lastName = lNameField.getText();
                    boolean isBusiness = businessBox.isArmed();
                    String businessName = businessNameField.getText();
                    int phoneNumber = phoneField.getInt();
                    String email = emailField.getText();
                    String address = addressField.getText();
                    String place = placeField.getText();
                    int zipCode = zipCodeField.getInt();
                    LocalDate startSub = startSubField.getValue();
                    LocalDate endSub = endSubField.getValue();
                    Address addressObject = new Address(address, zipCode, place);
                    ObservableList<Order> ordersIntThisSubscription = FXCollections.observableArrayList();
                    Subscription subscriptionObject = new Subscription(startSub, endSub, ordersIntThisSubscription);
                    Customer newCustomer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                            addressObject, businessName, subscriptionObject, ordersIntThisSubscription);

                    customers.add(newCustomer);
                    PopupDialog.confirmationDialog("Result", "Customer: " +
                            newCustomer.getFirstName() + " " + newCustomer.getLastName() + " successfully added");

                }
            } catch (Exception exc) {
                System.out.println(exc);
                exc.printStackTrace();
            }
        }
    };

    public void closeWindow() {
        Stage stage = (Stage) subWindowGP.getScene().getWindow();
        stage.close();
    }

    /*public boolean validateFields() {
        boolean validFields = fNameField.validate(nameRules, attemptedValidation);
        validFields &= lNameField.validate(mandatoryRule, attemptedValidation);
        validFields &= businessNameField.validate(mandatoryRule, attemptedValidation);
        validFields &= phoneField.validate(false, attemptedValidation);
        validFields &= emailField.validate(mandatoryRule, attemptedValidation);
        validFields &= addressField.validate(mandatoryRule, attemptedValidation);
        validFields &= placeField.validate(mandatoryRule, attemptedValidation);
        validFields &= zipCodeField.validate(false, attemptedValidation);
        if(validFields) {
            Customer testCustomer = customers.get(getAllCustomers(emailField.getText());
            if(testCustomer != null) {
                if((customerFormUpdate && testCustomer.getCustomerId() != selectedCustomer.getCustomerId()) || !customerFormUpdate) {
                    emailFieldErrorMsg.setText("Email is already taken.");
                    emailFieldErrorMsg.setVisible(true);
                    return false;
                }
            }
        }
        return validFields;
    }*/

    EventHandler<ActionEvent> updateCustomer = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                attemptedValidation = true;
                if(1==1) { // Create new object instead of writing directly to object in table in case database update doesn't go through.
                    String firstName = fNameField.getText();
                    String lastName = lNameField.getText();
                    boolean isBusiness = businessBox.isArmed();
                    String businessName = businessNameField.getText();
                    int phoneNumber = phoneField.getInt();
                    String email = emailField.getText();
                    String address = addressField.getText();
                    int zipCode = zipCodeField.getInt();
                    String place = placeField.getText();
                    LocalDate startSub = startSubField.getValue();
                    LocalDate endSub = endSubField.getValue();
                    Address addressObject = new Address(address, zipCode, place);
                    ObservableList<Order> ordersIntThisSubscription = FXCollections.observableArrayList();
                    Subscription subscriptionObject = new Subscription(startSub, endSub, ordersIntThisSubscription);
                    selectedCustomer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                            addressObject, businessName, subscriptionObject, ordersIntThisSubscription);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subWindowGP.requestFocus();
            }
        });

        fNameField.textProperty().addListener(validatefName);
        lNameField.textProperty().addListener(validatelName);
        businessNameField.textProperty().addListener(validateBusinessName);
        phoneField.textProperty().addListener(validatePhoneNumber);
        emailField.textProperty().addListener(validateEmail);
        addressField.textProperty().addListener(validateAddress);
        placeField.textProperty().addListener(validatePlace);
        if(customerFormUpdate) {
            fNameField.setText(selectedCustomer.getFirstName());
            lNameField.setText(selectedCustomer.getLastName());
            businessNameField.setText(selectedCustomer.getBusinessName());
            phoneField.setInt(selectedCustomer.getPhoneNumber());
            emailField.setText(selectedCustomer.getEmail());
            addressField.setText(selectedCustomer.getAddress().getAddress());
            zipCodeField.setInt(selectedCustomer.getAddress().getZipCode());
            customerSubmitButton.setText("Update customer");
            customerSubmitButton.setOnAction(updateCustomer);
        } else {
            customerSubmitButton.setText("Add customer");
            customerSubmitButton.setOnAction(addCustomer);
        }
    }
}
