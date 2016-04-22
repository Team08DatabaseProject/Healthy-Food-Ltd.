package users.chef;

import classpackage.Order;
import classpackage.OrderLine;
import classpackage.OrderStatus;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/21/16.
 */
public class ControllerChefOrders extends ControllerChef implements Initializable {

    @FXML
    public GridPane chefOrdersGP;
    public TableView chefOrdersTable;
    public TableColumn orderIdCol;
    public TableColumn deadlineCol;
    public TableColumn dishQuantityCol;
    public TableColumn priceCol;
    public TableColumn statusCol;
    public TableColumn infoCol;
    public Button applyChangesButton;
    public Button refreshOrdersButton;


    EventHandler<ActionEvent> viewOrderInfoEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (selectedOrder != null) {
                    final Stage addMenuStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("ChefOrderInfo.fxml"));
                    addMenuStage.setTitle("Add new menu");
                    addMenuStage.setScene(new Scene(root, 800, 800));
                    addMenuStage.show();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };



    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        chefOrdersTable.setEditable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("deadline"));
        dishQuantityCol.setCellValueFactory(new PropertyValueFactory<Order, OrderLine>("dishesInThisOrder"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("status"));
        infoCol.setCellValueFactory(new PropertyValueFactory<Order, Order>("info"));

        statusCol.setCellFactory(ComboBoxTableCell.forTableColumn(new StringConverter<OrderStatus>() {
            @Override
            public String toString(OrderStatus status) {
                return status.getName();
            }

            public OrderStatus fromString(String string) {
                return null;
            }
        }, statusTypes));

        dishQuantityCol.setCellFactory(col -> {
            return new TableCell<Order, OrderLine>() {
                @Override
                public void updateItem(OrderLine orderLine, boolean empty) {
                    if (orderLine == null || empty) {
                        setText(null);
                    } else {
                        System.out.println(orderLine.getAmount());
                        setText(String.valueOf(orderLine.getAmount()));
                    }
                }
            };
        });


        infoCol.setCellFactory(col -> {
            Button infoButton = new Button("View info");
            TableCell<Order, Order> cell = new TableCell<Order, Order>() {
                @Override
                public void updateItem(Order order, boolean empty) {
                    super.updateItem(order, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(infoButton);
                    }
                }
            };
            selectedOrder = cell.getItem();
            return cell;
        });

        chefOrdersTable.getColumns().setAll(orderIdCol, deadlineCol, dishQuantityCol, priceCol, statusCol, infoCol);
        chefOrdersTable.setItems(orderList);

    }
}
