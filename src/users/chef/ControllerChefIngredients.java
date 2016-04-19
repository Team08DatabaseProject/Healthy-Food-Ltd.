package users.chef;

import classpackage.DishLine;
import classpackage.Ingredient;
import classpackage.Supplier;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import classpackage.TestObjects;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.beans.property.ReadOnlyProperty;
import javafx.util.converter.DoubleStringConverter;


/**
 * Created by axelkvistad on 4/15/16.
 */
public class ControllerChefIngredients extends ControllerChef implements Initializable {

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

    EventHandler<ActionEvent> applyChangesButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                for (Ingredient ing : testIngredients) {
                    System.out.println(ing.getIngredientName());
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
                testIngredients = db.getAllIngredients(testSuppliers);
                ingTable.setItems(testIngredients);
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
                loader.setLocation(getClass().getResource("ChefAddIngredient.fxml"));
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
                /*
                * Everything here to do with DishLine is due to the fact that
                * testDishLines is used in other classes, while this class uses
                * testIngredients, and we need to make sure that they match each other.
                * Hopefully this will have a nicer solution once database is implemented.
                 */
                selectedIngredient = (Ingredient) ingTable.getSelectionModel().getSelectedItem();
                testIngredients.forEach(ing -> {
                    if (selectedIngredient.equals(ing)) {
                        selectedIngredient = ing;
                    }
                 });

                if (selectedIngredient != null) {
                    testIngredients.remove(selectedIngredient);
                    ingTable.setItems(testIngredients);
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

        final NumberFormat nf = NumberFormat.getNumberInstance();
        {
            nf.setMaximumFractionDigits(2);
        }
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
                        (event.getTableView().getItems().get(event.getTablePosition().
                                getRow())).setIngredientName(event.getNewValue());
                    }
                });


        ingUnit.setCellFactory(TextFieldTableCell.forTableColumn());
        ingUnit.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, String>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, String> event) {
                        (event.getTableView().getItems().get(event.getTablePosition().
                                getRow())).setUnit(event.getNewValue());
                    }
                });

        ingPrice.setCellFactory(TextFieldTableCell.<Ingredient, Double>forTableColumn(new DoubleStringConverter()));
        ingPrice.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setPrice(event.getNewValue());
                    }
                }
        );

        ingQuantity.setCellFactory(TextFieldTableCell.<Ingredient, Double>forTableColumn(new DoubleStringConverter()));
        ingQuantity.setOnEditCommit(
                new EventHandler<CellEditEvent<Ingredient, Double>>() {
                    @Override
                    public void handle(CellEditEvent<Ingredient, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setQuantityOwned(event.getNewValue());
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
        ingTable.setItems(testIngredients);
        ingTable.setEditable(true);
        applyChangesButton.setOnAction(applyChangesButtonClick);
        addIngButton.setOnAction(addIngButtonClick);
        removeIngButton.setOnAction(removeIngButtonClick);
        refreshIngTableButton.setOnAction(refreshIngredients);

    }
}
