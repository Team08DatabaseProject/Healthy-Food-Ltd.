package users.sales;

import classpackage.Customer;
import classpackage.TestObjects;
import classpackage.ZipCode;
import div.IntField;
import div.StringField;
import div.ZipCodeField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Trym Todalshaug on 20/04/2016.
 */
public class ControllerSalesCustomerForm extends ControllerSales implements Initializable{

    @FXML
    public GridPane addCustomerTable;
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

    private boolean attemptedValidation = false;

    protected static TestObjects testObjects = new TestObjects();
    protected static ObservableList<Customer> customerList = testObjects.allCustomers;

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
}
