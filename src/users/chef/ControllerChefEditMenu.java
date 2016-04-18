package users.chef;

import classpackage.Dish;
import classpackage.MenuLine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/18/16.
 */
public class ControllerChefEditMenu extends ControllerChefMenus implements Initializable {
    @FXML
    public GridPane editMenuGP;
    public TextField editMenuNameField;
    public ComboBox<String> editMealTypeCB;
    public ComboBox<MenuLine> editDishCB;
    public Button editAddToMenuButton;
    public Button editRemoveFromMenuButton;
    public TableView currentDishTable;
    public TableColumn currentDishNameCol;
    public TableColumn currentDishAmountCol;
    public TableColumn currentDishPriceCol;
    public Label editMenuNameLabel;
    public Label editMenuPriceLabel;
    public Button editApplyButton;
    public Button commitEditButton;

    private String selectedMealType;
    private String menuNameString;
    private double menuPrice;
    private String menuPriceString;

    private ObservableList<String> mealTypes = FXCollections.observableArrayList(
            "Non-vegetarian", "Vegetarian", "Vegan"
    );

    private ObservableList<MenuLine> oldDishes = FXCollections.observableArrayList();
    private ObservableList<MenuLine> newDishes = FXCollections.observableArrayList();

    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> editAddToMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                if (selectedDish != null) {
                    for (MenuLine ml : newDishes) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDish().getDishName())) {
                            add = false;
                        }
                    } if (add) {
                        newDishes.add(selectedDish);
                        currentDishTable.setItems(newDishes);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Dish cannot be added");
                        alert.setContentText("This dish cannot be added to the menu,\nas the menu already contains it.");
                        alert.showAndWait();
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
                menuNameString = editMenuNameField.getText();
                for (MenuLine ml : newDishes) {
                    menuPrice += ml.getDish().getPrice();
                }
                menuPrice = Math.round(menuPrice * 100.0) / 100.0;
                menuPriceString = nf.format(menuPrice);
                editMenuNameLabel.setText("Menu name: " + menuNameString);
                editMenuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> removeDish = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean remove = false;
                for (MenuLine ml : newDishes) {
                    if (ml.getDish().getDishName().equals(selectedDish.getDish().getDishName())) {
                        remove = true;
                    }
                } if (remove) {
                    newDishes.remove(selectedDish);
                    currentDishTable.setItems(newDishes);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> commitEdit = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedMenu.setName(menuNameString);
                selectedMenu.setMealType(selectedMealType);
                selectedMenu.setMenuLines(newDishes);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        oldDishes = selectedMenu.getMenuLines();
        newDishes.addAll(oldDishes);

        editMenuNameField.setText(selectedMenu.getName());

        editMealTypeCB.setItems(mealTypes);
        editMealTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedMealType = newValue;
            }
        });

        editMealTypeCB.setValue(selectedMenu.getMealType());
        boolean newType = true;
        for (String m : mealTypes) {
            if (selectedMenu.getMealType().equals(m)){
                newType = false;
            }
        }
        if (newType) {
            mealTypes.add(selectedMenu.getMealType());
        }

        editDishCB.setItems(testDishes);
        editDishCB.setConverter(new StringConverter<MenuLine>() {
            @Override
            public String toString(MenuLine menuLine) {
                if (menuLine == null) {
                    return null;
                } else {
                    return menuLine.getDish().getDishName();
                }
            }
            @Override
            public MenuLine fromString(String string) {
                return null;
            }
        });

        editDishCB.valueProperty().addListener(new ChangeListener<MenuLine>() {
            @Override
            public void changed(ObservableValue<? extends MenuLine> observable, MenuLine oldValue, MenuLine newValue) {
                selectedDish = newValue;
            }
        });

        currentDishNameCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        currentDishAmountCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Integer>("amount"));
        currentDishPriceCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));

        currentDishNameCol.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if (dish == null || empty) {
                        setText(null);
                    } else {
                        setText(dish.getDishName());
                    }
                }
            };
        });

        currentDishAmountCol.setCellFactory(column -> {
            return new TableCell<MenuLine, Integer>() {
                @Override
                protected void updateItem(Integer amount, boolean empty) {
                    if (amount == null || empty) {
                        setText(null);
                    } else {
                        setText(amount.toString());
                    }
                }
            };
        });

        currentDishPriceCol.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if (dish == null || empty) {
                        setText(null);
                    } else {
                        double dishPrice = dish.getPrice();
                        String dishPriceString = nf.format(dishPrice);
                        setText(dishPriceString);
                    }
                }
            };
        });

        currentDishTable.getColumns().setAll(currentDishNameCol, currentDishAmountCol, currentDishPriceCol);
        currentDishTable.setEditable(true);
        currentDishTable.setItems(oldDishes);
        editAddToMenuButton.setOnAction(editAddToMenuButtonClick);
        editApplyButton.setOnAction(applyChanges);
        commitEditButton.setOnAction(commitEdit);
        editRemoveFromMenuButton.setOnAction(removeDish);

    }

}
