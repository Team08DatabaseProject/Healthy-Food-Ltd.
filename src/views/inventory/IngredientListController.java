package views.inventory;

import classpackage.*;
import fields.DoubleField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 22.04.2016.
 */
public class IngredientListController extends InventoryController implements Initializable {

	@FXML
	public TableColumn<Ingredient, String> nameCol;
	public TableColumn<Ingredient, String> quantityCol;
	public TableColumn<Ingredient, Double> priceCol;
	public TableView<Ingredient> ingredientsTable;

	public Label tableErrorMsg;
	public Label unitLabel;
	public Button lineSubmitButton;

	public DoubleField quantityField;

	private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
	private Ingredient selectedIngredient;

	private boolean attemptedValidation = false;

	ChangeListener<String> validateQuantity = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable,
												String oldValue, String newValue) {
			quantityField.validate(false, attemptedValidation);
		}
	};

	private EventHandler<MouseEvent> useIngredient = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown()) {
				selectedIngredient = ingredientsTable.getSelectionModel().getSelectedItem();
				unitLabel.setText(selectedIngredient.getUnit());
			}
		}
	};

	EventHandler<ActionEvent> addPOrderLIne = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			attemptedValidation = true;
			if(selectedIngredient == null) {
				tableErrorMsg.setText("One ingredient must be picked.");
				return;
			}
			if(!quantityField.validate(false, attemptedValidation)) {
				return;
			}
			double quantity = quantityField.getDouble();
			POrderLine pOrderLine = new POrderLine(selectedIngredient, quantity);
			newPOrderLines.add(pOrderLine);
			Stage stage = (Stage) ingredientsTable.getScene().getWindow();
			stage.close();
		}
	};

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		ObservableList<Ingredient> ingredientsTemp = db.getIngredientsBySupplierId(selectedSupplier.get().getSupplierId());
		for(int i = 0; i < ingredientsTemp.size(); i++) {
			if(!newPOrderLines.contains(ingredientsTemp.get(i))) {
				ingredients.add(ingredientsTemp.get(i));
			}
		}
		nameCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityAndUnit"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceCol.setCellFactory(column -> {
			return new TableCell<Ingredient, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format(Locale.ENGLISH, "%.2f", item));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		ingredientsTable.setItems(ingredients);
		ingredientsTable.setOnMousePressed(useIngredient);
		lineSubmitButton.setOnAction(addPOrderLIne);
		ingredientsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				tableErrorMsg.setText("");
			}
		});
		quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
			quantityField.validate(false, attemptedValidation);
		}
		);
	}
}
