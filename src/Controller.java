import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	@FXML
	public TableView tables; // retreives TableView with fx:id="tables"

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs program launch
		final ObservableList<TableTest> data = FXCollections.observableArrayList(
			new TableTest("1", "5"),
			new TableTest("2", "2"),
			new TableTest("3", "3"),
			new TableTest("4", "1"),
			new TableTest("5", "9")
		);

		ObservableList<TableColumn> columns = tables.getColumns();
		columns.get(0).setCellValueFactory(new PropertyValueFactory<TableTest,String>("orderNo"));
		columns.get(1).setCellValueFactory(new PropertyValueFactory<TableTest,String>("dishes"));
		tables.setItems(data);
		data.add(new TableTest("Fifty-three", "7")); // adds new line to table by adding another TableTest object to the data array
	}
}
