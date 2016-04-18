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
public class ControllerChefAddDish extends ControllerChefDishes implements Initializable {

    public GridPane addDishGP;
    public TextField dishNameField;
    public TextField dishPriceFactorField;
    public ComboBox<DishLine> chooseIngredientCB;
    public Button addToDishButton;
    public TableView chosenIngTable;
    public TableColumn chosenIngName;
    public TableColumn chosenIngAmount;
    public TableColumn chosenIngUnit;
    public TableColumn chosenIngPrice;
    public Button setDishNameButton;
    public Button setPriceButton;
    public Label dishNameLabel;
    public Label dishPriceLabel;
    public Button addDishApplyButton;
    public Button removeFromDishButton;
    private DishLine selectedIngredient;

    ObservableList<DishLine> chosenIngredients = FXCollections.observableArrayList();


    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> addDishApplyButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String name = dishNameField.getText();
                String priceFactorString = dishPriceFactorField.getText();
                double priceFactor = Double.parseDouble(priceFactorString) / 100;
                double price = 0;
                for (DishLine dl : testDishLines) {
                    price += (dl.getAmount() * dl.getIngredient().getPrice());
                }
                price *= priceFactor;
                double finalPrice = Math.round(price * 100.0) / 100.0;
                Dish newDish = new Dish(finalPrice, name, testDishLines);
                MenuLine menuLine = new MenuLine(newDish);
                testMenuLines.add(menuLine);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> setDishNameButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!dishNameField.getText().isEmpty()) {
                    String name = dishNameField.getText();
                    dishNameLabel.setText("Dish name: " + name);
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
                if (!dishPriceFactorField.getText().isEmpty() && !chosenIngredients.isEmpty()) {
                    String priceFactorString = dishPriceFactorField.getText();
                    double priceFactor = Double.parseDouble(priceFactorString) / 100;
                    double price = 0;
                    for (DishLine dl : testDishLines) {
                        price += (dl.getAmount() * dl.getIngredient().getPrice());
                    }
                    price *= priceFactor;
                    dishPriceLabel.setText("Price: " + nf.format(price) + " NOK");
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
                    for (DishLine dl : chosenIngredients) {
                        if (dl.getIngredient().getIngredientName().equals(selectedIngredient.getIngredient().getIngredientName())) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        chosenIngredients.remove(selectedIngredient);
                        chosenIngTable.setItems(chosenIngredients);
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
                    for (DishLine dl : chosenIngredients) {
                        if (dl.getIngredient().getIngredientName().equals(selectedIngredient.getIngredient().getIngredientName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        chosenIngredients.add(selectedIngredient);
                        chosenIngTable.setItems(chosenIngredients);
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

        chooseIngredientCB.setItems(testDishLines);
        chooseIngredientCB.setConverter(new StringConverter<DishLine>() {
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

        chooseIngredientCB.valueProperty().addListener(new ChangeListener<DishLine>() {
            @Override
            public void changed(ObservableValue<? extends DishLine> observable, DishLine oldValue, DishLine newValue) {
                selectedIngredient = newValue;
            }
        });

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
        chosenIngTable.setItems(chosenIngredients);

        addToDishButton.setOnAction(addToDishButtonClick);
        setDishNameButton.setOnAction(setDishNameButtonClick);
        setPriceButton.setOnAction(setPriceButtonClick);
        removeFromDishButton.setOnAction(removeFromDishButtonClick);
        addDishApplyButton.setOnAction(addDishApplyButtonClick);

    }
}
