package users.chef;

import classpackage.Dish;
import classpackage.DishLine;
import classpackage.Ingredient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/22/16.
 */
public class ControllerChefDishInfo extends ControllerChef implements Initializable {

    @FXML
    public GridPane dishInfoGP;
    public Label dishInfoHeader;
    public Label dishIdLabel;
    public Label dishPriceLabel;
    public TableView ingInDishTable;
    public TableColumn ingNameCol;
    public TableColumn ingAmountCol;
    public TableColumn ingPriceCol;


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ObservableList<DishLine> ingredientsInDish = selectedDish.getAllDishLinesForThisDish();


        dishInfoHeader.setText(selectedDish.getDishName());
        dishIdLabel.setText(String.valueOf(selectedDish.getDishId()));
        dishPriceLabel.setText(selectedDish.getPrice() + " NOK");

        ingInDishTable.setEditable(true);
        ingNameCol.setCellValueFactory(new PropertyValueFactory<DishLine, Ingredient>("ingredient"));
        ingAmountCol.setCellValueFactory(new PropertyValueFactory<DishLine, DishLine>("abc"));
        ingPriceCol.setCellValueFactory(new PropertyValueFactory<DishLine, Double>("total"));

        ingNameCol.setCellFactory(col -> {
            return new TableCell<DishLine, Ingredient>() {
                @Override
                public void updateItem(Ingredient ing, boolean empty) {
                    if (ing == null || empty) {
                        setText(null);
                    } else {
                        setText(ing.getIngredientName());
                    }
                }
            };
        });

        ingAmountCol.setCellFactory(col -> {
            return new TableCell<DishLine, DishLine>() {
                @Override
                public void updateItem(DishLine dishLine, boolean empty) {
                    if (dishLine == null || empty) {
                        setText(null);
                    } else {
                        setText(dishLine.getUnitAndAmount());
                    }
                }
            };
        });

      /*  ingPriceCol.setCellFactory(col -> {
            return new TableCell<DishLine, Double>() {
                @Override
                public void updateItem(Double total, boolean empty) {
                    if (total == null || empty) {
                        setText(null);
                    } else {
                        setText();
                    }
                }
            };
        });*/

        ingInDishTable.getColumns().setAll(ingNameCol, ingAmountCol, ingPriceCol);
        ingInDishTable.setItems(ingredientsInDish);
    }
}
