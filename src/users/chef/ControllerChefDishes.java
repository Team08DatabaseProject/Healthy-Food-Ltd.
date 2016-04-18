package users.chef;

import classpackage.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/16/16.
 */
public class ControllerChefDishes extends ControllerChef implements Initializable {

    public GridPane dishesGP;
    public TableView dishTable;
    public TableColumn dishName;
    public TableColumn dishPrice;
    public Button addDishButton;
    public Button viewDishInfoButton;
    public Button removeDishButton;

    protected static MenuLine chosenDish;

    EventHandler<ActionEvent> addDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChefAddDish.fxml"));
                GridPane addDishGP = loader.load();
                Scene formScene = new Scene(addDishGP);
                Stage formStage = new Stage();
                formStage.setTitle("Add new dish");
                formStage.setScene(formScene);
                formStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> viewInfoButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                chosenDish = (MenuLine) dishTable.getSelectionModel().getSelectedItem();
                if (chosenDish != null) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("ChefEditDish.fxml"));
                    GridPane editDishGP = loader.load();
                    Scene formScene = new Scene(editDishGP);
                    Stage formStage = new Stage();
                    formStage.setTitle("Edit dish");
                    formStage.setScene(formScene);
                    formStage.show();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> removeDishButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                int selectedIndex = dishTable.getSelectionModel().getSelectedIndex();
                if (selectedIndex > -1) {
                    testDishLines.remove(dishTable.getItems().get(selectedIndex));
                    dishTable.setItems(testDishLines);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No dish selected");
                    alert.setContentText("Select a dish to delete it");
                    alert.showAndWait();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        final NumberFormat nf = NumberFormat.getNumberInstance();
        {
            nf.setMaximumFractionDigits(2);
        }

        dishName.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));
        dishPrice.setCellValueFactory(new PropertyValueFactory<MenuLine, Dish>("dish"));

        dishName.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getDishName());
                    }
                }
            };
        });

        dishPrice.setCellFactory(column -> {
            return new TableCell<MenuLine, Dish>() {
                @Override
                protected void updateItem(Dish item, boolean empty) {
                    if(item == null || empty) {
                        setText(null);
                    } else {
                        setText((String.valueOf(item.getPrice())));
                    }
                }
            };
        });


        dishTable.getColumns().setAll(dishName, dishPrice);
        dishTable.setItems(testMenuLines);
        addDishButton.setOnAction(addDishButtonClick);
        viewDishInfoButton.setOnAction(viewInfoButtonClick);


    }
}