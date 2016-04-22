package users.ceo;

import classpackage.Employee;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class ControllerCEOInventory extends ControllerCEO  implements Initializable {

	@FXML
	public TableView ordersTable;
	public ComboBox ordersStatus;

	protected static ObservableList<Employee> orders;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		//orders = db.getPurchaseOrders();

	}
}
