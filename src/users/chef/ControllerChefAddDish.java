package users.chef;

import classpackage.*;
import div.PopupDialog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/16/16.
 */
public class ControllerChefAddDish extends ControllerChef implements Initializable {

    public GridPane subWindowGP;
    public TextField dishNameField;
    public TextField dishPriceFactorField;
    public ComboBox<Ingredient> ingredientComboBox;
    public Button addIngToDishButton;
    public Button removeIngFromDishButton;
    public TableView chosenIngTable;
    public TableColumn chosenIngName;
    public TableColumn chosenIngAmount;
    public TableColumn chosenIngUnit;
    public TableColumn chosenIngPrice;
    public Label dishNameLabel;
    public Label dishPriceLabel;
    public Button commitNewDishButton;
    public Button applyNewDishChangesButton;

    private String dishNameString;
    private double dishPriceFactor;
    private String dishPriceFactorString;
    private double dishPrice = 0;


    // This list is filled up by the user by choosing from ingredientComboBox and clicking the addIngToDishButton
    ObservableList<DishLine> chosenDishLines = FXCollections.observableArrayList();

    // Used to set doubles to 2 decimals max
    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    // After fields are filled in and ingredients selected, this event adds the dish to the dish list
    EventHandler<ActionEvent> commitNewDishEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!(dishNameString.isEmpty() || dishPriceFactorString.isEmpty() || chosenDishLines.isEmpty())) {
                    Dish newDish = new Dish(dishPrice, dishNameString, chosenDishLines);
                    if(db.addDish(newDish)) {
                        PopupDialog.confirmationDialog("Result", "Dish \"" + newDish.getDishName() + "\" added.");
                        dishList.add(newDish);
                    } else {
                        PopupDialog.errorDialog("Error", "Dish could not be added.");
                    }

                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> applyChanges = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!(dishNameField.getText().isEmpty() || dishPriceFactorField.getText().isEmpty() || chosenDishLines.isEmpty())) {

                    dishNameString = dishNameField.getText();;
                    dishPriceFactorString = dishPriceFactorField.getText();
                    dishPriceFactor = Double.parseDouble(dishPriceFactorString) / 100.0;
                    dishPrice = 0;
                    for (DishLine dl : chosenDishLines) {
                         dishPrice += (dl.getAmount() * dl.getIngredient().getPrice());
                    }
                    dishPrice *= dishPriceFactor;
                    dishPrice = Math.round(dishPrice * 100.0) / 100.0;
                    String dishPriceString = nf.format(dishPrice);

                    dishNameLabel.setText("Dish name: " + dishNameString);
                    dishPriceLabel.setText("Dish price: " + dishPriceString + " NOK");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    EventHandler<ActionEvent> removeFromDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean remove = false;
                if (selectedDishLine != null) {
                    for (DishLine dl : chosenDishLines) {
                        if (dl.getIngredient().getIngredientName().equals(selectedDishLine.getIngredient().getIngredientName())) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        chosenDishLines.remove(selectedDishLine);
                        chosenIngTable.setItems(chosenDishLines);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ingredient cannot be removed");
                        alert.setContentText("The selected ingredient cannot be removed from the dish,\nas the dish does not contain it.");
                        alert.showAndWait();
                    }
                }
            } catch(Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> addToDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                if (selectedIngredient != null) {
                    for (DishLine dl : chosenDishLines) {
                        if (dl.getIngredient().getIngredientName().equals(selectedIngredient.getIngredientName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        chosenDishLines.add(new DishLine(selectedIngredient));
                        chosenIngTable.setItems(chosenDishLines);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Ingredient cannot be added");
                        alert.setContentText("This ingredient cannot be added to the dish,\nas the dish already contains it.");
                        alert.showAndWait();
                    }
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

        ingredientComboBox.setItems(ingredientList);
        ingredientComboBox.setConverter(new StringConverter<Ingredient>() {
            @Override
            public String toString(Ingredient ingredient) {
                if (ingredient == null) {
                    return null;
                } else {
                    return ingredient.getIngredientName();
                }
            }
            @Override
            public Ingredient fromString(String string) {
                return null;
            }
        });

        ingredientComboBox.valueProperty().addListener(new ChangeListener<Ingredient>() {
            @Override
            public void changed(ObservableValue<? extends Ingredient> observable, Ingredient oldValue, Ingredient newValue) {
                selectedIngredient = newValue;
            }
        });

        // CellValueFactories for DishLine tablecolumns
        chosenIngName.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        chosenIngAmount.setCellValueFactory(new PropertyValueFactory<DishLine, Double>("amount"));
        chosenIngUnit.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        chosenIngPrice.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));


        chosenIngName.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(ingredient.getIngredientName());
                    }
                }
            };
        });

        chosenIngAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        chosenIngAmount.setOnEditCommit(
                new EventHandler<CellEditEvent<DishLine, Double>>() {
                    @Override
                    public void handle(CellEditEvent<DishLine, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                        // = event.getNewValue();
                    }
                });

        chosenIngPrice.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(ingredient.getPrice()));
                    }
                }
            };
        });

        /*

        chosenIngPrice.setCellFactory(lv -> {
            TableCell<DishLine, Ingredient> cell = new TableCell<>();
            cell.itemProperty().addListener(new ChangeListener<Ingredient>() {
                @Override
                public void changed(ObservableValue<? extends Ingredient> observable, Ingredient oldValue, Ingredient newValue) {
                    if (newValue != null) {
                        cell.setText(String.valueOf(newValue.getPrice() * ));
                    }
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return cell;
        });

*/
        chosenIngUnit.setCellFactory(column -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                protected void updateItem(Ingredient ingredient, boolean empty) {
                    if(ingredient == null || empty) {
                        setText(null);
                    } else {
                        setText(ingredient.getUnit());
                    }
                }
            };
        });




        chosenIngTable.getColumns().setAll(chosenIngName, chosenIngAmount, chosenIngUnit, chosenIngPrice);
        chosenIngTable.setEditable(true);
        chosenIngTable.setItems(chosenDishLines);

        addIngToDishButton.setOnAction(addToDishButtonClick);
        removeIngFromDishButton.setOnAction(removeFromDishButtonClick);


        commitNewDishButton.setOnAction(commitNewDishEvent);
        applyNewDishChangesButton.setOnAction(applyChanges);

    }
}
