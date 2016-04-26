package users.sales.customers;

import classpackage.Order;
import classpackage.OrderLine;
import classpackage.OrderStatus;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import users.sales.ControllerSales;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/25/16.
 */
public class ControllerSalesEditCustomerInfo extends ControllerSales implements Initializable {

    @FXML
    public GridPane subWindowGP;
    public TextField fNameField;
    public TextField lNameField;
    public CheckBox  businessBox;
    public TextField businessNameField;
    public TextField phoneField;
    public TextField emailField;
    public Label hasSubscriptionLabel;
    public TableView currentOrdersTable;
    public TableColumn orderIDCol;
    public TableColumn deadlineCol;
    public TableColumn priceCol;
    public TableColumn dishQuantityCol;
    public TableColumn statusCol;
    public TableColumn partOfSubscriptionCol;
    public Button subscriptionButton;
    public Button commitChangesButton;

    private ObservableList<Order> selectedCustomerOrders = FXCollections.observableArrayList();


    EventHandler<ActionEvent> subscriptionEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("Subscription.fxml"));
                GridPane subscriptionGP = loader.load();
                Scene formScene = new Scene(subscriptionGP);
                Stage formStage = new Stage();
                formStage.setTitle("Subscription");
                formStage.setScene(formScene);
                formStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subWindowGP.requestFocus();
            }
        });

        if (selectedCustomer != null) {
            selectedCustomerOrders = selectedCustomer.getOrders();

            fNameField.setText(selectedCustomer.getFirstName());
            lNameField.setText(selectedCustomer.getLastName());
            businessBox.setSelected(selectedCustomer.getIsBusiness());
            if (selectedCustomer.getIsBusiness()) {
                businessNameField.setText(selectedCustomer.getBusinessName());
            } else {
                businessNameField.setDisable(true);
            }
            phoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
            emailField.setText(selectedCustomer.getEmail());

            if (selectedCustomer.getSubscription() != null) {
                selectedSubscription = selectedCustomer.getSubscription();
                hasSubscriptionLabel.setText("Yes (ID: " + selectedCustomer.getSubscription().getSubscriptionId() + ")");
                subscriptionButton.setText("View/Edit subscription");
            } else {
                selectedSubscription = null;
            }
        }

        businessBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                businessNameField.setDisable(!newValue);
            }
        });

        currentOrdersTable.setEditable(true);

        orderIDCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("orderId"));
        deadlineCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("deadlineTime"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
        dishQuantityCol.setCellValueFactory(new PropertyValueFactory<Order, ObservableList<OrderLine>>("dishesInThisOrder"));
        statusCol.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("status"));
        partOfSubscriptionCol.setCellValueFactory(new PropertyValueFactory<Order, Boolean>("partOfSubscription"));



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

        partOfSubscriptionCol.setCellFactory(column -> {
            return new TableCell<Order, Boolean>() {
                @Override
                public void updateItem(Boolean partOfSub, boolean empty) {
                    if (partOfSub == null || empty) {
                        setText(null);
                    } else if (partOfSub) {
                        setText("Yes");
                    } else {
                        setText("No");
                    }
                }
            };
        });

        currentOrdersTable.getColumns().setAll(orderIDCol, deadlineCol, priceCol, dishQuantityCol);
        currentOrdersTable.setItems(selectedCustomerOrders);

        subscriptionButton.setOnAction(subscriptionEvent);


    }

}
