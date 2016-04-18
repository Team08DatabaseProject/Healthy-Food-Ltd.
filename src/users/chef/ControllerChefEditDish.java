package users.chef;

import classpackage.*;
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
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/18/16.
 */
public class ControllerChefEditDish extends ControllerChefDishes implements Initializable {

    public GridPane editDishGP;
    public TextField editDishNameField;
    public TextField editDishPriceFactorField;
    public ComboBox<DishLine> editIngredientCB;
    public Button addIngButton;
    public Button removeIngButton;
    public TableView currentIngTable;
    public TableColumn currentIngName;
    public TableColumn currentIngAmount;
    public TableColumn currentIngUnit;
    public TableColumn currentIngPrice;
    public Button editDishNameButton;
    public Button editPriceButton;
    public Label currentNameLabel;
    public Label currentPriceLabel;
    public Button editDishApplyButton;
    private TestObjects testObjects = new TestObjects();
    private DishLine selectedIngredient;

    ObservableList<DishLine> testIngredients  = testObjects.allIngredientsDL;
    ObservableList<DishLine> currentIngredients = chosenDish.getDish().getAllDishLinesForThisDish();


    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> editDishApplyButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String name = editDishNameField.getText();
                String priceFactorString = editDishPriceFactorField.getText();
                double priceFactor = Double.parseDouble(priceFactorString) / 100;
                double price = 0;
                for (DishLine dl : currentIngredients) {
                    price += (dl.getAmount() * dl.getIngredient().getPrice());
                }
                price *= priceFactor;
                double finalPrice = Math.round(price * 100.0) / 100.0;
                Dish newDish = new Dish(finalPrice, name, currentIngredients);
                chosenDish.setDish(newDish);
                chosenDish.setPriceFactor(priceFactor);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> setDishNameButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!editDishNameField.getText().isEmpty()) {
                    String name = editDishNameField.getText();
                    currentNameLabel.setText("Dish name: " + name);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> setPriceButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!editDishPriceFactorField.getText().isEmpty() && !currentIngredients.isEmpty()) {
                    String priceFactorString = editDishPriceFactorField.getText();
                    double priceFactor = Double.parseDouble(priceFactorString) / 100;
                    double price = 0;
                    for (DishLine dl : currentIngredients) {
                        price += (dl.getAmount() * dl.getIngredient().getPrice());
                    }
                    price *= priceFactor;
                    currentPriceLabel.setText("Price: " + nf.format(price) + " NOK");
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
                if (selectedIngredient != null) {
                    for (DishLine dl : currentIngredients) {
                        if (dl.getIngredient().getIngredientName().equals(selectedIngredient.getIngredient().getIngredientName())) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        currentIngredients.remove(selectedIngredient);
                        currentIngTable.setItems(currentIngredients);
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
                    for (DishLine dl : currentIngredients) {
                        if (dl.getIngredient().getIngredientName().equals(selectedIngredient.getIngredient().getIngredientName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        currentIngredients.add(selectedIngredient);
                        currentIngTable.setItems(currentIngredients);
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

        editIngredientCB.setItems(testIngredients);
        editDishNameField.setText(chosenDish.getDish().getDishName());
        editDishPriceFactorField.setText(nf.format(chosenDish.getPriceFactor() * 100));
        currentNameLabel.setText("Dish name: " + chosenDish.getDish().getDishName());
        currentPriceLabel.setText("Price: " + chosenDish.getDish().getPrice());
        editIngredientCB.setConverter(new StringConverter<DishLine>() {
            @Override
            public String toString(DishLine dishLine) {
                if (dishLine == null) {
                    return null;
                } else {
                    return dishLine.getIngredient().getIngredientName();
                }
            }
            @Override
            public DishLine fromString(String string) {
                return null;
            }
        });

        editIngredientCB.valueProperty().addListener(new ChangeListener<DishLine>() {
            @Override
            public void changed(ObservableValue<? extends DishLine> observable, DishLine oldValue, DishLine newValue) {
                selectedIngredient = newValue;
            }
        });

        currentIngName.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        currentIngAmount.setCellValueFactory(new PropertyValueFactory<DishLine, Double>("amount"));
        currentIngUnit.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        currentIngPrice.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));


        currentIngName.setCellFactory(column -> {
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

        currentIngAmount.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        currentIngAmount.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<DishLine, Double>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<DishLine, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                        // = event.getNewValue();
                    }
                });

        currentIngPrice.setCellFactory(column -> {
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
        currentIngUnit.setCellFactory(column -> {
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


        currentIngTable.getColumns().setAll(currentIngName, currentIngAmount, currentIngUnit, currentIngPrice);
        currentIngTable.setEditable(true);
        currentIngTable.setItems(currentIngredients);

        addIngButton.setOnAction(addToDishButtonClick);
        editDishNameButton.setOnAction(setDishNameButtonClick);
        editPriceButton.setOnAction(setPriceButtonClick);
        removeIngButton.setOnAction(removeFromDishButtonClick);
        editDishApplyButton.setOnAction(editDishApplyButtonClick);

    }
}
