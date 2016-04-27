package views.inventory;

import classpackage.*;
import javafx.application.Platform;
import main.MainController;
import main.PopupDialog;
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
public class InventoryController extends MainController implements Initializable {

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

	public Button newPOrderButton;

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


	private static ObservableList<POrder> pOrders = FXCollections.observableArrayList();

	private StringProperty selectedPOrderStatus = new SimpleStringProperty();

	private EventHandler<ActionEvent> refreshPOrders = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			pOrdersStatusChanged();
		}
	};

	private EventHandler<ActionEvent> newPOrder = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("POrder.fxml"));
					GridPane orderPane = loader.load();
					Scene orderScene = new Scene(orderPane, 800, 800);
					Stage formStage = new Stage();
					formStage.setTitle("New purchase order");
					formStage.setScene(orderScene);
					formStage.show();
			} catch (Exception exc) {
				PopupDialog.errorDialog("Error", "Couldn't add new purchase order.");
			}

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


	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		pOrders = db.getPOrders(false);
		pOrdersStatus.getSelectionModel().selectFirst();
		pOrdersStatus.getSelectionModel().selectedItemProperty().addListener(pOrdersStatusChangedEvent);
		refreshPOrdersButton.setOnAction(refreshPOrders);
		newPOrderButton.setOnAction(newPOrder);
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
							button.getStyleClass().add("smallButton");
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
	}
}
