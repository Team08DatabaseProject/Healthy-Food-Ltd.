package users.driver;
/**
 * Created by Axel 16.03.2016
 * Controller for the users.driver
 */

//import classpackage.SqlQueries;
import classpackage.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ControllerDriver implements Initializable {

    protected static SqlQueries db = new SqlQueries();

    @FXML
    public BorderPane rootPaneDriver;
    public TableView ordersReadyTable;
    public TableColumn statusColLeft;
    public TableColumn deadlineColLeft;
    public TableColumn dateDeliveredColLeft;
    public TableColumn addressColLeft;

    public Button addToRouteButton;
    public Button setToDeliveredButton;
    public Button setToReadyButton;

    public TableView routeOrdersTable;
    public TableColumn statusColRight;
    public TableColumn deadlineColRight;
    public TableColumn dateDeliveredColRight;
    public TableColumn addressColRight;

    public Button removeFromListButton;
    public Button removeAllButton;
    public Button generateRouteButton;
    public Button deliveryInProcessButton;

    Address firstAddress = new Address("Osloveien 54", 7018, "Trondheim");
    Address secondAddress = new Address("Weidemanns vei 5A", 7014, "Trondheim");
    Address thirdAddress = new Address("Klæbuveien 134A", 7031, "Trondheim");

    protected static ObservableList<OrderStatus> statusTypes = db.getStatusTypes();
    protected static ObservableList<Supplier> supplierList = db.getAllSuppliers();
    protected static ObservableList<Ingredient> ingredientList = db.getAllIngredients(supplierList);
    protected static ObservableList<Dish> dishList = db.getAllDishes(ingredientList);
    protected static ObservableList<Order> orderList = db.getOrders(3, dishList);



  //  protected static ObservableList<Order> orderList = testObjects.orderList;
    protected static ObservableList<Order> routeOrderList = FXCollections.observableArrayList();

    /* "https://www.google.com/maps/dir/" + firstAddress.getAddress() + ",+" + firstAddress.getZipCode() + "+" + firstAddress.getPlace() + ",+Norway/";*/
    public static String generateUrl(ObservableList<Order> ordersToGenerateRoute){
        String url = "https://www.google.com/maps/dir/";
        Collections.sort(ordersToGenerateRoute, (o1, o2) -> o1.getDeadline().compareTo(o2.getDeadline()));
        for (Order order :
                ordersToGenerateRoute) {
            Address address = order.getAddress();
            url += address.getAddress() + ",+" + address.getZipCode() + "+" + address.getPlace() + ",+Norway/";
        }
        return url;
    }

    public static void addMap(String url, Pane pane, Stage stage){
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(browser);

        webEngine.getLoadWorker().stateProperty()
                .addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

                        if (newState == Worker.State.SUCCEEDED) {
                           // stage.setTitle(webEngine.getLocation());
                        }

                    }
                });
        webEngine.load(url);
        pane.getChildren().add(scrollPane);
        stage.show();
    }

    EventHandler<ActionEvent> deliveryInProcessEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!routeOrderList.isEmpty()) {
                    for (Order order : routeOrderList) {
                        order.setStatus(statusTypes.get(3));
                    }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> addToDeliveryRouteEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Order item = (Order) ordersReadyTable.getSelectionModel().getSelectedItem();
            if (item != null) {
                boolean exists = false;
                for (Order order :
                        routeOrderList) {
                    if (order == item) {
                        exists = true;
                    }
                }
                if (!exists) {
                    routeOrderList.add(item);
                }
            }
        }
    };

    EventHandler<ActionEvent> removeFromListEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Order item = (Order) routeOrdersTable.getSelectionModel().getSelectedItem();
            if (item != null) {
                routeOrdersTable.getSelectionModel().clearSelection();
                routeOrderList.remove(item);
            }
        }
    };

    EventHandler<ActionEvent> removeAllEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (!routeOrderList.isEmpty()) {
                    routeOrderList.clear();
                    routeOrdersTable.setItems(routeOrderList);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> markAsDelivered = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                Order order = (Order) ordersReadyTable.getSelectionModel().getSelectedItem();
                if (order != null) {
                    LocalDateTime deliveryDate = LocalDateTime.now();
                    order.setActualDeliveryDateTime(deliveryDate);
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> generateRouteEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            Pane paneForMap = new Pane();
            String url = generateUrl(routeOrderList);
            Scene mapScene = new Scene(paneForMap);
            Stage mapStage = new Stage();
            mapStage.setScene(mapScene);
            mapStage.setTitle("Delivery route");
            mapStage.setHeight(600);
            mapStage.setWidth(900);
           // Stage stage = (Stage) rootPaneDriver.getScene().getWindow();
            addMap(url, paneForMap, mapStage);
        }
    };

    public String ldtToString(LocalDateTime ldt) {
        return "Date: " + ldt.getYear() + "/" + ldt.getMonthValue() + "/" + ldt.getDayOfMonth() + "\nTime: "
                + String.format("%02d", ldt.getHour()) + ":" + String.format("%02d", ldt.getMinute());
    }


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        deadlineColLeft.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("deadlineTime"));
        dateDeliveredColLeft.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("actualDeliveryDateTime"));
        statusColLeft.setCellValueFactory(new PropertyValueFactory<Order, OrderStatus>("status"));
        addressColLeft.setCellValueFactory(new PropertyValueFactory<Order, Address>("address"));


        deadlineColLeft.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime ldt, boolean empty) {
                    if (ldt == null || empty) {
                        setText(null);
                    } else {
                        setText(ldtToString(ldt));
                    }
                }
            };
        });

        dateDeliveredColLeft.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime ldt, boolean empty) {
                    if (ldt == null && !empty) {
                        setText("Not yet delivered");
                    } else if (empty) {
                        setText(null);
                    } else {
                        setText(ldtToString(ldt));
                    }
                }
            };
        });


        statusColLeft.setCellFactory(column -> {
            return new TableCell<Order, OrderStatus>() {
                @Override
                protected void updateItem(OrderStatus status, boolean empty) {
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        setText(status.getName());
                    }
                }
            };
        });

        addressColLeft.setCellFactory(column -> {
            return new TableCell<Order, Address>() {
                @Override
                protected void updateItem(Address item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getAddress() + "\n" + item.getZipCode() + " " + item.getPlace());
                    }
                }
            };
        });




        deadlineColRight.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("deadlineTime"));

        dateDeliveredColRight.setCellValueFactory(
                new PropertyValueFactory<Order, LocalDateTime>("actualDeliveryDateTime")
        );

        statusColRight.setCellValueFactory(
                new PropertyValueFactory<Order, OrderStatus>("status")
        );

        addressColRight.setCellValueFactory(new PropertyValueFactory<Order, Address>("address"));

        deadlineColRight.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime ldt, boolean empty) {
                    if (ldt == null || empty) {
                        setText(null);
                    } else {
                        setText(ldtToString(ldt));
                    }
                }
            };
        });

        dateDeliveredColRight.setCellFactory(column -> {
            return new TableCell<Order, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime ldt, boolean empty) {
                    if (ldt == null && !empty) {
                        setText("Not yet delivered");
                    } else if (empty) {
                        setText(null);
                    } else {
                        setText(ldtToString(ldt));
                    }
                }
            };
        });

        statusColRight.setCellFactory(column -> {
            return new TableCell<Order, OrderStatus>() {
                @Override
                protected void updateItem(OrderStatus status, boolean empty) {
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        setText(status.getName());
                    }
                }
            };
        });

        addressColRight.setCellFactory(column -> {
            return new TableCell<Order, Address>() {
                @Override
                protected void updateItem(Address item, boolean empty) {
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getAddress() + "\n" + item.getZipCode() + " " + item.getPlace());
                    }
                }
            };
        });

        ordersReadyTable.setEditable(true);
        ordersReadyTable.setItems(orderList);
        
        routeOrdersTable.setEditable(true);
        routeOrdersTable.setItems(routeOrderList);

        addToRouteButton.setOnAction(addToDeliveryRouteEvent);
        removeFromListButton.setOnAction(removeFromListEvent);
        generateRouteButton.setOnAction(generateRouteEvent);
        deliveryInProcessButton.setOnAction(deliveryInProcessEvent);
        removeAllButton.setOnAction(removeAllEvent);
        setToDeliveredButton.setOnAction(markAsDelivered);

    }
}
