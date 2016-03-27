package Driver;

/**
 * Created by axelkvistad on 3/24/16.
 * ControllerDriverChangeStatus.java
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDriverCS implements Initializable {

    public GridPane centerGrid;
    public TableView tables;
    public Button deliveredButton;
    DriverOrderStatus d1 = new DriverOrderStatus(1, "Testveien 1", "13:00", "Not delivered");
    DriverOrderStatus d2 = new DriverOrderStatus(2, "Testveien 2", "14:00", "Not delivered");
    DriverOrderStatus d3 = new DriverOrderStatus(3, "Testveien 3", "15:00", "Not delivered");
    DriverOrderStatus d4 = new DriverOrderStatus(4, "Testveien 4", "16:00", "Not delivered");

    final ObservableList<DriverOrderStatus> changeStatusData = FXCollections.observableArrayList(
            d1, d2, d3, d4
    );


    EventHandler<ActionEvent> markAsDelivered = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                ((DriverOrderStatus) tables.getSelectionModel().getSelectedItem()).setStatus("Delivered");
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        ObservableList<TableColumn> columns = tables.getColumns();
        columns.get(0).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,Integer>("orderNo"));
        columns.get(1).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("address"));
        columns.get(2).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("deadline"));
        columns.get(3).setCellValueFactory(new PropertyValueFactory<DriverOrderStatus,String>("status"));
        tables.setItems(changeStatusData);
        deliveredButton.setOnAction(markAsDelivered);
    }
}
