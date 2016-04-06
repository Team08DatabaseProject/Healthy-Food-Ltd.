package CEO;

import classpackage.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 06.04.2016.
 */
public class ControllerCEOEmployees extends ControllerCEO  implements Initializable {

	ObservableList<Employee> employees;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		employees = db.getEmployees();

	}
}
