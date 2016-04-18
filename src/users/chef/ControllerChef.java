package users.chef;

/**
 * Created by Axel Kvistad on 13.04.2016
 */
import classpackage.DishLine;
import classpackage.Ingredient;
import classpackage.MenuLine;
import classpackage.TestObjects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
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



    EventHandler<ActionEvent> menuButtonClick = new EventHandler<ActionEvent>() {
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

    EventHandler<ActionEvent> ingredientsButtonClick = new EventHandler<ActionEvent>() {
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

    EventHandler<ActionEvent> dishesButtonClick = new EventHandler<ActionEvent>() {
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
        menusButton.setOnAction(menuButtonClick);
        ingredientsButton.setOnAction(ingredientsButtonClick);
        dishesButton.setOnAction(dishesButtonClick);
    }
}
