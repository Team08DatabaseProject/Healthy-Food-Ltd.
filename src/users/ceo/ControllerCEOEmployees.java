package users.ceo;

import classpackage.Address;
import classpackage.Employee;
import classpackage.EmployeePosition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by HUMBUG on 06.04.2016.
 */
public class ControllerCEOEmployees extends ControllerCEO  implements Initializable {

	@FXML
	private TableView employeesTable;
	private ObservableList<Employee> employees;

	public String generatePsasword(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRTSUVWXYZÆØÅabcdefghijklmnopqrstuvwxyzæøå1234567890.,:;!\"#+-";
		String password = "";
		for(int i = 0; i < characters.length(); i++) {
			int index = ThreadLocalRandom.current().nextInt(characters.length());
			password += characters.charAt(index);
		}
		return password;
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		employees = db.getEmployees();
		ObservableList<TableColumn> columns = employeesTable.getColumns();
		columns.get(0).setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<Employee, String>("eMail"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phoneNo"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<Employee, Address>("address"));
		columns.get(4).setCellFactory(column -> {
			return new TableCell<Employee, Address>() {
				@Override
				protected void updateItem(Address item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getAddress() + "\n" + item.getZipCode().getZipCode() + " " + item.getZipCode().getPlace());
					}
				}
			};
		});
		columns.get(5).setCellValueFactory(new PropertyValueFactory<Employee, EmployeePosition>("position"));
		columns.get(5).setCellFactory(column -> {
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
		columns.get(6).setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
		columns.get(6).setCellFactory(column -> {
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

		employeesTable.setItems(employees);
	}
}
