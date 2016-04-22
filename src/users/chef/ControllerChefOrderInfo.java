package users.chef;

import classpackage.Dish;
import classpackage.OrderLine;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/22/16.
 */
public class ControllerChefOrderInfo extends ControllerChef implements Initializable {

    @FXML
    public GridPane orderInfoGP;
    public Label orderIdLabel;
    public Label deadlineLabel;
    public Label priceLabel;
    public Label addressLabel;
    public Label requestLabel;
    public TableView dishesInOrderTable;
    public TableColumn dishNameCol;
    public TableColumn dishAmountCol;
    public TableColumn dishPriceCol;
    public TableColumn dishInfoCol;

    private final NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        ObservableList<OrderLine> dishesInOrder = selectedOrder.getDishesInThisOrder();

        dishNameCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish"));
        dishAmountCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("amount"));
        dishPriceCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish"));
        dishInfoCol.setCellValueFactory(new PropertyValueFactory<OrderLine, OrderLine>("info"));

        dishNameCol.setCellFactory(col -> {
            return new TableCell<OrderLine, Dish>() {
                @Override
                public void updateItem(Dish dish, boolean empty) {
                    super.updateItem(dish, empty);
                    if (dish == null || empty) {
                        setText(null);
                    } else {
                        setText(dish.getDishName());
                    }
                }
            };
        });

        dishAmountCol.setCellFactory(col -> {
            return new TableCell<OrderLine, Integer>() {
                @Override
                public void updateItem(Integer amount, boolean empty) {
                    if (amount == null || empty) {
                        setText(null);
                    } else {
                        setText(amount.toString());
                    }
                }
            };
        });

        dishPriceCol.setCellFactory(col -> {
            return new TableCell<OrderLine, Dish>() {
                @Override
                public void updateItem(Dish dish, boolean empty) {
                    if (dish == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(dish.getPrice()));
                    }
                }
            };
        });

        dishInfoCol.setCellFactory(col -> {
            final Button dishInfoButton = new Button("More info");
            TableCell<OrderLine, OrderLine> cell = new TableCell<OrderLine, OrderLine>() {
                @Override
                public void updateItem(OrderLine orderLine, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(dishInfoButton);
                    }
                }
            };
            selectedDish = cell.getItem().getDish();
            return cell;
        });

        dishesInOrderTable.setEditable(true);
        dishesInOrderTable.getColumns().setAll(dishNameCol, dishAmountCol, dishPriceCol, dishInfoCol);
        dishesInOrderTable.setItems(dishesInOrder);

    }

}
