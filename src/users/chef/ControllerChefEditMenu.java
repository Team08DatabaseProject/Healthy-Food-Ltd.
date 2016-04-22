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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/18/16.
 */
public class ControllerChefEditMenu extends ControllerChef implements Initializable {
    
    @FXML
    public GridPane editMenuGP;
    public TextField editMenuNameField;
    public ComboBox<String> editMealTypeCB;
    public ComboBox<Dish> editDishCB;
    public Button editAddToMenuButton;
    public Button editRemoveFromMenuButton;
    public TableView currentDishTable;
    public TableColumn currentDishNameCol;
    public TableColumn currentDishAmountCol;
    public TableColumn currentDishPriceCol;
    public Label editMenuNameLabel;
    public Label editMenuPriceLabel;
    public Label editMealTypeLabel;
    public Button editApplyButton;
    public Button commitEditButton;

    private String selectedMealType;
    private String menuNameString;
    private double menuPrice;
    private String menuPriceString;

    private ObservableList<String> mealTypes = FXCollections.observableArrayList(
            "Non-vegetarian", "Vegetarian", "Vegan"
    );

    private ObservableList<MenuLine> oldMenuLines = FXCollections.observableArrayList();
    private ObservableList<MenuLine> newMenuLines = FXCollections.observableArrayList();

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
                    for (MenuLine ml : newMenuLines) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDishName())) {
                            add = false;
                        }
                    } if (add) {
                        MenuLine newML= new MenuLine(selectedDish);
                        newMenuLines.add(newML);
                        currentDishTable.setItems(newMenuLines);
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
                menuPrice = getMenuPrice();
                menuPriceString = nf.format(menuPrice);
                editMenuNameLabel.setText("Menu name: " + menuNameString);
                editMenuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    private double getMenuPrice() {
        menuPrice = 0;
        for (MenuLine ml : newMenuLines) {
            menuPrice += ml.getDish().getPrice() * ml.getAmount();
        }
        menuPrice = Math.round(menuPrice * 100.0) / 100.0;
        return menuPrice;
    }

    EventHandler<ActionEvent> removeDish = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean remove = false;
                for (MenuLine ml : newMenuLines) {
                    if (ml.getDish().getDishName().equals(selectedDish.getDishName())) {
                        selectedMenuLine = ml;
                        remove = true;
                    }
                } if (remove) {
                    newMenuLines.remove(selectedMenuLine);
                    currentDishTable.setItems(newMenuLines);
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
                selectedMenu.setMenuLines(newMenuLines);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        oldMenuLines = selectedMenu.getMenuLines();
        newMenuLines.addAll(oldMenuLines);

        menuNameString = selectedMenu.getName();
        menuPrice = getMenuPrice();
        menuPriceString = nf.format(menuPrice);


        editMenuNameField.setText(menuNameString);
        editMenuNameLabel.setText("Menu name: " + menuNameString);
        editMenuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
        editMealTypeLabel.setText("Meal type: " + selectedMenu.getMealType());



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

        editDishCB.setItems(dishList);
        editDishCB.setConverter(new StringConverter<Dish>() {
            @Override
            public String toString(Dish dish) {
                if (dish == null) {
                    return null;
                } else {
                    return dish.getDishName();
                }
            }
            @Override
            public Dish fromString(String string) {
                return null;
            }
        });

        editDishCB.valueProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
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

        currentDishAmountCol.setCellFactory(TextFieldTableCell.<MenuLine, Integer>forTableColumn(new IntegerStringConverter()));
        currentDishAmountCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MenuLine, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MenuLine, Integer> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                    }
                }
        );

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
        currentDishTable.setItems(oldMenuLines);
        editAddToMenuButton.setOnAction(editAddToMenuButtonClick);
        editApplyButton.setOnAction(applyChanges);
        commitEditButton.setOnAction(commitEdit);
        editRemoveFromMenuButton.setOnAction(removeDish);

    }

}
