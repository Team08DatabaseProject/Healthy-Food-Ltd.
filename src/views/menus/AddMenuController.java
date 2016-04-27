package views.menus;

/**
 * Created by axelkvistad on 4/14/16.
 */
import classpackage.*;
import classpackage.Menu;
import main.PopupDialog;
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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMenuController extends MenusController implements Initializable{

    @FXML
    public GridPane subWindowGP;
    public TextField menuNameField;
    public ComboBox<MealType> mealTypeCB;
    public ComboBox<Dish> chooseDishCB;
    public Button addToMenuButton;
    public Button removeFromMenuButton;
    public TableView chosenDishTable;
    public TableColumn chosenDishName;
    public TableColumn chosenDishAmount;
    public TableColumn chosenDishPrice;
    public TableColumn chosenDishPriceFactor;
    public Label menuNameLabel;
    public Label menuPriceLabel;
    public Button addMenuButton;
    public Button applyButton;

    private MealType selectedMealType;
    private String menuNameString;
    private double menuPrice = 0;
    private String menuPriceString;

    private ObservableList<MenuLine> chosenMenuLines = FXCollections.observableArrayList();

    EventHandler<ActionEvent> commitNewMenuEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                menuNameString = menuNameField.getText();
                boolean add = true;
                Menu newMenu = new Menu(menuNameString, selectedMealType, chosenMenuLines);
                menuPrice = newMenu.getTotalPrice();
                menuPriceString = nf.format(menuPrice);
                for (Menu m : menuList) {
                    if (m.getName().equals(newMenu.getName())) {
                        add = false;
                    }
                }
                menuNameLabel.setText("Menu name: " + menuNameString);
                menuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
                if (add) {
                    if (db.addMenu(newMenu)) {
                        menuList.add(newMenu);
                        PopupDialog.confirmationDialog("Result", "Menu \"" + newMenu.getName() + "\" added.");
                    } else {
                        PopupDialog.errorDialog("Error", "Menu could not be added.");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> applyChanges = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                menuNameString = menuNameField.getText();
                menuPrice = 0;
                for (MenuLine ml : chosenMenuLines) {
                    menuPrice += ml.getDish().getPrice() * ml.getAmount() * ml.getPriceFactor();
                }
                menuPrice = Math.round(menuPrice * 100.0) / 100.0;
                menuPriceString = nf.format(menuPrice);
                menuNameLabel.setText("Menu name: " + menuNameString);
                menuPriceLabel.setText("Menu price: " + menuPriceString + " NOK");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> addToMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                if (selectedDish != null) {
                    for (MenuLine ml : chosenMenuLines) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDishName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        MenuLine newMenuLine = new MenuLine(selectedDish);
                        chosenMenuLines.add(newMenuLine);
                        chosenDishTable.setItems(chosenMenuLines);
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

    EventHandler<ActionEvent> removeFromMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean remove = false;
                if (selectedDish != null) {
                    for (MenuLine ml : chosenMenuLines) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDishName())) {
                            selectedMenuLine = ml;
                            remove = true;
                        }
                    }
                    if (remove) {
                        chosenMenuLines.remove(selectedMenuLine);
                        chosenDishTable.setItems(chosenMenuLines);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setHeaderText("Dish cannot be removed");
                        alert.setContentText("The selected dish cannot be removed from the menu,\nas the menu does not contain it.");
                        alert.showAndWait();
                    }
                }
            } catch(Exception exc) {
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

        mealTypeCB.setItems(mealTypes);
        mealTypeCB.setCellFactory(column -> {
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

        mealTypeCB.valueProperty().addListener(new ChangeListener<MealType>() {
            @Override
            public void changed(ObservableValue<? extends MealType> observable, MealType oldValue, MealType newValue) {
                selectedMealType = newValue;
            }
        });


        chooseDishCB.setItems(dishList);
        chooseDishCB.setCellFactory(column -> {
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

        chooseDishCB.valueProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
                selectedDish = newValue;
            }
        });

        chosenDishTable.setEditable(true);

        chosenDishName.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        chosenDishAmount.setCellValueFactory(new PropertyValueFactory<MenuLine, Integer>("amount"));
        chosenDishPrice.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        chosenDishPriceFactor.setCellValueFactory(new PropertyValueFactory<MenuLine, Double>("priceFactor"));

        chosenDishName.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if(dish == null || empty) {
                        setText(null);
                    } else {
                        setText(dish.getDishName());
                    }
                }
            };
        });

        chosenDishAmount.setCellFactory(TextFieldTableCell.<MenuLine, Integer>forTableColumn(new IntegerStringConverter()));
        chosenDishAmount.setOnEditCommit(
                new EventHandler<CellEditEvent<MenuLine, Integer>>() {
                    @Override
                    public void handle(CellEditEvent<MenuLine, Integer> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                    }
                });

        chosenDishPrice.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if(dish == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(dish.getPrice()));
                    }
                }
            };
        });

        chosenDishPriceFactor.setCellFactory(TextFieldTableCell.<MenuLine, Double>forTableColumn(new DoubleStringConverter()));
        chosenDishPriceFactor.setOnEditCommit(
                new EventHandler<CellEditEvent<MenuLine, Double>>() {
                    @Override
                    public void handle(CellEditEvent<MenuLine, Double> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setPriceFactor(event.getNewValue());
                    }
                });

        chosenDishTable.getColumns().setAll(chosenDishName, chosenDishAmount, chosenDishPrice, chosenDishPriceFactor);

        addToMenuButton.setOnAction(addToMenuButtonClick);
        addMenuButton.setOnAction(commitNewMenuEvent);
        removeFromMenuButton.setOnAction(removeFromMenuButtonClick);
        applyButton.setOnAction(applyChanges);

    }
}