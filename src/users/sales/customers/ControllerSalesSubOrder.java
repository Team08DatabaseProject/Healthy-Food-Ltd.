package users.sales.customers;

import classpackage.*;
import div.PopupDialog;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import users.sales.ControllerSales;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by axelkvistad on 4/25/16.
 */
public class ControllerSalesSubOrder extends ControllerSales implements Initializable {

    @FXML
    public GridPane subWindowGP;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField placeField;
    public Label dayOfWeekLabel;
    public ComboBox<Integer> dayOfWeekCB;
    public ComboBox<Integer> deadlineHrCB;
    public ComboBox<Integer> deadlineMinCB;
    public Button addDayAndTimeButton;
    public TextArea customerRequestsField;
    public Label priceFieldLabel;
    public TextField priceField;
    public TableView chosenDishTable;
    public TableColumn dishNameCol;
    public TableColumn quantityCol;
    public TableColumn priceCol;
    public ComboBox<Dish> chooseDishCB;
    public Button addDishButton;
    public Button removeDishButton;
    public Button addToSubscriptionButton;
    public VBox dayAndTimeVBox;

    private ObservableList<OrderLine> chosenOL = FXCollections.observableArrayList();
    private ObservableList<Order> finalSubOrders = FXCollections.observableArrayList();

    private ObservableList<Order> mondayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> tuesdayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> wednesdayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> thursdayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> fridayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> saturdayOrders = FXCollections.observableArrayList();
    private ObservableList<Order> sundayOrders = FXCollections.observableArrayList();

    private LocalDate startSub;
    private LocalDate endSub;

    private ObservableList<ObservableList<Order>> weekOrderList = FXCollections.<ObservableList<Order>>observableArrayList(
            mondayOrders, tuesdayOrders, wednesdayOrders, thursdayOrders, fridayOrders, saturdayOrders, sundayOrders
    );

    //Days of week
    final ObservableList<Integer> deadlineDayOfWeekList = FXCollections.observableArrayList(
            1, 2, 3, 4, 5, 6, 7
    );

