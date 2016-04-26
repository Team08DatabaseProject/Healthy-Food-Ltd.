package views.orders;

import classpackage.Dish;
import classpackage.OrderLine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/22/16.
 */
public class OrderInfoController extends OrdersController implements Initializable {

    @FXML
    public GridPane orderInfoGP;
    public Label leftHeader;
    public Label deadlineLabel;
    public Label priceLabel;
    public Label addressLabel;
    public Label requestLabel;
    public TableView dishesInOrderTable;
    public TableColumn dishNameCol;
    public TableColumn dishAmountCol;
    public TableColumn dishPriceCol;

    EventHandler<MouseEvent> viewDishInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                OrderLine ol = (OrderLine) dishesInOrderTable.getSelectionModel().getSelectedItem();
                selectedDish = ol.getDish();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("DishInfo.fxml"));
                    GridPane dishPane = loader.load();
                    Scene dishScene = new Scene(dishPane, 500, 500);
                    Stage formStage = new Stage();
                    formStage.setTitle(selectedDish.getDishName() + " - Dish information");
                    formStage.setScene(dishScene);
                    formStage.show();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    System.out.println(exc);
                }
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        leftHeader.setText("Order " + selectedOrder.getOrderId());
        deadlineLabel.setText(selectedOrder.getDeadlineTime().toString());
        priceLabel.setText(selectedOrder.getPrice() + " NOK");
        addressLabel.setText(selectedOrder.getAddress().getAddress());
        requestLabel.setText("\"" + selectedOrder.getCustomerRequests() + "\"");
        ObservableList<OrderLine> dishesInOrder = selectedOrder.getDishesInThisOrder();

        dishNameCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish"));
        dishAmountCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("amount"));
        dishPriceCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Double>("total"));

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

       /* dishPriceCol.setCellFactory(col -> {
            return new TableCell<OrderLine, Double>() {
                @Override
                public void updateItem(Dish dish, boolean empty) {
                    if (dish == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(dish.getPrice()));
                    }
                }
            };
        });*/

        dishesInOrderTable.setEditable(true);
        dishesInOrderTable.setItems(dishesInOrder);
        dishesInOrderTable.setOnMousePressed(viewDishInfoEvent);

    }

}
