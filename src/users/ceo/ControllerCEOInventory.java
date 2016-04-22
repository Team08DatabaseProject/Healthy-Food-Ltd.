package users.ceo;

import classpackage.Address;
import classpackage.Employee;
import classpackage.Ingredient;
import classpackage.Supplier;
import div.PopupDialog;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 20.04.2016.
 */
public class ControllerCEOInventory extends ControllerCEO implements Initializable {

	@FXML
	public TableView ordersTable;
	public ComboBox ordersStatus;

	public Button chooseSupplierButton;
	public Button addLineButton;
	public TextField supplierNameField;
	public TextField phoneNoField;
	public TextArea supplierAddressField;

	public TableColumn<Ingredient, String> ingredientCol;
	public TableColumn<Ingredient, Double> quantityCol;
	public TableColumn<Ingredient, Double> priceCol;
	public TableColumn<Ingredient, Double> totalCol;

	protected static ObjectProperty<Supplier> selectedSupplier = new SimpleObjectProperty<>();

	private ObservableList<Employee> orders;
	private ObservableList<Supplier> suppliers;

	EventHandler<ActionEvent> chooseSupplier = new EventHandler<ActionEvent>() {
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

	EventHandler<ActionEvent> addLine = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent e) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("CEOIngredientList.fxml"));
				TableView suppliersTable = loader.load();
				Scene formScene = new Scene(suppliersTable, 500, 600);
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

	public void updateSupplierFields() {
		if(selectedSupplier != null) {
			Supplier supplier = selectedSupplier.get();
			supplierNameField.setText(supplier.getBusinessName());
			phoneNoField.setText(Integer.toString(supplier.getPhoneNumber()));
			supplierAddressField.setText(supplier.getThisAddress().getAddress() + "\n" +
				supplier.getThisAddress().getZipCode() + " " + supplier.getThisAddress().getPlace());
		}
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
		suppliers = db.getAllSuppliers();
		chooseSupplierButton.setOnAction(chooseSupplier);
		addLineButton.setOnAction(addLine);
		selectedSupplier.addListener(new ChangeListener(){
			@Override public void changed(ObservableValue o, Object oldValue, Object newValue){
				updateSupplierFields();
			}
		});

		ingredientCol.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantityOwned"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
}
