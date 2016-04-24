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
    public Button editApplyButton;
    public Button commitEditButton;

    private MealType selectedMealType;
    private String menuNameString = selectedMenu.getName();
    private double menuPrice = selectedMenu.getTotalPrice();
    private String menuPriceString = nf.format(menuPrice);



    private ObservableList<MenuLine> oldMenuLines = FXCollections.observableArrayList();
    private ObservableList<MenuLine> newMenuLines = FXCollections.observableArrayList();

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


    EventHandler<ActionEvent> removeDish = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedMenuLine = (MenuLine) currentDishTable.getSelectionModel().getSelectedItem();
                if (selectedMenuLine != null) {
                    newMenuLines.remove(selectedMenuLine);
                    currentDishTable.setItems(newMenuLines);
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
                menuNameString = editMenuNameField.getText();
                menuPrice = selectedMenu.getTotalPrice();
                menuPriceString = nf.format(menuPrice);
                editMenuNameLabel.setText("Menu name: " + menuNameString);
                editMenuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");

                selectedMenu.setName(menuNameString);
                selectedMenu.setMealType(selectedMealType);
                selectedMenu.setMenuLines(newMenuLines);
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

        oldMenuLines = selectedMenu.getMenuLines();
        newMenuLines.addAll(oldMenuLines);


        editMenuNameField.setText(selectedMenu.getName());
        editMenuNameLabel.setText("Menu name: " + selectedMenu.getName());
        editMenuPriceLabel.setText("Menu price: " + nf.format(menuPrice) + " NOK");
        editMealTypeLabel.setText("Meal type: " + selectedMenu.getMealType());


        editMealTypeCB.setItems(mealTypes);
        editMealTypeCB.setCellFactory(column -> {
            return new ListCell<MealType>() {
                @Override
                public void updateItem(MealType mealType, boolean empty) {
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
        currentDishTable.setItems(oldMenuLines);
        editAddToMenuButton.setOnAction(editAddToMenuButtonClick);
        commitEditButton.setOnAction(commitEdit);
        editRemoveFromMenuButton.setOnAction(removeDish);

    }

}
