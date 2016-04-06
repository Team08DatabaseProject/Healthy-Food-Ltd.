package users.ceo;

import classpackage.Employee;
import classpackage.EmployeePosition;
import classpackage.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 06.04.2016.
 */
public class ControllerCEOEmployees extends ControllerCEO  implements Initializable {

	@FXML
	private TableView emmployeesTable;
	private ObservableList<Employee> employees;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		employees = db.getEmployees();
		ObservableList<TableColumn> columns = emmployeesTable.getColumns();
		columns.get(0).setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<Employee, String>("eMail"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<Employee, Integer>("phoneNo"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<Employee, EmployeePosition>("position"));
		columns.get(5).setCellValueFactory(new PropertyValueFactory<Employee, Double>("salary"));
		columns.get(5).setCellFactory(column -> {
			return new TableCell<Employee, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.toString() + " kr.");
					}
				}
			};
		});

		emmployeesTable.setItems(employees);
	}
}
