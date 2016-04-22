package users.chef;

import div.PopupDialog;
import classpackage.DishLine;
import classpackage.Ingredient;
import classpackage.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import classpackage.TestObjects;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.beans.property.ReadOnlyProperty;
import javafx.util.converter.DoubleStringConverter;
import classpackage.TestObjects;

/**
 * Created by axelkvistad on 4/15/16.
 */

public class ControllerChefAddIngredient extends ControllerChef implements Initializable {

    public GridPane addIngredientGP;
    public TextField ingNameField;
    public TextField ingUnitField;
    public TextField ingPriceField;
    public TextField ingQuantityField;
    public ComboBox<Supplier> ingSupplierCB;
    public Button ingApplyButton;


    EventHandler<ActionEvent> ingApplyButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String name = ingNameField.getText();
                String unit = ingUnitField.getText();
                double price = Double.parseDouble(ingPriceField.getText());
                double quantity = Double.parseDouble(ingQuantityField.getText());
                Ingredient newIngredient = new Ingredient(name, unit, quantity, price, selectedSupplier);
                if(db.addIngredient(newIngredient)) {
                    ingredientList.add(newIngredient);
                    PopupDialog.confirmationDialog("Result", "Ingredient \"" + newIngredient.getIngredientName() + "\" added.");
                } else {
                    PopupDialog.errorDialog("Error", "Could not add ingredient.");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ingSupplierCB.setItems(supplierList);
        ingSupplierCB.setPromptText("Supplier");

        ingSupplierCB.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                if (supplier == null) {
                    return null;
                } else {
                    return supplier.getBusinessName();
                }
            }
            @Override
            public Supplier fromString(String s) {
                return null;
            }
        });

        ingSupplierCB.valueProperty().addListener(new ChangeListener<Supplier>() {
            @Override
            public void changed(ObservableValue<? extends Supplier> observable, Supplier oldValue, Supplier newValue) {
                selectedSupplier = newValue;
            }
        });


        ingApplyButton.setOnAction(ingApplyButtonClick);


    }
}
