package driver;
/**
 * Created by Axel 16.03.2016
 * Controller for the driver
 */

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
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import classpackage.Order;

public class ControllerDriver implements Initializable {

    private SqlQueries query = new SqlQueries();

    @FXML public TableView tables; // Retrieves TableView with fx:id="tables2"
    public Button readyOrderButton;
    public Button changeStatusButton;
    public Button deliveredButton;
    public Button notDeliveredButton;
    public BorderPane rootPaneDriver;
    public GridPane changeStatusTable;

    /*
    // Test data for orders which are ready for delivery
    final ObservableList<DriverOrderDelivery> readyOrderData = FXCollections.observableArrayList(
            new DriverOrderDelivery(1, "Testveien 1", "13:00"),
            new DriverOrderDelivery(2, "Testveien 2", "14:00"),
            new DriverOrderDelivery(3, "Testveien 3", "15:00"),
            new DriverOrderDelivery(4, "Testveien 4", "16:00")
    );

     // Same test data, only for the part of the menu where the driver can change the status of the order
    final ObservableList<DriverOrderStatus> changeStatusData = FXCollections.observableArrayList(
            new DriverOrderStatus(1, "Testveien 1", "13:00", "Not delivered"),
            new DriverOrderStatus(2, "Testveien 2", "14:00", "Not delivered"),
            new DriverOrderStatus(3, "Testveien 3", "15:00", "Not delivered"),
            new DriverOrderStatus(4, "Testveien 4", "16:00", "Not delivered")
    );
*/

    final ObservableList<Order> orderTest = query.getOrders(1); // Fucked up, fix






    // Shows a list of orders ready for delivery and a button for generating the route (non-functional as of now)
    EventHandler<ActionEvent> readyOrderEvent  = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ReadyOrderTable.fxml"));
                TableView readyOrderTable = loader.load();
                rootPaneDriver.setCenter(readyOrderTable);
                ObservableList<TableColumn> columns = readyOrderTable.getColumns();
                columns.get(0).setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<Order,String>("address"));
                columns.get(2).setCellValueFactory(new PropertyValueFactory<Order,Date>("deadline"));
                readyOrderTable.setItems(orderTest);

                FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("ReadyOrderBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneDriver.setBottom(readyOrderBottom);

            } catch(Exception exc) {
                System.out.println("readyOrderEvent: " + exc);
            }
        }
    };

    // Shows list of orders with the option to change their status from "Not delivered" to "Delivered".
    EventHandler<ActionEvent> changeStatusEvent  = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("ChangeOrderStatusTable.fxml"));
                changeStatusTable = loader.load();
                rootPaneDriver.setCenter(changeStatusTable);

            } catch(Exception exc) {
                System.out.println("changeStatusEvent: " + exc);
            }
        }
    };

    // WORK IN PROGRESS: changes the status of selected orders from "Not delivered" to "Delivered".
    // Once this is figured out I'll make another one for doing the reverse.
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

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch

        //data.add(new TableTest("Fifty-three", "7")); // adds new line to table by adding another TableTest object to the data array

        //deliveredButton.setOnAction(markAsDelivered); <-- this line makes the program crash, should be initialized AFTER user clicks "Change order status"-button.
        // also tried to put it inside changeStatusEvent() but this unsurprisingly leads to NullPointerException
        readyOrderButton.setOnAction(readyOrderEvent);
        changeStatusButton.setOnAction(changeStatusEvent);

    }


}