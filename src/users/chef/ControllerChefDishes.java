package users.chef;

import classpackage.*;
import main.PopupDialog;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/16/16.
 */
public class ControllerChefDishes extends ControllerChef implements Initializable {

    public GridPane subMenuGP;
    public TableView dishTable;
    public TableColumn dishName;
    public TableColumn dishPrice;
    public Button addDishButton;
    public Button viewDishInfoButton;
    public Button removeDishButton;

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
                selectedDish = (Dish) dishTable.getSelectionModel().getSelectedItem();
                if (selectedDish != null) {
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
                selectedDish = (Dish) dishTable.getSelectionModel().getSelectedItem();
                if (selectedDish != null) {
                    if (db.deleteDish(selectedDish)) {
                        dishList.remove(selectedDish);
                        dishTable.setItems(dishList);
                        PopupDialog.confirmationDialog("Result", "Dish \"" + selectedDish.getDishName() + "\" removed.");
                    } else {
                        PopupDialog.errorDialog("Error", "Dish could not be removed.");
                    }

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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subMenuGP.requestFocus();
            }
        });

        dishName.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        dishPrice.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price"));

        dishName.setCellFactory(column -> {
            return new TableCell<Dish, String>() {
                @Override
                protected void updateItem(String name, boolean empty) {
                    if(name == null || empty) {
                        setText(null);
                    } else {
                        setText(name);
                    }
                }
            };
        });

        dishPrice.setCellFactory(column -> {
            return new TableCell<Dish, Double>() {
                @Override
                protected void updateItem(Double price, boolean empty) {
                    if(price == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(price));
                    }
                }
            };
        });

        dishTable.getColumns().setAll(dishName, dishPrice);
        dishTable.setItems(dishList);
        addDishButton.setOnAction(addDishButtonClick);
        removeDishButton.setOnAction(removeDishButtonClick);
        viewDishInfoButton.setOnAction(viewInfoButtonClick);


    }
}