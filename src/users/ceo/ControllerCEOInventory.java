package users.ceo;

import classpackage.*;
import div.PopupDialog;
import javafx.beans.property.*;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class ControllerCEOInventory extends ControllerCEO implements Initializable {

	@FXML
	public TableView<POrder> pOrdersTable;
	public TableView pOrderLinesTable;
	public ComboBox pOrdersStatus;

	public Label pOrderTitle;
	public Label grandTotalLabel;
	public Text pOrderSummary;
	public Text pOrderSupplierSummary;
	public TextField grandTotalField;
	public Button refreshPOrdersButton;

	public TableView<POrderLine> newPOrderLinesTable;
	public Button chooseSupplierButton;
	public Button resetNewPOrderButton;
	public Button addLineButton;
	public Button removeLinesButton;
	public Button savePOrderButton;
	public TextField supplierNameField;
	public TextField phoneNoField;
	public TextArea supplierAddressField;

	//Purchase orders columns
	public TableColumn<POrder, Integer> IDCol;
	public TableColumn<POrder, Supplier> supplierCol;
	public TableColumn<POrder, Integer> linesCol;
	public TableColumn<POrder, LocalDate> placedCol;
	public TableColumn<POrder, Boolean> receivedCol;

	//Purchase order summary columns
	public TableColumn<POrderLine, Ingredient> ingredientCol;
	public TableColumn<POrderLine, Double> quantityCol;
	public TableColumn<POrderLine, Ingredient> priceCol;
	public TableColumn<POrderLine, Double> totalCol;

	// New purchase order columns
	public TableColumn<POrderLine, Boolean> ingredientCheckedCol;
	public TableColumn<POrderLine, Ingredient> newIngredientCol;
	public TableColumn<POrderLine, Double> newQuantityCol;
	public TableColumn<POrderLine, Ingredient> newPriceCol;
	public TableColumn<POrderLine, Double> newTotalCol;
	public TextField newGrandTotalField;

	protected static ObservableList<POrder> pOrders = FXCollections.observableArrayList();

	protected static ObjectProperty<Supplier> selectedSupplier = new SimpleObjectProperty<>();
	protected static ObservableList<POrderLine> newPOrderLines = FXCollections.observableArrayList();
	private double grandTotal = 0.0;

	protected StringProperty selectedPOrderStatus = new SimpleStringProperty();

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

	private EventHandler<ActionEvent> refreshPOrders = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			pOrdersStatusChanged();
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

	ChangeListener<String> pOrdersStatusChangedEvent = new ChangeListener<String>() {
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			pOrdersStatusChanged();
		}
	};

	private void pOrdersStatusChanged() {
		selectedPOrderStatus.set(pOrdersStatus.getSelectionModel().getSelectedItem().toString());
		if(selectedPOrderStatus.get().equals("Received")) {
			pOrders = db.getPOrders(true);
		} else {
			pOrders = db.getPOrders(false);
		}
		pOrdersTable.setItems(pOrders);
	}

	private EventHandler<MouseEvent> pOrderChangedEvent = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if (event.isPrimaryButtonDown()) {
				pOrderChanged();
			}
		}
	};

	private void pOrderChanged() {
		if(pOrdersTable.getSelectionModel().getSelectedItem() != null) {
			POrder selectedPOrder = pOrdersTable.getSelectionModel().getSelectedItem();
			String pOrderHeader = "Order ID: " + selectedPOrder.getpOrderId() + "\nDate placed: " + selectedPOrder.getFormattedPlacedDate();
			Supplier supplier = selectedPOrder.getSupplier();
			String supplierSummary = "Supplier: \n" + supplier.getBusinessName() + "\n" + supplier.getPhoneNumber() + "\n" + supplier.getThisAddress().getAddress() +
							"\n" + supplier.getThisAddress().getZipCode() + " " + supplier.getThisAddress().getPlace();
			pOrderLinesTable.setItems(selectedPOrder.getpOrderLines());
			pOrderSummary.setText(pOrderHeader);
			pOrderSupplierSummary.setText(supplierSummary);
			grandTotalField.setText(Double.toString(selectedPOrder.getGrandTotal()));
			pOrderSummary.setVisible(true);
			pOrderSupplierSummary.setVisible(true);
			grandTotalLabel.setVisible(true);
			grandTotalField.setVisible(true);
			pOrderLinesTable.setVisible(true);
			pOrderTitle.setVisible(true);
		} else {
			pOrderSummary.setVisible(false);
			pOrderSupplierSummary.setVisible(false);
			grandTotalLabel.setVisible(false);
			grandTotalField.setVisible(false);
			pOrderLinesTable.setVisible(false);
			pOrderTitle.setVisible(false);
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
		newPOrderLines.clear();
		addLineButton.setDisable(true);
		removeLinesButton.setDisable(true);
		savePOrderButton.setDisable(true);
		newGrandTotalField.setText("0.00");
		chooseSupplierButton.setDisable(false);
		updateSupplierFields();
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		//Purchase orders
		pOrders = db.getPOrders(false);
		pOrdersStatus.getSelectionModel().selectFirst();
		pOrdersStatus.getSelectionModel().selectedItemProperty().addListener(pOrdersStatusChangedEvent);
		refreshPOrdersButton.setOnAction(refreshPOrders);
		IDCol.setCellValueFactory(new PropertyValueFactory<>("pOrderId"));
		supplierCol.setCellValueFactory(new PropertyValueFactory<>("supplier"));
		supplierCol.setCellFactory(column -> {
			return new TableCell<POrder, Supplier>() {
				@Override
				protected void updateItem(Supplier item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getBusinessName());
					}
				}
			};
		});
		linesCol.setCellValueFactory(new PropertyValueFactory<>("numberOfLines"));
		placedCol.setCellValueFactory(new PropertyValueFactory<>("placedDate"));
		placedCol.setCellFactory(column -> {
			return new TableCell<POrder, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
						setText(item.format(formatter));
					}
				}
			};
		});
		receivedCol.setCellValueFactory(new PropertyValueFactory<>("received"));
		receivedCol.setCellFactory(column -> {
			return new TableCell<POrder, Boolean>() {
				@Override
				protected void updateItem(Boolean item, boolean empty) {
					super.updateItem(item, empty);
					if(item == null || empty) {
						setText(null);
						setGraphic(null);
					} else {
						if(!item) {
							Button button = new Button("Received");
							TableCell<POrder, Boolean> cell = this;
							button.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									POrder pOrder = (POrder) cell.getTableRow().getItem();
									pOrder.setReceived(true);
									db.updatePOrder(pOrder);
									ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
									for(POrderLine pOrderLine : pOrder.getpOrderLines()) {
										Ingredient ingredient = pOrderLine.getIngredient();
										ingredient.setQuantityOwned(ingredient.getQuantityOwned() + pOrderLine.getQuantity());
										ingredients.add(ingredient);
									}
									db.updateIngredient(ingredients);
									cell.getTableView().getItems().remove(cell.getTableRow().getIndex());
									setGraphic(null);
									pOrderChanged();
								}
							});
							setGraphic(button);
						} else {
							setGraphic(null);
						}
					}
				}
			};
		});
		pOrdersTable.setItems(pOrders);
		pOrdersTable.setOnMousePressed(pOrderChangedEvent);
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
						setText(String.format("%.2f", item.getPrice()));
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
						setText(String.format("%.2f", item));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		newPOrderLinesTable.setEditable(true);
		newPOrderLinesTable.setItems(newPOrderLines);
		//New purchase order
		resetNewPOrder();
		suppliers = db.getAllSuppliers();
		newPOrderLines.clear();
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
