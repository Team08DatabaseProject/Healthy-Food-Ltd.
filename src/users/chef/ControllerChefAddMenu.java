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
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class ControllerChefAddMenu extends ControllerChefMenus implements Initializable{

    @FXML
    public GridPane addMenuGP;
    public TextField menuNameField;
    public TextField menuPriceFactorField;
    public ComboBox<String> mealTypeCB;
    public ComboBox<MenuLine> chooseDishCB;
    public Button addToMenuButton;
    public Button removeFromMenuButton;
    public TableView chosenDishTable;
    public TableColumn chosenDishName;
    public TableColumn chosenDishAmount;
    public TableColumn chosenDishPrice;
    public Label menuNameLabel;
    public Label menuPriceLabel;
    public Button addMenuButton;
    public Button applyButton;

    private String selectedMealType;
    private String menuNameString;
    private double menuPrice = 0;
    private String menuPriceString;

    private ObservableList<MenuLine> chosenDishes = FXCollections.observableArrayList();
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
                Menu newMenu = new Menu(menuNameString, selectedMealType, chosenDishes);
                testMenus.add(newMenu);
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
                String priceFactorString = menuPriceFactorField.getText();
                double priceFactor = Double.parseDouble(priceFactorString) / 100;
                for (MenuLine ml : chosenDishes) {
                    menuPrice += ml.getDish().getPrice();
                }
                menuPrice *= priceFactor;
                menuPriceString = nf.format(menuPrice);
                menuNameLabel.setText("Menu name: " + menuNameString);
                menuPriceLabel.setText("Menu price: " + menuPriceString);
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
                    for (MenuLine ml : chosenDishes) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDish().getDishName())) {
                            add = false;
                        }
                    }
                    if (add) {
                        chosenDishes.add(selectedDish);
                        chosenDishTable.setItems(chosenDishes);
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
                    for (MenuLine ml : chosenDishes) {
                        if (ml.getDish().getDishName().equals(selectedDish.getDish().getDishName())) {
                            remove = true;
                        }
                    }
                    if (remove) {
                        chosenDishes.remove(selectedDish);
                        chosenDishTable.setItems(chosenDishes);
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
        chooseDishCB.setConverter(new StringConverter<MenuLine>() {
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

        chooseDishCB.valueProperty().addListener(new ChangeListener<MenuLine>() {
            @Override
            public void changed(ObservableValue<? extends MenuLine> observable, MenuLine oldValue, MenuLine newValue) {
                selectedDish = newValue;
            }
        });

        chosenDishTable.setEditable(true);

        chosenDishName.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        chosenDishAmount.setCellValueFactory(new PropertyValueFactory<MenuLine, Integer>("amount"));
        chosenDishPrice.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));

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

        chosenDishAmount.setCellFactory(column -> {
            return new TableCell<MenuLine, Integer>() {
                @Override
                protected void updateItem(Integer amount, boolean empty) {
                    if(amount == null || empty) {
                        setText(null);
                    } else {
                        setText(amount.toString());
                    }
                }
            };
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



        chosenDishTable.getColumns().setAll(chosenDishName, chosenDishAmount, chosenDishPrice);


        addToMenuButton.setOnAction(addToMenuButtonClick);
        addMenuButton.setOnAction(addMenu);
        removeFromMenuButton.setOnAction(removeFromMenuButtonClick);
        applyButton.setOnAction(applyChanges);

    }
}