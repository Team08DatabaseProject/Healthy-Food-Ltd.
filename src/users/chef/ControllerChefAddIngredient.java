package users.chef;

import div.PopupDialog;
import classpackage.Ingredient;
import classpackage.Supplier;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;


/**
 * Created by axelkvistad on 4/15/16.
 */

public class ControllerChefAddIngredient extends ControllerChef implements Initializable {

    @FXML
    public GridPane subWindowGP;
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subWindowGP.requestFocus();
            }
        });

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
