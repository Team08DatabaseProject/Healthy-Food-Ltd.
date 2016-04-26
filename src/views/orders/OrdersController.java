package views.orders;

import classpackage.*;
import main.MainController;
import main.PopupDialog;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
public class OrdersController extends MainController implements Initializable {

    @FXML
    public GridPane subMenuGP;
    public TableView chefOrdersTable;
    public TableColumn orderIdCol;
    public TableColumn deadlineCol;
    public TableColumn dishQuantityCol;
    public TableColumn priceCol;
    public TableColumn statusCol;
    public Button applyChangesButton;
    public Button refreshOrdersButton;

    protected static Dish selectedDish;
    protected static Order selectedOrder;

    protected static ObservableList<Supplier> supplierList = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredientList = db.getAllIngredients(supplierList);
    protected static ObservableList<Dish> dishList = db.getAllDishes(ingredientList);
    protected static ObservableList<Order> orderList = db.getOrders(2, dishList);

    protected static ObservableList<OrderStatus> statusTypes = db.getStatusTypes();

    EventHandler<MouseEvent> viewOrderInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                selectedOrder = (Order) chefOrdersTable.getSelectionModel().getSelectedItem();
                try {
                    if (selectedOrder != null) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("OrderInfo.fxml"));
                        GridPane orderPane = loader.load();
                        Scene orderScene = new Scene(orderPane, 800, 800);
                        Stage formStage = new Stage();
                        formStage.setTitle("Order " + selectedOrder.getOrderId() + " information");
                        formStage.setScene(orderScene);
                        formStage.show();
                    } else {
                        System.out.println("blurb");
                    }
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };

    EventHandler<ActionEvent> applyChangesEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                ObservableList<Order> orderUpdateList = FXCollections.observableArrayList();
                String updateString = "";
                for (Order order : orderList) {
                    if (order.isChanged()) {
                        orderUpdateList.add(order);
                        updateString += "\n" + order.getOrderId();
                        order.setChanged(false);
                    }
                }
                if (orderUpdateList.isEmpty()) {
                    PopupDialog.errorDialog("Update failure", "Changes were not applied, as no changes were detected in orders.");
                } else if (1==1) {  // TODO: 4/24/16 update with SqlQueries method
                    PopupDialog.confirmationDialog("Result", "Order(s):" + updateString + "\nupdated.");
                } else {
                    PopupDialog.errorDialog("Error", "Something went wrong.\nOrder:" + updateString + "\nfailed to update.");
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

        statusCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Order, OrderStatus>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Order, OrderStatus> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);
                    }
                }
        );

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
        applyChangesButton.setOnAction(applyChangesEvent);

    }
}
