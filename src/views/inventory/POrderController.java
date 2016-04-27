package views.inventory;

import classpackage.Ingredient;
import classpackage.POrder;
import classpackage.POrderLine;
import classpackage.Supplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainController;
import main.PopupDialog;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 27.04.2016.
 */
public class POrderController extends InventoryController implements Initializable {

	// New purchase order columns
	@FXML
	public TableColumn<POrderLine, Boolean> ingredientCheckedCol;
	public TableColumn<POrderLine, Ingredient> newIngredientCol;
	public TableColumn<POrderLine, Double> newQuantityCol;
	public TableColumn<POrderLine, Ingredient> newPriceCol;
	public TableColumn<POrderLine, Double> newTotalCol;
	public TextField newGrandTotalField;
	public TableView<POrderLine> newPOrderLinesTable;
	public Button chooseSupplierButton;
	public Button resetNewPOrderButton;
	public Button addLineButton;
	public Button removeLinesButton;
	public Button savePOrderButton;
	public TextField supplierNameField;
	public TextField phoneNoField;
	public TextArea supplierAddressField;

	protected static ObjectProperty<Supplier> selectedSupplier = new SimpleObjectProperty<>();
	protected static ObservableList<POrderLine> newPOrderLines = FXCollections.observableArrayList();
	private double grandTotal = 0.0;

	private EventHandler<ActionEvent> chooseSupplier = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("SupplierList.fxml"));
				TableView suppliersTable = loader.load();
				Scene formScene = new Scene(suppliersTable, 400, 550);
				Stage formStage = new Stage();
				formStage.setTitle("Suppliers");
				formStage.setScene(formScene);
				formStage.show();
			} catch (Exception exc) {
				System.out.println(exc);
			}
		}
	};

	private EventHandler<ActionEvent> addLine = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("IngredientList.fxml"));
				GridPane ingredientsForm = loader.load();
				Scene formScene = new Scene(ingredientsForm, 500, 600);
				Stage formStage = new Stage();
				formStage.setTitle("Ingredients");
				formStage.setScene(formScene);
				formStage.show();
			} catch (Exception exc) {
				PopupDialog.errorDialog("Error", "Couldn't add new line to purchase order.");
			}
		}
	};

	private EventHandler<MouseEvent> updateLine = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
				POrderLine selectedPOrderLine = newPOrderLinesTable.getSelectionModel().getSelectedItem();
				double newQuantity = PopupDialog.doubleDialog("Update quantity", "New quantity");
				selectedPOrderLine.setQuantity(newQuantity);
				updateGrandTotalField();
			}
		}
	};


	private EventHandler<ActionEvent> removeLines = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Are you sure you want to delete the selected lines?")) {
				boolean ok = true;
				ObservableList<POrderLine> checkedLines = FXCollections.observableArrayList();
				for(POrderLine line : newPOrderLinesTable.getItems()) { // Can't delete from newPOrderLinesTable list while iterating over it. Performs deletions in separate loop
					if(line.isChecked()) {
						checkedLines.add(line);
					}
				}
				newPOrderLinesTable.getItems().removeAll(checkedLines);
				if(newPOrderLines.size() == 0) {
					removeLinesButton.setDisable(true);
					savePOrderButton.setDisable(true);
				}
			}
		}
	};

	private EventHandler<ActionEvent> savePOrder = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Are you sure you want to send and save this purchase order?")) {
				POrder pOrder = new POrder(selectedSupplier.get(), newPOrderLines);
				if(db.addPOrder(pOrder)) {
					PopupDialog.newPOrderEmail(pOrder, selectedSupplier.get());
					resetNewPOrder();
				} else {
					PopupDialog.errorDialog("Error", "The purchase order could not be added.");
				}
			}
		}
	};

	private EventHandler<ActionEvent> resetNewPOrderHandler = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Are you sure you want to reset this purchase order?")) {
				resetNewPOrder();
			}
		}
	};


	private void updateSupplierFields() {
		if(selectedSupplier.isNotNull().get()) {
			Supplier supplier = selectedSupplier.get();
			supplierNameField.setText(supplier.getBusinessName());
			phoneNoField.setText(Integer.toString(supplier.getPhoneNumber()));
			supplierAddressField.setText(supplier.getThisAddress().getAddress() + "\n" +
							supplier.getThisAddress().getZipCode() + " " + supplier.getThisAddress().getPlace());
			addLineButton.setDisable(false);
			chooseSupplierButton.setDisable(true);
		} else {
			supplierNameField.setText("");
			phoneNoField.setText("");
			supplierAddressField.setText("");
			addLineButton.setDisable(true);
		}
	}

	private void updateGrandTotalField() {
		if(newPOrderLines.size() > 0) {
			removeLinesButton.setDisable(false);
			savePOrderButton.setDisable(false);
			grandTotal = 0.0;
			for(int i = 0; i < newPOrderLines.size(); i++) {
				grandTotal += newPOrderLines.get(i).getTotal();
			}
			newGrandTotalField.setText(String.format("%.2f", grandTotal));
		} else {
			newGrandTotalField.setText("0.00");
			removeLinesButton.setDisable(true);
			savePOrderButton.setDisable(true);
		}
	}
	private void resetNewPOrder() {
		selectedSupplier = new SimpleObjectProperty<>();
		selectedSupplier.addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue o, Object oldValue, Object newValue) {
				updateSupplierFields();
			}
		});
		newPOrderLines.clear();
		addLineButton.setDisable(true);
		removeLinesButton.setDisable(true);
		savePOrderButton.setDisable(true);
		newGrandTotalField.setText("0.00");
		chooseSupplierButton.setDisable(false);
		updateSupplierFields();
	}


	private ListChangeListener updateGrandTotal = new ListChangeListener() {
		@Override
		public void onChanged(ListChangeListener.Change change) {
			updateGrandTotalField();
		}
	};


	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		newPOrderLinesTable.setEditable(true);
		newPOrderLinesTable.setItems(newPOrderLines);
		resetNewPOrder();
		newPOrderLines.clear();
		chooseSupplierButton.setOnAction(chooseSupplier);
		addLineButton.setOnAction(addLine);
		removeLinesButton.setOnAction(removeLines);
		savePOrderButton.setOnAction(savePOrder);
		resetNewPOrderButton.setOnAction(resetNewPOrderHandler);
		selectedSupplier.addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue o, Object oldValue, Object newValue) {
				updateSupplierFields();
			}
		});
		ingredientCheckedCol.setCellValueFactory(new PropertyValueFactory<>("checked"));
		ingredientCheckedCol.setCellFactory(CheckBoxTableCell.forTableColumn(ingredientCheckedCol));
		ingredientCheckedCol.setEditable(true);
		newIngredientCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		newIngredientCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Ingredient>() {
				@Override
				protected void updateItem(Ingredient item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getIngredientName());
					}
				}
			};
		});
		newQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		newPriceCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		newPriceCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Ingredient>() {
				@Override
				protected void updateItem(Ingredient item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item.getPrice()));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		newTotalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		newTotalCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Double>() {
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
		newPOrderLinesTable.setEditable(true);
		newPOrderLinesTable.setItems(newPOrderLines);
		newPOrderLinesTable.setOnMousePressed(updateLine);
		newGrandTotalField.setAlignment(Pos.BASELINE_RIGHT);
		newPOrderLines.addListener(updateGrandTotal);
		newGrandTotalField.prefWidthProperty().bind(newTotalCol.widthProperty());
	}
}
