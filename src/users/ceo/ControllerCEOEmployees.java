package users.ceo;

import classpackage.Employee;
import classpackage.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
		columns.get(0).setCellValueFactory(new PropertyValueFactory<Employee,Integer>("firstName"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<Order,String>("lastName"));
		columns.get(2).setCellValueFactory(new PropertyValueFactory<Order,Date>("eMail"));
		columns.get(3).setCellValueFactory(new PropertyValueFactory<Order,Date>("phoneNo"));
		columns.get(4).setCellValueFactory(new PropertyValueFactory<Order,Date>("position"));
		columns.get(5).setCellValueFactory(new PropertyValueFactory<Order,Date>("salary"));
		emmployeesTable.setItems(employees);
	}
}