    //Opening/delivery hours from 7 AM to 10 PM
    final ObservableList<Integer> deadlineHourList = FXCollections.observableArrayList(
            7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
    //Minute selection for every 15 minutes of every hour
    final ObservableList<Integer> deadlineMinuteList = FXCollections.observableArrayList(0, 15, 30, 45);




    EventHandler<ActionEvent> addDishToOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                if (selectedDish != null){
                    boolean add = true;
                    for (OrderLine ol : chosenOL) {
                        if (ol.getDish().getDishName().equals(selectedDish.getDishName())){
                            add = false;
                        }
                    }
                    if (add){
                        OrderLine newOL = new OrderLine(selectedDish);
                        chosenOL.add(newOL);
                        chosenDishTable.setItems(chosenOL);
                        double totalPrice = 0;
                        for (OrderLine ol : chosenOL){
                            totalPrice += ol.getTotal();
                        }
                        priceField.setText(nf.format(totalPrice));
                    }
                }
            }catch (Exception e){
                System.out.println("addDishToOrderEvent " + e);
            }
        }
    };

    EventHandler<ActionEvent> addToSubscriptionEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                finalSubOrders.clear();
                for (ObservableList<Order> day : weekOrderList) {
                    if (!day.isEmpty()) {
                        finalSubOrders.addAll(day);
                    }
                }
                if (!(selectedSubscription == null || finalSubOrders.isEmpty())) {
                    if (db.addOrders(selectedSubscription, finalSubOrders, selectedCustomer)) {
                        selectedSubscription.setOrdersOnThisSubscription(finalSubOrders);
                        PopupDialog.confirmationDialog("Result", "Orders added to subscription.");
                    } else {
                        PopupDialog.errorDialog("Error", "Orders could not be added to subscription.");
                    }
                } else if (finalSubOrders.isEmpty()) {
                    PopupDialog.errorDialog("Error", "No changes detected in orders.");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    EventHandler<ActionEvent> addDayAndTimeEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String address = addressField.getText();
                int zipCodeInt = Integer.parseInt(zipCodeField.getText());
                String place = placeField.getText();
                String customerRequests = customerRequestsField.getText();
                double price = Double.parseDouble(priceField.getText());
                Address newAddress = new Address(address, zipCodeInt, place);

                int dayOfWeek = dayOfWeekCB.getSelectionModel().getSelectedItem();
                System.out.println("Day of week: " + dayOfWeek);
                int hour = deadlineHrCB.getSelectionModel().getSelectedItem();
                System.out.println("Hour: " + hour);
                int minute = deadlineMinCB.getSelectionModel().getSelectedItem();
                System.out.println("Minute: " + minute);
                startSub = selectedSubscription.getStartSubscription();
                endSub = selectedSubscription.getEndSubscription();
                OrderStatus created = statusTypes.get(0);

                LocalDateTime orderLDT = startSub.atStartOfDay();
                int startDayOfWeek = orderLDT.getDayOfWeek().getValue();
                orderLDT = orderLDT.plusHours(hour);
                orderLDT = orderLDT.plusMinutes(minute);

                if (dayOfWeek > startDayOfWeek) {
                    orderLDT = orderLDT.plusDays(dayOfWeek - startDayOfWeek);
                } else if (dayOfWeek < startDayOfWeek) {
                    orderLDT = orderLDT.plusWeeks(1);
                }

                LocalDateTime lastSub = endSub.atStartOfDay().plusDays(1);
                while (orderLDT.isBefore(lastSub) || orderLDT.isEqual(lastSub)) {
                    Order subOrder = new Order(customerRequests, orderLDT, price, created, chosenOL, newAddress);
                    subOrder.setPartOfSubscription(true);
                    weekOrderList.get(dayOfWeek-1).add(subOrder);
                    orderLDT = orderLDT.plusWeeks(1);
                }
                String info = DayOfWeek.of(dayOfWeek).getDisplayName(TextStyle.FULL, Locale.ENGLISH) + ", " +
                String.format("%02d", hour) + ":" + String.format("%02d", minute);

                boolean added = false;
                for (Node node : dayAndTimeVBox.getChildren()) {
                    Label label = (Label) node;
                    if (!label.isVisible() && !added) {
                        label.setText(info);
                        label.setVisible(true);
                        added = true;
                    }
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        System.out.println(selectedSubscription.getEndSubscription().toString());
        System.out.println(selectedSubscription.getEndSubscription().atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subWindowGP.requestFocus();
            }
        });
        if (selectedSubscription.getOrdersOnThisSubscription() != null) {
            finalSubOrders = selectedSubscription.getOrdersOnThisSubscription();
        }

        deadlineHrCB.setItems(deadlineHourList);
        deadlineMinCB.setItems(deadlineMinuteList);
        chooseDishCB.setItems(dishes);

        if (selectedOrder != null) {
            addressField.setText(selectedOrder.getAddress().getAddress());
            zipCodeField.setText(String.valueOf(selectedOrder.getAddress().getZipCode()));
            placeField.setText(selectedOrder.getAddress().getPlace());
            if (!selectedOrder.getDishesInThisOrder().isEmpty()) {
                chosenOL = selectedOrder.getDishesInThisOrder();
            }
        }

        priceField.setDisable(true);

        dayOfWeekCB.setItems(deadlineDayOfWeekList);
        dayOfWeekCB.setCellFactory(column -> {
            return new ListCell<Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!(item == null || empty)) {
                        setText(DayOfWeek.of(item).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                    }
                }
            };
        });
        dayOfWeekCB.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return DayOfWeek.of(object).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });

        deadlineHrCB.setItems(deadlineHourList);
        deadlineHrCB.setCellFactory(column -> {
            return new ListCell<Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(String.format("%02d", item));
                    }
                }
            };
        });
        deadlineMinCB.setItems(deadlineMinuteList);
        deadlineMinCB.setCellFactory(column -> {
            return new ListCell<Integer>() {
                @Override
                protected  void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && !empty) {
                        setText(String.format("%02d", item));
                    }
                }
            };
        });

        // Disable addDayAndTimeButton unless day / hour / minute is set
        dayOfWeekCB.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                addDayAndTimeButton.setDisable(newValue == null);
            }
        });
        deadlineHrCB.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                addDayAndTimeButton.setDisable(newValue == null);
            }
        });
        deadlineMinCB.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                addDayAndTimeButton.setDisable(newValue == null);
            }
        });

        chooseDishCB.setItems(dishes);
        chooseDishCB.setCellFactory(column -> {
            return new ListCell<Dish>() {
                @Override
                public void updateItem(Dish dish, boolean empty) {
                    super.updateItem(dish, empty);
                    if (!(dish == null || empty)) {
                        setText(dish.getDishName());
                    }
                }
            };
        });
        chooseDishCB.valueProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
                selectedDish = newValue;
            }
        });

        chosenDishTable.setEditable(true);
        dishNameCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish")); //dishName
        quantityCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("amount")); //qty
        priceCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish")); //price


        quantityCol.setCellFactory(TextFieldTableCell.<OrderLine, Integer>forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<OrderLine, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<OrderLine, Integer> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                        double totalPrice = 0;
                        for (OrderLine ol : chosenOL){
                            totalPrice += ol.getTotal();
                        }
                        priceField.setText(nf.format(totalPrice));
                    }
                });


        priceCol.setCellFactory(column -> {
            return new TableCell<OrderLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if(dish == null || empty) {
                        setText(null);
                    } else {
                        setText(nf.format(dish.getPrice()));
                    }
                }
            };
        });


        chosenDishTable.getColumns().setAll(dishNameCol, quantityCol, priceCol);
        chosenDishTable.setItems(chosenOL);
        addDayAndTimeButton.setOnAction(addDayAndTimeEvent);
        addDishButton.setOnAction(addDishToOrderEvent);
        addToSubscriptionButton.setOnAction(addToSubscriptionEvent);


    }
}
