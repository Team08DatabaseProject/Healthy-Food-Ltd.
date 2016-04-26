package views.ingredients;

import classpackage.Ingredient;
import classpackage.Supplier;
import javafx.application.Platform;
import javafx.fxml.FXML;
import main.MainController;
import main.PopupDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.util.converter.DoubleStringConverter;


/**
 * Created by axelkvistad on 4/15/16.
 */
public class IngredientsController extends MainController implements Initializable {

    @FXML
    public GridPane subMenuGP;
    public TableView ingTable;
    public TableColumn ingName;
    public TableColumn ingUnit;
    public TableColumn ingPrice;
    public TableColumn ingQuantity;
    public TableColumn ingSupplier;
    public Button addIngButton;
    public Button applyChangesButton;
    public Button removeIngButton;
    public Button refreshIngTableButton;

    protected static Ingredient selectedIngredient;
    protected static Supplier selectedSupplier;

    protected static ObservableList<Supplier> supplierList = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredientList = db.getAllIngredients(supplierList);

    private ObservableList<Ingredient> ingredientsCopy = FXCollections.observableArrayList();

    EventHandler<ActionEvent> applyChangesButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String updateString = "";
                for (Ingredient ing : ingredientList) {
                    if (ing.isChanged()) {
                        ingredientsCopy.add(ing);
                        updateString += "\n" + ing.getIngredientName();
                        ing.setChanged(false);
                    }
                }
                if (ingredientsCopy.isEmpty()) {
                    PopupDialog.errorDialog("Update failure", "Changes were not applied, as no changes were detected in ingredients.");
                } else if (db.updateIngredient(ingredientsCopy)) {
                    ingredientsCopy.clear();
                    PopupDialog.confirmationDialog("Result", "Ingredients:" + updateString + "\nupdated.");
                } else {
                    PopupDialog.errorDialog("Error", "Something went wrong.\nIngredients:" + updateString + "\nfailed to update.");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> refreshIngredients = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                ingredientList = db.getAllIngredients(supplierList);
                ingTable.setItems(ingredientList);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> addIngButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("AddIngredient.fxml"));
                GridPane addIngGP = loader.load();
                Scene formScene = new Scene(addIngGP);
                Stage formStage = new Stage();
                formStage.setTitle("Add new ingredient");
                formStage.setScene(formScene);
                formStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> removeIngButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedIngredient = (Ingredient) ingTable.getSelectionModel().getSelectedItem();
                if (selectedIngredient != null) {
                    if (db.deleteIngredient(selectedIngredient)) {
                        ingredientList.remove(selectedIngredient);
                        ingTable.setItems(ingredientList);
                        PopupDialog.confirmationDialog("Result", "Ingredient: \"" + selectedIngredient.getIngredientName() + "\" deleted.");
                    } else {
                        PopupDialog.errorDialog("Error", "Could not remove ingredient.");
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No ingredient selected");
                    alert.setContentText("Select an ingredient to delete it");
                    alert.showAndWait();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };



    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ingName.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("ingredientName"));
        ingUnit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unit"));
        ingPrice.setCellValueFactory(new PropertyValueFactory<Ingredient, Double>("price"));
        ingQuantity.setCellValueFactory(new PropertyValueFactory<Ingredient, Double>("quantityOwned"));
        ingSupplier.setCellValueFactory(new PropertyValueFactory<Ingredient, Supplier>("supplier"));

        ingName.setCellFactory(TextFieldTableCell.forTableColumn());
        ingName.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, String>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, String> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setIngredientName(event.getNewValue());
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);

                    }
                });


        ingUnit.setCellFactory(TextFieldTableCell.forTableColumn());

        ingUnit.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, String>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, String> event) {
                        (event.getTableView().getItems().get(event.getTablePosition().getRow())).setUnit(event.getNewValue());
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);

                    }
                });

        ingPrice.setCellFactory(TextFieldTableCell.<Ingredient, Double>forTableColumn(new DoubleStringConverter()));
        ingPrice.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);
                    }
                }
        );

        ingQuantity.setCellFactory(TextFieldTableCell.<Ingredient, Double>forTableColumn(new DoubleStringConverter()));
        ingQuantity.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantityOwned(event.getNewValue());
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);
                    }
                }
        );

        ingSupplier.setCellFactory(column -> {
            return new TableCell<Ingredient, Supplier>() {
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

        ingTable.getColumns().setAll(ingName, ingUnit, ingPrice, ingQuantity, ingSupplier);
        ingTable.setItems(ingredientList);
        ingTable.setEditable(true);
        applyChangesButton.setOnAction(applyChangesButtonClick);
        addIngButton.setOnAction(addIngButtonClick);
        removeIngButton.setOnAction(removeIngButtonClick);
        refreshIngTableButton.setOnAction(refreshIngredients);

    }
}
