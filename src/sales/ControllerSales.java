package sales;

import classpackage.*;
import classpackage.SqlQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by trymrt 28.03.2016
 * Controller for the Sales personnel
 */

public class ControllerSales implements Initializable {

    public Button createOrderButton; //Button for creating an order

    public TextField fNameField;
    public TextField lNameField;
    public ComboBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField customerRequestsField;
    public DatePicker deadlinePicker;
    public TextField priceField;
    public TextField statusField;

    private ObservableList<Order> allOrdersForSales = FXCollections.observableArrayList();
    private ObservableList<Subscription> allSubscribtions = FXCollections.observableArrayList();

    @FXML
    public BorderPane rootPaneSales; //RootPane
    public TableView tables; // Retrieves TableView with fx:id="tables2"
    public Button ordersButton; //Button for showing orders
    //public Button deleteOrderButton; //Button for deleting an order

    private SqlQueries query = new SqlQueries();
    final ObservableList<Order> orderTest = query.getOrders(4);

    // Shows a list of orders and their status.
    EventHandler<ActionEvent> orderEvent = new EventHandler<ActionEvent>() {

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

            } catch(Exception exc) {
                System.out.println("orderEvent: " + exc);
            }
        }
    };

    //fungerer når jeg kommenterer ut denne!
    EventHandler<ActionEvent> createOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                boolean isBusiness = businessBox.isArmed();
                String businessName = businessNameField.getText();
                String email = emailField.getText();
                String phoneNumber = phoneField.getText();
                String address = addressField.getText();
                String customerRequests = customerRequestsField.getText();
                Date

                /*FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("EditOrdersBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneSales.setBottom(readyOrderBottom);*/

            } catch(Exception exc) {
                System.out.println("createOrderEvent: " + exc);
            }
        }
    };

    public ObservableList<Order> getAllOrdersForSales() {
        return allOrdersForSales;
    }

    public void setAllOrdersForSales(ObservableList<Order> allOrdersForSales) {
        this.allOrdersForSales = allOrdersForSales;
    }

    public ObservableList<Subscription> getAllSubscribtions() {
        return allSubscribtions;
    }

    public void setAllSubscribtions(ObservableList<Subscription> allSubscribtions) {
        this.allSubscribtions = allSubscribtions;
    }

    // Shows list of orders with the option to change their status from "Not delivered" to "Delivered".
    /*EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
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
    };*/

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // Required method for Initializable, runs at program launch

        ordersButton.setOnAction(orderEvent);
        createOrderButton.setOnAction(createOrderEvent);
        //deleteOrderButton.setOnAction(deleteOrderEvent);

    }
}
