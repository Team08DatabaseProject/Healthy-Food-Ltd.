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
public class ControllerCEOIngredientList extends ControllerCEOInventory implements Initializable {

	@FXML
	public TableColumn<Ingredient, String> nameCol;
	public TableColumn<Ingredient, Double> quantityCol;
	public TableColumn<Ingredient, String> unitCol;
	public TableColumn<Ingredient, Double> priceCol;
	public TableView<Ingredient> ingredientsTable;

	private ObservableList<Ingredient> ingredients;

	private Ingredient selectedIngredient;

	private EventHandler<MouseEvent> useIngredient = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
				selectedIngredient = ingredientsTable.getSelectionModel().getSelectedItem();
				Stage stage = (Stage) ingredientsTable.getScene().getWindow();
				stage.close();
			}
		}
	};

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		ingredients = db.getIngredientsBySupplierId(selectedSupplier.get().getSupplierId());
		nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityOwned"));
		unitCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		ingredientsTable.setItems(ingredients);
		ingredientsTable.setOnMousePressed(useIngredient);
	}
}
