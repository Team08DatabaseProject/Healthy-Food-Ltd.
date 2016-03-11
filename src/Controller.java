import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	@FXML
	//public TableView tables; // Retrieves TableView with fx:id="tables"
	public Button ingredients;
	public BorderPane rootPane;
	final ObservableList<TableTest> data = FXCollections.observableArrayList(
			new TableTest("1", "5"),
			new TableTest("2", "2"),
			new TableTest("3", "3"),
			new TableTest("4", "1"),
			new TableTest("5", "9")
	);

	EventHandler<ActionEvent> ingButton  = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("./src/OrderTable.fxml"));
				TableView orderTable = loader.load();
				rootPane.setCenter(orderTable);
				ObservableList<TableColumn> columns = orderTable.getColumns();
				columns.get(0).setCellValueFactory(new PropertyValueFactory<TableTest,String>("orderNo"));
				columns.get(1).setCellValueFactory(new PropertyValueFactory<TableTest,String>("dishes"));
				orderTable.setItems(data);
			} catch(Exception exc) {
				System.out.println(exc);
			}
		}
	};

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch



		//data.add(new TableTest("Fifty-three", "7")); // adds new line to table by adding another TableTest object to the data array

		ingredients.setOnAction(ingButton);
	}


}
