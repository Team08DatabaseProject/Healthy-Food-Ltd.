package users.sales;

import classpackage.Customer;
import classpackage.Order;
import classpackage.TestObjects;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 04/04/2016.
 */
public class ControllerSalesOrders extends ControllerSales implements Initializable{

    @FXML
    public Button createOrderButton; //Button for creating an order
    public Button deleteOrderButton; //Button for deleting an order
    public TableView ordersTable;
    public BorderPane rootPaneOrders;

    protected static TestObjects testObjects = new TestObjects();
    protected static ObservableList<Order> orders = testObjects.allOrders;
    protected static ObservableList<Customer> customerList = testObjects.allCustomers;

    public GridPane salesTextField;
    protected static Order selectedOrder;
    protected static Customer selectedCustomer;

    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SalesTextField.fxml"));
                salesTextField = loader.load();
                rootPaneOrders.setCenter(salesTextField);
                rootPaneOrders.setBottom(null);
                rootPaneOrders.setRight(null);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                int selectedIndex = ordersTable.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0){
                    ordersTable.getItems().remove(selectedIndex);
                }else{
                    //Nothing selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No order selected");
                    alert.setContentText("Select an order to delete it");

                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("deleteOrderEvent" + e);
            }
        }
    };

    EventHandler<MouseEvent> infoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                selectedOrder = (Order)ordersTable.getSelectionModel().getSelectedItem();
                for (Customer customer : customerList) {
                    for (Order order : customer.getOrders()) {
                        if (order.getPrice() == selectedOrder.getPrice() && order.getAddress() == selectedOrder.getAddress()) {
                            selectedCustomer = customer;
                        }
                    }
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("infoTable.fxml"));
                    GridPane infoGrid = loader.load();
                    rootPaneOrders.setBottom(infoGrid);
                } catch (Exception e) {
                    System.out.println("infoEvent " + e);
                }
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        ordersTable.setEditable(true);
        ObservableList<TableColumn> columns = ordersTable.getColumns();
        TableColumn<Order,Integer> orderId = columns.get(0);
        TableColumn<Order,LocalDate> deadline = columns.get(1);
        TableColumn<Order,String> status = columns.get(2);
        TableColumn<Order,Double> price = columns.get(3);


        orderId.setCellValueFactory(new PropertyValueFactory<Order,Integer>("orderId")); //orderId
        deadline.setCellValueFactory(new PropertyValueFactory<Order,LocalDate>("deadline")); //deadline
        status.setCellValueFactory(new PropertyValueFactory<Order,String>("status")); //status
        price.setCellValueFactory(new PropertyValueFactory<Order,Double>("price")); //price

        ordersTable.getColumns().setAll(orderId, deadline, status, price);
        createOrderButton.setOnAction(createOrderEvent);
        deleteOrderButton.setOnAction(deleteOrderEvent);
        ordersTable.setOnMousePressed(infoEvent);
        ordersTable.setItems(orders);
    }
}
