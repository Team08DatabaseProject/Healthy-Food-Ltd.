package chef;

/**
 * Created by Trym Todalshaug on 26/03/2016.
 */
import javafx.collections.FXCollections;
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

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

abstract public class ControllerChef implements Initializable{

    @FXML
    public TableView table;
    public Button readyOrderButton;
    public Button changeStatusButton;
    public Button deliveredButton;
    public Button notDeliveredButton;
    public BorderPane rootPaneDriver;
    public GridPane changeStatusTable;

    // Test data for orders which are ready for delivery
    final ObservableList<ChefOrderReady> readyOrderData = FXCollections.observableArrayList(
            new ChefOrderReady(1, "13:00"),
            new ChefOrderReady(2, "14:00"),
            new ChefOrderReady(3, "15:00"),
            new ChefOrderReady(4, "16:00")
    );

    // Same test data, only for the part of the menu where the Chef can change the status of the order
    final ObservableList<ChefOrderStatus> changeStatusData = FXCollections.observableArrayList(
            new ChefOrderStatus(1, "13:00", "Not ready"),
            new ChefOrderStatus(2, "14:00", "Not ready"),
            new ChefOrderStatus(3, "15:00", "ready"),
            new ChefOrderStatus(4, "16:00", "Not ready")
    );

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
                columns.get(0).setCellValueFactory(new PropertyValueFactory<ChefOrderReady,Integer>("orderNo"));
                //columns.get(1).setCellValueFactory(new PropertyValueFactory<ChefOrderReady,String>("address"));
                columns.get(1).setCellValueFactory(new PropertyValueFactory<ChefOrderReady,String>("deadline"));
                readyOrderTable.setItems(readyOrderData);

                FXMLLoader bottomLoader = new FXMLLoader();
                bottomLoader.setLocation(getClass().getResource("ReadyOrderBottom.fxml"));
                HBox readyOrderBottom = bottomLoader.load();
                rootPaneDriver.setBottom(readyOrderBottom);

            } catch(Exception exc) {
                System.out.println("readyOrderEvent: " + exc);
            }
        }
    };
}
