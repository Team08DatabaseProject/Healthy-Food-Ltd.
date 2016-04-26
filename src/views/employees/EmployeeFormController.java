package views.employees;
/**
 * Created by Axel 16.03.2016
 * Controller for the ceo
 */

import classpackage.*;
import fields.DoubleField;
import fields.IntField;
import fields.StringField;
import fields.ZipCodeField;
import main.*;
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
import javafx.stage.Stage;


import java.net.URL;
import java.util.*;


public class EmployeeFormController extends EmployeesController implements Initializable {

    @FXML
    public GridPane addEmployeeTable;
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
		private boolean salaryAutoChanged = false;

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
			salaryAutoChanged = false;
		}
	};

	ChangeListener<String> validateZipCode = new ChangeListener<String>() {
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
			if(salaryField.getText().length() == 0 || salaryAutoChanged) {
				salaryField.setText(String.format("%.2f", selectedPosition.getDefaultSalary()));
				salaryAutoChanged = true;
			}
		}
	};

	Map<Integer, Integer> nameRules = new HashMap<Integer, Integer>() {
		{
			put(StringField.CAN_BE_EMPTY, 0);
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
	              String password = generatePassword(8);
								String passHash = hashPassword(password);
	              Address addressObject = new Address(address, zipCode, place);
	              Employee newEmp = new Employee(username, firstName, lastName, phoneNo, eMail, salary, passHash, addressObject, selectedPosition);
	              if(db.addEmployee(newEmp)) {
		              PopupDialog.informationDialog("Result", "Employee " + firstName + " " + lastName + " successfully added to the database.");
									employees.add(newEmp);
									PopupDialog.newUserEmail(newEmp, password);
									closeWindow();
	              } else {
		              PopupDialog.errorDialog("Result", "Error: Failed to add employee " + firstName + " " + lastName + " to the database.");
	              }
              }
          } catch (Exception exc) {
              PopupDialog.errorDialog("Error", "Couldn't add new employee.");
          }
      }
  };

	EventHandler<ActionEvent> updateEmployee = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				attemptedValidation = true;
				if(validateFields()) { // Create new object instead of writing directly to object in table in case database update doesn't go through.
					String firstName = fNameField.getText();
					String lastName = lNameField.getText();
					int phoneNo = phoneField.getInt();
					String eMail = emailField.getText();
					String address = addressField.getText();
					int zipCode = zipCodeField.getInt();
					String place = placeField.getText();
					String username = usernameField.getText();
					double salary = salaryField.getDouble();
					Address addressObject = new Address(selectedEmployee.getAddress().getAddressId(), address, zipCode, place);
					Employee updatedEmp = new Employee(selectedEmployee.getEmployeeId(), username, firstName, lastName, phoneNo, eMail, salary, selectedEmployee.getPassHash(), addressObject, selectedPosition);
					if(db.updateEmployee(updatedEmp)) {
						PopupDialog.informationDialog("Result", "Employee " + updatedEmp.getFirstName() + " " + updatedEmp.getLastName() + " successfully updated.");
						employees.set(employees.indexOf(selectedEmployee), updatedEmp);
						closeWindow();
					} else {
						PopupDialog.errorDialog("Result", "Error: Failed to update employee " + updatedEmp.getFirstName() + " " + updatedEmp.getLastName() + "");
					}
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
		if(validFields) {
			Employee testEmployee = db.getEmployeeByUsername(usernameField.getText());
			if(testEmployee != null) {
				if((employeeFormUpdate && testEmployee.getEmployeeId() != selectedEmployee.getEmployeeId()) || !employeeFormUpdate) {
					usernameFieldErrorMsg.setText("Username is already taken.");
					usernameFieldErrorMsg.setVisible(true);
					return false;
				}
			}
		}
		return validFields;
	}

	public void closeWindow() {
		Stage stage = (Stage) addEmployeeTable.getScene().getWindow();
		stage.close();
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
	    zipCodeField.setInt(selectedEmployee.getAddress().getZipCode());
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