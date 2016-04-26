package users.chef;

/**
 * Created by Axel Kvistad on 13.04.2016
 */
import classpackage.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class ControllerChef implements Initializable{

    @FXML
    public BorderPane rootPaneDriver;
    public Button ordersButton;
    public Button menusButton;
    public Button dishesButton;
    public Button ingredientsButton;
    private GridPane centerDisplay;
    protected static SqlQueries db = new SqlQueries();
    protected static ObservableList<Supplier> supplierList = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredientList = db.getAllIngredients(supplierList);
    protected static ObservableList<Dish> dishList = db.getAllDishes(ingredientList);
    protected static ObservableList<Menu> menuList = db.getAllMenus(dishList);
    protected static ObservableList<Order> orderList = db.getOrders(2, dishList);
    protected static ObservableList<OrderStatus> statusTypes = db.getStatusTypes();
    protected static ObservableList<MealType> mealTypes = db.getAllMealTypes();


    protected static Ingredient selectedIngredient;
    protected static Dish selectedDish;
    protected static DishLine selectedDishLine;
    protected static MenuLine selectedMenuLine;
    protected static Menu selectedMenu;
    protected static Supplier selectedSupplier;
    protected static Order selectedOrder;

    protected final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    // Display orders relevant to chef
    EventHandler<ActionEvent> showOrdersEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChefOrders.fxml"));
                centerDisplay = loader.load();
                rootPaneDriver.setCenter(centerDisplay);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    // Display "Menu" menu
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

    // Display Ingredient menu
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

    // Display Dish menu
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootPaneDriver.requestFocus();
            }
        });

        ordersButton.setOnAction(showOrdersEvent);
        menusButton.setOnAction(showMenusEvent);
        ingredientsButton.setOnAction(showIngredientsEvent);
        dishesButton.setOnAction(showDishesEvent);
    }
}
