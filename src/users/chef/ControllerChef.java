package users.chef;

/**
 * Created by Axel Kvistad on 13.04.2016
 */
import classpackage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChef implements Initializable{

    @FXML
    public BorderPane rootPaneDriver;
    public Button ordersButton;
    public Button menusButton;
    public Button dishesButton;
    public Button ingredientsButton;
    private GridPane centerDisplay;
    protected static TestObjects testObjects = new TestObjects();
    protected static ObservableList<Ingredient> testIngredients = testObjects.allIngredients;
    protected static ObservableList<DishLine> testDishLines = testObjects.allIngredientsDL;
    protected static ObservableList<MenuLine> testMenuLines = testObjects.allDishes;
    protected static ObservableList<classpackage.Menu> testMenus = testObjects.allMenus;
    protected static ObservableList<Supplier> testSuppliers = testObjects.supplierList;
    protected static ObservableList<Dish> testDishes = testObjects.dishList;
    protected static Ingredient selectedIngredient;
    protected static Dish selectedDish;
    protected static DishLine selectedDishLine;
    protected static MenuLine selectedMenuLine;
    protected static Menu selectedMenu;
    protected static Supplier selectedSupplier;



    EventHandler<ActionEvent> showMenusEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChefMenus.fxml"));
                centerDisplay = loader.load();
                rootPaneDriver.setCenter(centerDisplay);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> showIngredientsEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChefIngredients.fxml"));
                centerDisplay = loader.load();
                rootPaneDriver.setCenter(centerDisplay);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> showDishesEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChefDishes.fxml"));
                centerDisplay = loader.load();
                rootPaneDriver.setCenter(centerDisplay);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        menusButton.setOnAction(showMenusEvent);
        ingredientsButton.setOnAction(showIngredientsEvent);
        dishesButton.setOnAction(showDishesEvent);
    }
}
