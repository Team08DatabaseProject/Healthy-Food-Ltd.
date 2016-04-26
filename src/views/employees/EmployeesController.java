package views.employees;

import classpackage.Address;
import classpackage.*;
import javafx.application.Platform;
import main.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HUMBUG on 06.04.2016.
 */

public class EmployeesController extends MainController implements Initializable {

	@FXML
	public TableView<Employee> employeesTable;
	public Button addEmployeeFormButton;
	public Button refreshEmployeesButton;
	public Button newPasswordButton;
	public Button deleteEmployeesButton;
	public TableColumn<Employee, Boolean> checkedCol;
	public TableColumn<Employee, String> fNameCol;
	public TableColumn<Employee, String> lNameCol;
	public TableColumn<Employee, String> eMailCol;
	public TableColumn<Employee, Integer> phoneNoCol;
	public TableColumn<Employee, Address> addressCol;
	public TableColumn<Employee, EmployeePosition> positionCol;
	public TableColumn<Employee, Double> salaryCol;

	protected static ObservableList<Employee> employees;

	protected static boolean employeeFormUpdate = false;
	protected static Employee selectedEmployee;

	private EventHandler<ActionEvent> addEmployeeForm = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				employeeFormUpdate = false;
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("EmployeeForm.fxml"));
				GridPane addEmployeeTable = loader.load();
				Scene formScene = new Scene(addEmployeeTable, 300, 550);
				Stage formStage = new Stage();
				formStage.setTitle("New employee");
				formStage.setScene(formScene);
				formStage.show();
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
	};

	private EventHandler<ActionEvent> refreshEmployees = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			employees = db.getEmployees();
			employeesTable.setItems(employees);
		}
	};

	private EventHandler<MouseEvent> updateEmployeeForm = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
				selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
				try {
					employeeFormUpdate = true;
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("EmployeeForm.fxml"));
					GridPane addEmployeeTable = loader.load();
					Scene formScene = new Scene(addEmployeeTable, 300, 550);
					Stage formStage = new Stage();
					formStage.setTitle("Update Employee");
					formStage.setScene(formScene);
					formStage.show();
				} catch (Exception exc) {
					System.out.println(exc);
				}
			}
		}
	};

	private EventHandler<ActionEvent> newPassword = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Generate new passwords for selected employees?")) {
				String result = "";
				boolean ok = true;
				for(Employee emp : employeesTable.getItems()) {
					if(emp.isChecked()) {
						String newPassword = generatePassword(8);
						String passHash = hashPassword(newPassword);
						emp.setPassHash(passHash);
						if(!db.updateEmployee(emp)) {
							ok = false;
							result += "Could not update " + emp.getFirstName() + " " + emp.getLastName() + "\n";
						} else {
							PopupDialog.newPasswordEmail(emp, newPassword);
						}
					}
				}
				if(!ok) {
					PopupDialog.errorDialog("Error", result);
				} else {
					PopupDialog.informationDialog("Result", "Selected employees updated successfully.");
				}
			}
		}
	};

	private EventHandler<ActionEvent> deleteEmployees = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Are you sure you want to delete the selected employees?")) {
				String result = "";
				boolean ok = true;
				ObservableList<Employee> checkedEmps = FXCollections.observableArrayList();
				for(Employee emp : employeesTable.getItems()) { // Can't delete from employeesTable list while iterating over it. Performs deletions in separate loop
					if(emp.isChecked()) {
						checkedEmps.add(emp);
					}
				}
				for(int i = 0; i < checkedEmps.size(); i++) {
					Employee emp = checkedEmps.get(i);
					if(!db.deleteEmployee(emp)) {
						ok = false;
						result += "Could not delete " + emp.getFirstName() + " " + emp.getLastName() + "\n";
					} else {
						employeesTable.getItems().remove(emp);
					}
				}
				if(!ok) {
					PopupDialog.errorDialog("Error", result);
				} else {
					PopupDialog.informationDialog("Result", "Selected employees deleted successfully.");
				}
			}
		}
	};

	public String generatePassword(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRTSUVWXYZÆØÅabcdefghijklmnopqrstuvwxyzæøå1234567890.,:;!\"#+-";
		String password = "";
		for(int i = 0; i < length; i++) {
			password += characters.charAt(ThreadLocalRandom.current().nextInt(characters.length()));
		}
		return password;
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				rootPane.requestFocus();
			}
		});

		addEmployeeFormButton.setOnAction(addEmployeeForm);
		refreshEmployeesButton.setOnAction(refreshEmployees);
		newPasswordButton.setOnAction(newPassword);
		deleteEmployeesButton.setOnAction(deleteEmployees);
		employees = db.getEmployees();
		checkedCol.setCellValueFactory(new PropertyValueFactory<>("checked"));
		checkedCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkedCol));
		checkedCol.setEditable(true);
		fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		eMailCol.setCellValueFactory(new PropertyValueFactory<>("eMail"));
		phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		addressCol.setCellFactory(column -> {
			return new TableCell<Employee, Address>() {
				@Override
				protected void updateItem(Address item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getAddress() + "\n" + item.getZipCode() + " " + item.getPlace());
					}
				}
			};
		});
		positionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
		positionCol.setCellFactory(column -> {
			return new TableCell<Employee, EmployeePosition>() {
				@Override
				protected void updateItem(EmployeePosition item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getDescription());
					}
				}
			};
		});
		salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
		salaryCol.setCellFactory(column -> {
			return new TableCell<Employee, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		employeesTable.setEditable(true);
		employeesTable.setOnMousePressed(updateEmployeeForm);
		employeesTable.setItems(employees);
	}
}
