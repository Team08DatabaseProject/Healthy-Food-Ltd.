package users.ceo;
/**
 * Created by Axel 16.03.2016
 * Controller for the ceo
 */

import classpackage.*;
import div.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.*;

import static javax.swing.UIManager.put;

public class ControllerCEOAddEmployee extends ControllerCEOEmployees implements Initializable {

    @FXML
    public Button employeeSubmitButton;
    public StringField fNameField;
    public StringField lNameField;
    public IntField phoneField;
    public StringField emailField;
    public StringField addressField;
    public ZipCodeField zipCodeField;
		public StringField placeField;
    public StringField usernameField;
    public ComboBox<EmployeePosition> positionBox;
    public DoubleField salaryField;

		public Label usernameFieldErrorMsg;

		private ObservableList<EmployeePosition> employeePositions = FXCollections.observableArrayList();
		private EmployeePosition selectedPosition;
		private boolean attemptedValidation = false;

	ChangeListener<String> validatefName = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			fNameField.validate(nameRules, attemptedValidation);
		}
	};

	ChangeListener<String> validatelName = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			lNameField.validate(mandatoryRule, attemptedValidation);
		}
	};

	ChangeListener<String> validatePhoneNo = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			phoneField.validate(false, attemptedValidation);
		}
	};

	ChangeListener<String> validateeMail = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			emailField.validate(mandatoryRule, attemptedValidation);
		}
	};

	ChangeListener<String> validateAddress = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			addressField.validate(mandatoryRule, attemptedValidation);
		}
	};

	ChangeListener<String> validateUsername = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			usernameField.validate(nameRules, attemptedValidation);
		}
	};

	ChangeListener<String> validateSalary = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			salaryField.validate(false, attemptedValidation);
		}
	};

	ChangeListener<String> validateZipCode = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
												String oldValue, String newValue) {
			if(zipCodeField.validate(false, attemptedValidation)) {
				int zipCode = Integer.parseInt(newValue);
				/*ZipCode zip = db.getZipcodeByZipInt(zipCode);
				if(zip != null && placeField.getText().length() == 0) {
						placeField.setText(zip.getPlace());
				}*/
			}
		}
	};

	ChangeListener<String> validatePlace = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
		                    String oldValue, String newValue) {
			placeField.validate(mandatoryRule, attemptedValidation);
		}
	};

	ChangeListener<EmployeePosition> positionChanged = new ChangeListener<EmployeePosition>() {
		@Override
		public void changed(ObservableValue<? extends EmployeePosition> observable, EmployeePosition oldValue, EmployeePosition newValue) {
			selectedPosition = newValue;
		}
	};

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

  EventHandler<ActionEvent> addEmployee = new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
          try {
              attemptedValidation = true;
              if(validateFields()) {
	              String firstName = fNameField.getText();
	              String lastName = lNameField.getText();
	              int phoneNo = phoneField.getInt();
	              String eMail = emailField.getText();
	              String address = addressField.getText();
	              int zipCode = zipCodeField.getInt();
	              String place = placeField.getText();
	              String username = usernameField.getText();
	              double salary = salaryField.getDouble();
	              String passHash = generatePassword(8);
	              ZipCode zipCodeObject = new ZipCode(zipCode, place);
	              Address addressObject = new Address(address, zipCodeObject);
	              Employee newEmp = new Employee(username, firstName, lastName, phoneNo, eMail, salary, passHash, addressObject, selectedPosition);
	              db.addEmployee(newEmp);
              }


          } catch (Exception exc) {
              System.out.println(exc);
          }
      }
  };

	EventHandler<ActionEvent> updateEmployee = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				attemptedValidation = true;
				if(validateFields()) {
					selectedEmployee.setFirstName(fNameField.getText());
					selectedEmployee.setLastName(lNameField.getText());
					selectedEmployee.setPhoneNo(phoneField.getInt());
					selectedEmployee.seteMail(emailField.getText());
					selectedEmployee.setUsername(usernameField.getText());
					selectedEmployee.setSalary(salaryField.getDouble());
					ZipCode zipCodeObject = new ZipCode(zipCodeField.getInt(), placeField.getText());
					Address addressObject = new Address(addressField.getText(), zipCodeObject);
					selectedEmployee.setAddress(addressObject);
					//db.updateEmployee(selectedEmployee);
				}
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
	};

	public boolean validateFields() {
		boolean validFields = fNameField.validate(nameRules, attemptedValidation);
		validFields &= lNameField.validate(mandatoryRule, attemptedValidation);
		validFields &= phoneField.validate(false, attemptedValidation);
		validFields &= emailField.validate(mandatoryRule, attemptedValidation);
		validFields &= addressField.validate(mandatoryRule, attemptedValidation);
		validFields &= zipCodeField.validate(false, attemptedValidation);
		validFields &= placeField.validate(mandatoryRule, attemptedValidation);
		validFields &= usernameField.validate(nameRules, attemptedValidation);
		validFields &= salaryField.validate(false, attemptedValidation);
		/*if(validFields) {
			Employee testEmployee = db.getEmployeeByUsername(usernameField.getText());
			if(testEmployee != null) {
				usernameFieldErrorMsg.setText("Username is already taken.");
				usernameFieldErrorMsg.setVisible(true);
				return false;
			}
		}*/
		return validFields;
	}

  public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
    employeePositions = db.getEmployeePositions();
		positionBox.setItems(employeePositions);
    positionBox.setCellFactory(column -> {
      return new ListCell<EmployeePosition>() {
	      @Override
	      protected void updateItem(EmployeePosition item, boolean empty) {
		      super.updateItem(item, empty);
		      if (item != null && !empty) {
			      setText(item.toString());
	      }
      }
    };});
	  fNameField.textProperty().addListener(validatefName);
	  lNameField.textProperty().addListener(validatelName);
	  phoneField.textProperty().addListener(validatePhoneNo);
	  emailField.textProperty().addListener(validateeMail);
	  addressField.textProperty().addListener(validateAddress);
	  usernameField.textProperty().addListener(validateUsername);
	  salaryField.textProperty().addListener(validateSalary);
    zipCodeField.textProperty().addListener(validateZipCode);
	  placeField.textProperty().addListener(validatePlace);
    positionBox.getSelectionModel().selectedItemProperty().addListener(positionChanged);
    if(employeeFormUpdate) {
			fNameField.setText(selectedEmployee.getFirstName());
	    lNameField.setText(selectedEmployee.getLastName());
	    phoneField.setInt(selectedEmployee.getPhoneNo());
	    emailField.setText(selectedEmployee.geteMail());
			addressField.setText(selectedEmployee.getAddress().getAddress());
	    zipCodeField.setInt(selectedEmployee.getAddress().getZipCode().getZipCode());
	    usernameField.setText(selectedEmployee.getUsername());
	    salaryField.setDouble(selectedEmployee.getSalary());
	    employeeSubmitButton.setText("Update employee");
	    employeeSubmitButton.setOnAction(updateEmployee);
	    positionBox.getSelectionModel().select(selectedEmployee.getPosition());
    } else {
	    employeeSubmitButton.setText("Add employee");
	    employeeSubmitButton.setOnAction(addEmployee);
	    positionBox.getSelectionModel().selectFirst();
    }
  }
}
