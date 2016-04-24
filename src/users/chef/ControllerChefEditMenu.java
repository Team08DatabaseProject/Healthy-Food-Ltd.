package users.chef;

import classpackage.Dish;
import classpackage.MealType;
import classpackage.MenuLine;
import div.PopupDialog;
import javafx.application.Platform;
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
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/18/16.
 */
public class ControllerChefEditMenu extends ControllerChef implements Initializable {
    
    @FXML
    public GridPane subWindowGP;
    public TextField editMenuNameField;
    public ComboBox<MealType> editMealTypeCB;
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
    public Button commitEditButton;

    private MealType selectedMealType = selectedMenu.getMealType();
    ObservableList<MenuLine> menuLines = selectedMenu.getMenuLines();
    ObservableList<MenuLine> menuLinesOriginal = FXCollections.observableArrayList();

    ObservableList<MenuLine> addList = FXCollections.observableArrayList();
    ObservableList<MenuLine> removeList = FXCollections.observableArrayList();
    ObservableList<MenuLine> updateList = FXCollections.observableArrayList();



    EventHandler<ActionEvent> editAddToMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                if (selectedDish != null) {
                    for (MenuLine ml : menuLines) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDishName())) {
                            add = false;
                        }
                    } if (add) {
                        MenuLine newML= new MenuLine(selectedDish);
                        newML.setNewlyCreated(true);
                        addList.add(newML);
                        menuLines.add(newML);
                        currentDishTable.setItems(menuLines);
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


    EventHandler<ActionEvent> removeDish = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            selectedMenuLine = (MenuLine) currentDishTable.getSelectionModel().getSelectedItem();
            try {
                if (selectedMenuLine != null) {
                    if (!(selectedMenuLine.isNewlyCreated() && selectedMenuLine.isChanged())) {
                        removeList.add(selectedMenuLine);
                    } else if (selectedMenuLine.isNewlyCreated() && selectedMenuLine.isChanged()) {
                        addList.remove(selectedMenuLine);
                        updateList.remove(selectedMenuLine);
                    } else if (selectedMenuLine.isNewlyCreated()) {
                        addList.remove(selectedMenuLine);
                    } else {
                        updateList.remove(selectedMenuLine);
                    }

                    menuLines.remove(selectedMenuLine);
                    currentDishTable.setItems(menuLines);
                    currentDishTable.getSelectionModel().clearSelection();
                } else {
                    PopupDialog.informationDialog("Information", "To remove a dish from the menu, first select it from the table by clicking it.\n" +
                            "After selecting a dish to remove, click the \"Remove dish from menu\" button.");
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

                if (!addList.isEmpty()) {
                    db.addMenuLine(selectedMenu, addList);
                }
                if (!updateList.isEmpty()) {
                    db.updateMenuLine(selectedMenu, updateList);
                }
                if (!removeList.isEmpty()) {
                    db.deleteMenuLines(selectedMenu, removeList);
                }

                if (db.updateMenu(selectedMenu)) {
                    PopupDialog.confirmationDialog("Result", "Menu \"" + selectedMenu.getName() + "\" successfully updated.");
                } else {
                    PopupDialog.errorDialog("Error", "Menu \"" + selectedMenu.getName() + "\" failed to update.");
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

        menuLinesOriginal.addAll(menuLines);

        String menuName = selectedMenu.getName();
        String menuPriceString = nf.format(selectedMenu.getTotalPrice());

        editMenuNameField.setText(menuName);
        editMenuNameLabel.setText("Menu name: " + menuName);
        editMenuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
        editMealTypeLabel.setText("Meal type: " + selectedMealType);

        editMealTypeCB.setItems(mealTypes);
        editMealTypeCB.setCellFactory(column -> {
            return new ListCell<MealType>() {
                @Override
                public void updateItem(MealType mealType, boolean empty) {
                    super.updateItem(mealType, empty);
                    if (!(mealType == null || empty)) {
                        setText(mealType.toString());
                    }
                }
            };
        });
        editMealTypeCB.valueProperty().addListener(new ChangeListener<MealType>() {
            @Override
            public void changed(ObservableValue<? extends MealType> observable, MealType oldValue, MealType newValue) {
                selectedMealType = newValue;
            }
        });
        editMealTypeCB.setValue(selectedMenu.getMealType());

        editDishCB.setItems(dishList);
        editDishCB.setCellFactory(column -> {
            return new ListCell<Dish>() {
                @Override
                public void updateItem(Dish dish, boolean empty) {
                    super.updateItem(dish, empty);
                    if (!(dish == null || empty)) {
                        setText(dish.toString());
                    }
                }
            };
        });
        editDishCB.valueProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
                selectedDish = newValue;
            }
        });

        currentDishNameCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        currentDishAmountCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Integer>("amount"));
        currentDishPriceCol.setCellValueFactory(new PropertyValueFactory<MenuLine, Double>("totalPrice"));

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
                        if (!event.getTableView().getItems().get(event.getTablePosition().getRow()).isChanged()) {
                            updateList.add(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);
                        }
                    }
                });

        currentDishPriceCol.setCellFactory(column -> {
            return new TableCell<MenuLine, Double>() {
                @Override
                protected void updateItem(Double totalPrice, boolean empty) {
                    if (totalPrice == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(totalPrice));
                    }
                }
            };
        });

        currentDishTable.getColumns().setAll(currentDishNameCol, currentDishAmountCol, currentDishPriceCol);
        currentDishTable.setEditable(true);
        currentDishTable.setItems(menuLines);
        editAddToMenuButton.setOnAction(editAddToMenuButtonClick);
        commitEditButton.setOnAction(commitEdit);
        editRemoveFromMenuButton.setOnAction(removeDish);

    }
}
