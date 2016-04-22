package users.chef;

import classpackage.Order;
import classpackage.OrderLine;
import classpackage.OrderStatus;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
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
    public Button applyChangesButton;
    public Button refreshOrdersButton;

    EventHandler<MouseEvent> viewOrderInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                selectedOrder = (Order) chefOrdersTable.getSelectionModel().getSelectedItem();
                try {
                    if (selectedOrder != null) {
                        final Stage addMenuStage = new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("ChefOrderInfo.fxml"));
                        addMenuStage.setTitle("Order " + selectedOrder.getOrderId() + " information");
                        addMenuStage.setScene(new Scene(root, 800, 800));
                        addMenuStage.show();
                    } else {
                        System.out.println("blurb");
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        chefOrdersTable.setEditable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("deadline"));
        dishQuantityCol.setCellValueFactory(new PropertyValueFactory<Order, ObservableList<OrderLine>>("dishesInThisOrder"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("status"));

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
            return new TableCell<Order, ObservableList<OrderLine>>() {
                @Override
                public void updateItem(ObservableList<OrderLine> orderLine, boolean empty) {
                    if (orderLine == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(orderLine.size()));
                    }
                }
            };
        });


        chefOrdersTable.getColumns().setAll(orderIdCol, deadlineCol, dishQuantityCol, priceCol, statusCol);
        chefOrdersTable.setItems(orderList);
        chefOrdersTable.setOnMousePressed(viewOrderInfoEvent);

    }
}
