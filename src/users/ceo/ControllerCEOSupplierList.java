package users.ceo;

import classpackage.Address;
import classpackage.Employee;
import classpackage.Ingredient;
import classpackage.Supplier;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 22.04.2016.
 */
public class ControllerCEOSupplierList extends ControllerCEOInventory implements Initializable {

	@FXML
	public TableColumn<Supplier, String> nameCol;
	public TableColumn<Supplier, Integer> phoneNoCol;
	public TableColumn<Supplier, Address> addressCol;
	public TableView<Supplier> suppliersTable;

	private ObservableList<Supplier> suppliers;

	private EventHandler<MouseEvent> useSupplier = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
				selectedSupplier.set(suppliersTable.getSelectionModel().getSelectedItem());
				Stage stage = (Stage) suppliersTable.getScene().getWindow();
				stage.close();
			}
		}
	};

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		suppliers = db.getAllSuppliers();

		nameCol.setCellValueFactory(new PropertyValueFactory<>("businessName"));
		phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("thisAddress"));
		addressCol.setCellFactory(column -> {
			return new TableCell<Supplier, Address>() {
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
		suppliersTable.setItems(suppliers);
		suppliersTable.setOnMousePressed(useSupplier);
	}
}
