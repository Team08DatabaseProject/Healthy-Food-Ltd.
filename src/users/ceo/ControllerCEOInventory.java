package users.ceo;

import classpackage.*;
import div.PopupDialog;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class ControllerCEOInventory extends ControllerCEO implements Initializable {

	@FXML
	public TableView ordersTable;
	public ComboBox ordersStatus;

	public TableView<POrderLine> pOrderLinesTable;
	public Button chooseSupplierButton;
	public Button resetNewPOrderButton;
	public Button addLineButton;
	public Button removeLinesButton;
	public Button savePOrderButton;
	public TextField supplierNameField;
	public TextField phoneNoField;
	public TextArea supplierAddressField;

	public TableColumn<POrderLine, Boolean> ingredientCheckedCol;
	public TableColumn<POrderLine, Ingredient> ingredientCol;
	public TableColumn<POrderLine, Double> quantityCol;
	public TableColumn<POrderLine, Ingredient> priceCol;
	public TableColumn<POrderLine, Double> totalCol;
	public TextField grandTotalField;

	protected static ObjectProperty<Supplier> selectedSupplier = new SimpleObjectProperty<>();
	protected static ObservableList<POrderLine> pOrderLines = FXCollections.observableArrayList();
	private double grandTotal = 0.0;

	private ObservableList<Employee> orders;
	private ObservableList<Supplier> suppliers;

	private EventHandler<ActionEvent> chooseSupplier = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("CEOSupplierList.fxml"));
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
				loader.setLocation(getClass().getResource("CEOIngredientList.fxml"));
				GridPane ingredientsForm = loader.load();
				Scene formScene = new Scene(ingredientsForm, 500, 600);
				Stage formStage = new Stage();
				formStage.setTitle("Ingredients");
				formStage.setScene(formScene);
				formStage.show();
			} catch (Exception exc) {
				exc.printStackTrace();
				System.out.println(exc);
			}
		}
	};

	private EventHandler<MouseEvent> updateLine = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
				POrderLine selectedPOrderLine = pOrderLinesTable.getSelectionModel().getSelectedItem();
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
				for(POrderLine line : pOrderLinesTable.getItems()) { // Can't delete from pOrderLinesTable list while iterating over it. Performs deletions in separate loop
					if(line.isChecked()) {
						checkedLines.add(line);
					}
				}
				pOrderLinesTable.getItems().removeAll(checkedLines);
				if(pOrderLines.size() == 0) {
					removeLinesButton.setDisable(true);
					savePOrderButton.setDisable(true);
				}
				PopupDialog.informationDialog("Result", "Selected lines deleted successfully.");
			}
		}
	};

	private EventHandler<ActionEvent> savePOrder = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			if(PopupDialog.confirmationDialog("Question", "Are you sure you want to send and save this purchase order?")) {
				POrder pOrder = new POrder(selectedSupplier.get().getSupplierId(), pOrderLines);
				if(db.addPOrder(pOrder)) {
					PopupDialog.newPOrderEmail(pOrder, selectedSupplier.get());
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
		if(selectedSupplier.get() != null) {
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


	private ListChangeListener updateGrandTotal = new ListChangeListener() {
		@Override
		public void onChanged(ListChangeListener.Change change) {
			updateGrandTotalField();
		}
	};

	private void updateGrandTotalField() {
		if(pOrderLines.size() > 0) {
			removeLinesButton.setDisable(false);
			savePOrderButton.setDisable(false);
			grandTotal = 0.0;
			for(int i = 0; i < pOrderLines.size(); i++) {
				grandTotal += pOrderLines.get(i).getTotal();
			}
			grandTotalField.setText(String.format(Locale.ENGLISH, "%.2f", grandTotal));
		} else {
			grandTotalField.setText("0.00");
			removeLinesButton.setDisable(true);
			savePOrderButton.setDisable(true);
		}
	}

	private void resetNewPOrder() {
		selectedSupplier = new SimpleObjectProperty<>();
		pOrderLines.clear();
		addLineButton.setDisable(true);
		removeLinesButton.setDisable(true);
		savePOrderButton.setDisable(true);
		grandTotalField.setText("0.00");
		chooseSupplierButton.setDisable(false);
		updateSupplierFields();
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		resetNewPOrder();
		suppliers = db.getAllSuppliers();
		pOrderLines.clear();
		chooseSupplierButton.setOnAction(chooseSupplier);
		addLineButton.setOnAction(addLine);
		removeLinesButton.setOnAction(removeLines);
		savePOrderButton.setOnAction(savePOrder);
		resetNewPOrderButton.setOnAction(resetNewPOrderHandler);
		selectedSupplier.addListener(new ChangeListener(){
			@Override public void changed(ObservableValue o, Object oldValue, Object newValue){
				updateSupplierFields();
			}
		});
		ingredientCheckedCol.setCellValueFactory(new PropertyValueFactory<>("checked"));
		ingredientCheckedCol.setCellFactory(CheckBoxTableCell.forTableColumn(ingredientCheckedCol));
		ingredientCheckedCol.setEditable(true);
		ingredientCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		ingredientCol.setCellFactory(column -> {
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
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		priceCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Ingredient>() {
				@Override
				protected void updateItem(Ingredient item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format(Locale.ENGLISH, "%.2f", item.getPrice()));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		totalCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Double>() {
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
		pOrderLinesTable.setEditable(true);
		pOrderLinesTable.setItems(pOrderLines);
		pOrderLinesTable.setOnMousePressed(updateLine);
		pOrderLinesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		grandTotalField.setAlignment(Pos.BASELINE_RIGHT);
		pOrderLines.addListener(updateGrandTotal);
		grandTotalField.prefWidthProperty().bind(totalCol.widthProperty());
	}
}
