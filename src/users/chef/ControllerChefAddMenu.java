package users.chef;

/**
 * Created by axelkvistad on 4/14/16.
 */
import classpackage.*;
import classpackage.Menu;
import div.Login;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class ControllerChefAddMenu extends ControllerChef implements Initializable{

    @FXML
    public GridPane addMenuGP;
    public TextField menuNameField;
    public ComboBox<String> mealTypeCB;
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

    private String selectedMealType;
    private String menuNameString;
    private double menuPrice = 0;
    private String menuPriceString;

    private ObservableList<MenuLine> chosenMenuLines = FXCollections.observableArrayList();
    private ObservableList<String> mealTypes = FXCollections.observableArrayList(
            "Non-vegetarian", "Vegetarian", "Vegan"
    );

    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    EventHandler<ActionEvent> addMenu = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                boolean add = true;
                Menu newMenu = new Menu(menuNameString, selectedMealType, chosenMenuLines);
                for (Menu m : testMenus) {
                    if (m.getName().equals(newMenu.getName())) {
                        add = false;
                    }
                }
                if (add) {
                    testMenus.add(newMenu);
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

        mealTypeCB.setItems(mealTypes);
        mealTypeCB.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedMealType = newValue;
            }
        });
        chooseDishCB.setItems(testDishes);
        chooseDishCB.setConverter(new StringConverter<Dish>() {
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
                }
        );

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
                }
        );

        chosenDishTable.getColumns().setAll(chosenDishName, chosenDishAmount, chosenDishPrice, chosenDishPriceFactor);

        addToMenuButton.setOnAction(addToMenuButtonClick);
        addMenuButton.setOnAction(addMenu);
        removeFromMenuButton.setOnAction(removeFromMenuButtonClick);
        applyButton.setOnAction(applyChanges);

    }
}