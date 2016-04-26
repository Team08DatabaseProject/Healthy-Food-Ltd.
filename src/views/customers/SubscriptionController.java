package views.customers;

import classpackage.Order;
import classpackage.OrderLine;
import classpackage.OrderStatus;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/25/16.
 */
public class SubscriptionController extends CustomersController implements Initializable {

    @FXML
    public GridPane subWindowGP;
    public DatePicker subscriptionStart;
    public DatePicker subscriptionEnd;
    public TableView subscriptionOrderTable;
    public TableColumn orderIDCol;
    public TableColumn deadlineCol;
    public TableColumn priceCol;
    public TableColumn dishQuantityCol;
    public TableColumn statusCol;
    public Button addNewOrderButton;
    public Button commitButton;

    private ObservableList<Order> subscriptionOrders = FXCollections.observableArrayList();

    protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy \n HH:mm");

    EventHandler<ActionEvent> addNewOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedOrder = null;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("SubOrder.fxml"));
                GridPane SubOrderGP = loader.load();
                Scene formScene = new Scene(SubOrderGP);
                Stage formStage = new Stage();
                formStage.setTitle("Add order to subscription");
                formStage.setScene(formScene);
                formStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    public String ldtToString(LocalDateTime ldt) {
        return "Date: " + ldt.getYear() + "/" + String.format("%02d", ldt.getMonthValue()) + "/" + String.format("%02d", ldt.getDayOfMonth()) + "\nTime: "
                + String.format("%02d", ldt.getHour()) + ":" + String.format("%02d", ldt.getMinute());
    }

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subWindowGP.requestFocus();
            }
        });

        if (selectedSubscription != null) {
            subscriptionOrders = selectedSubscription.getOrdersOnThisSubscription();
            subscriptionStart.setDisable(true);
            subscriptionStart.setValue(selectedSubscription.getStartSubscription());
            subscriptionEnd.setValue(selectedSubscription.getEndSubscription());
        }

        subscriptionOrderTable.setEditable(true);

        orderIDCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("deadlineTime"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        dishQuantityCol.setCellValueFactory(new PropertyValueFactory<Order, ObservableList<OrderLine>>("dishesInThisOrder"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("status"));

        deadlineCol.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                public void updateItem(LocalDateTime ldt, boolean empty) {
                    if (ldt == null || empty) {
                        setText(null);
                    } else {
                        setText(ldt.format(formatter));
                    }
                }
            };
        });

        dishQuantityCol.setCellFactory(column -> {
            return new TableCell<Order, ObservableList<OrderLine>>() {
                @Override
                public void updateItem(ObservableList<OrderLine> orderLines, boolean empty) {
                    if (orderLines == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(orderLines.size()));
                    }
                }
            };
        });

        statusCol.setCellFactory(column -> {
            return new TableCell<Order, OrderStatus>() {
                @Override
                public void updateItem(OrderStatus status, boolean empty) {
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        setText(status.getName());
                    }
                }
            };
        });

        subscriptionOrderTable.getColumns().setAll(orderIDCol, deadlineCol, priceCol, dishQuantityCol, statusCol);
        subscriptionOrderTable.setItems(subscriptionOrders);

        addNewOrderButton.setOnAction(addNewOrderEvent);
    }
}
