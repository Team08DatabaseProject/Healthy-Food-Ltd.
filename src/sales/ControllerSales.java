package sales;

import classpackage.Order;
import classpackage.SqlQueries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by trymrt 28.03.2016
 * Controller for the Sales personnel
 */

public class ControllerSales implements Initializable {

    private SqlQueries query = new SqlQueries();


    @FXML
    public BorderPane rootPaneSales; //RootPane
    public GridPane gridPaneOrders;
    public TableView tables; // Retrieves TableView with fx:id="tables"
    public Button ordersButton;
    public Button deleteOrderButton;
    public Button createOrderButton;

    final ObservableList<Order> orderTest = query.getOrders();

    // Shows a list of orders and their status.
    EventHandler<ActionEvent> OrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("OrdersTable.fxml"));
                tables = loader.load();
                rootPaneSales.setCenter(tables);
                ObservableList<TableColumn> columns = tables.getColumns();
                columns.get(0).setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<Order,Integer>("customerId"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Order,Integer>("subscriptionId"));
                columns.get(3).setCellValueFactory(new PropertyValueFactory<Order,String>("customerRequests"));
                columns.get(4).setCellValueFactory(new PropertyValueFactory<Order,Date>("deadline"));
                columns.get(5).setCellValueFactory(new PropertyValueFactory<Order,Double>("price"));
                columns.get(6).setCellValueFactory(new PropertyValueFactory<Order,String>("address"));
                columns.get(7).setCellValueFactory(new PropertyValueFactory<Order,String>("status"));
                tables.setItems(orderTest);

                /*FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("ReadyOrderBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneDriver.setBottom(readyOrderBottom);*/

            } catch(Exception exc) {
                System.out.println("OrderEvent: " + exc);
            }
        }
    };

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                ordersTable = loader.load();
                gridPaneOrders.setCenter(ordersTable);

            } catch(Exception exc) {
                System.out.println("deleteOrderEvent: " + exc);
            }
        }
    };

    // Shows list of orders with the option to change their status from "Not delivered" to "Delivered".
    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                ordersTable = loader.load();
                gridPaneOrders.setCenter(ordersTable);

            } catch(Exception exc) {
                System.out.println("deleteOrderEvent: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // Required method for Initializable, runs at program launch

        ordersButton.setOnAction(OrderEvent);
        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);

    }

    /*
    EventHandler<ActionEvent> markAsDelivered = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                changeStatusTable.getSelectionModel().setCellSelectionEnabled(true);
                DriverOrderStatus orderStatus = (DriverOrderStatus) changeStatusTable.getSelectionModel().getSelectedItem();
                orderStatus.setStatus("Delivered");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };
    */


}
