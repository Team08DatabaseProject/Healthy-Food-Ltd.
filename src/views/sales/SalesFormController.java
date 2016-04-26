package views.sales;

import classpackage.*;
import java.lang.String;

import main.PopupDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 07/04/2016.
 */

public class SalesFormController extends SalesController implements Initializable{

    @FXML
    public GridPane ordersFormGrid;
    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subscriptionIdField;
    public TextField fNameField;
    public TextField lNameField;
    public CheckBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public Label priceFieldLabel;
    public TextField placeField;
    public TextArea customerRequestsField;
    public Label deadlineLabel;
    public ComboBox<Integer> deadlineHrBox;
    public ComboBox<Integer> deadlineMinBox;
    public DatePicker deadlineDatePicker;
    public TextField priceField;
    public Button createButton;
    public ComboBox<Dish> chooseDishes;
    public Button addDishButton;
    public TableView chosenDishTable;
    public TableColumn dishNameCol;
    public TableColumn quantityCol;
    public TableColumn priceCol;
    public ComboBox<OrderStatus> statusBox;
    public Button removeOrderDishButton;


    ObservableList<OrderLine> chosenDishes = FXCollections.observableArrayList();

    //ObservableLists for the hour and minute ComboBoxes:
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
                    for (OrderLine ol : chosenDishes) {
                        if (ol.getDish().getDishName().equals(selectedDish.getDishName())){
                            add = false;
                        }
                    }
                    if (add){
                        OrderLine newOL = new OrderLine(selectedDish);
                        chosenDishes.add(newOL);
                        chosenDishTable.setItems(chosenDishes);
                        double totalPrice = 0;
                        for (OrderLine ol : chosenDishes){
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

    EventHandler<ActionEvent> removeDishFromOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedOrderLine = (OrderLine) chosenDishTable.getSelectionModel().getSelectedItem();
                if (selectedOrderLine != null) {
                    chosenDishes.remove(selectedOrderLine);
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No dish selected");
                    alert.setContentText("Select a dish to delete it");
                    alert.showAndWait();
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> createOrderFieldEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                boolean isBusiness = businessBox.isArmed();
                String businessName = businessNameField.getText();
                String email = emailField.getText();
                int phoneNumber = Integer.parseInt(phoneField.getText());
                String address = addressField.getText();
                int zipCodeInt = Integer.parseInt(zipCodeField.getText());
                String place = placeField.getText();
                String customerRequests = customerRequestsField.getText();

                // deadline:
                LocalDate deadlineDate = deadlineDatePicker.getValue();
                Integer deadlineHr = deadlineHrBox.getValue();
                Integer deadlineMin = deadlineMinBox.getValue();
                LocalTime deadlineTime = LocalTime.of(deadlineHr, deadlineMin);
                LocalDateTime deadlineLDT = LocalDateTime.of(deadlineDate, deadlineTime);

                // startsub:
                LocalDate startSubscription = deadlineDatePicker.getValue();
                LocalDate endSubscription = deadlineDatePicker.getValue();

                double price = Double.parseDouble(priceField.getText());
                OrderStatus status = statusBox.getValue();
               // int orderId = Integer.parseInt(orderIdField.getText());
                int customerId = Integer.parseInt(customerIdField.getText());
                int subcriptionId = Integer.parseInt(subscriptionIdField.getText());

                Address newAddress = new Address(address, zipCodeInt, place);
                ObservableList<Dish> dishesInThisOrder = FXCollections.observableArrayList();

                Order newOrder = new Order(customerRequests, deadlineLDT, price, status, dishesInThisOrder, newAddress);
                orders.add(newOrder);

                Subscription subscription = new Subscription(startSubscription, endSubscription, orders);
                if (selectedCustomer != null) {
                    ObservableList<Order> newOrderList = selectedCustomer.getOrders();
                    newOrderList.add(newOrder);
                    selectedCustomer.setOrders(newOrderList);
                    if (db.updateCustomer(selectedCustomer)) {
                        PopupDialog.confirmationDialog("Result", "Order succesfully registered to customer.");
                    } else {
                        PopupDialog.errorDialog("Error", "Order failed to register.");
                    }
                } else {
                    ObservableList<Order> newOrderList = FXCollections.observableArrayList(newOrder);
                    Customer newCustomer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                            newAddress, businessName, subscription, newOrderList);
                    if(db.addCustomer(newCustomer)) {
                        PopupDialog.confirmationDialog("Result", "New customer successfully added and order registered.");
                    } else {
                        PopupDialog.errorDialog("Error", "New customer and order failed to register.");
                    }
                }

            } catch(Exception exc) {
                System.out.println("createOrderFieldEvent: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        //Disables the ability to change these three fields
        orderIdField.setDisable(true);
        customerIdField.setDisable(true);
        subscriptionIdField.setDisable(true);
        //.

        if (selectedCustomer != null) {
            orderIdField.setDisable(true);
            customerIdField.setDisable(true);
            subscriptionIdField.setDisable(true);
            fNameField.setDisable(true);
            lNameField.setDisable(true);
            businessBox.setDisable(true);
            businessNameField.setDisable(true);
            emailField.setDisable(true);
            phoneField.setDisable(true);
            addressField.setDisable(true);
            zipCodeField.setDisable(true);
            customerIdField.setText(String.valueOf(selectedCustomer.getCustomerId()));
            if (selectedCustomer.getSubscription() != null) {
                subscriptionIdField.setText(String.valueOf(selectedCustomer.getSubscription().getSubscriptionId()));
            }
            fNameField.setText(selectedCustomer.getFirstName());
            lNameField.setText(selectedCustomer.getLastName());
            businessBox.setSelected(selectedCustomer.getIsBusiness());
            if (selectedCustomer.getIsBusiness()) {
                businessNameField.setText(selectedCustomer.getBusinessName());
            }
            emailField.setText(selectedCustomer.getEmail());
            phoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
            addressField.setText(selectedCustomer.getAddress().getAddress());
            zipCodeField.setText(String.valueOf(selectedCustomer.getAddress().getZipCode()));
        }

        deadlineHrBox.setItems(deadlineHourList);
        deadlineHrBox.setCellFactory(column -> {
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

        deadlineMinBox.setItems(deadlineMinuteList);
        deadlineMinBox.setCellFactory(column -> {
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

        statusBox.setItems(statusTypes);
        statusBox.setConverter(new StringConverter<OrderStatus>() {
            @Override
            public String toString(OrderStatus os) {
                if (os == null){
                    return null;
                } else {
                    return os.getName();
                }
            }

            @Override
            public OrderStatus fromString(String string) {
                return null;
            }
        });

        chooseDishes.setItems(dishes);
        chooseDishes.setConverter(new StringConverter<Dish>() {
            @Override
            public String toString(Dish dish) {
                if (dish == null){
                    return null;
                } else {
                    return dish.getDishName();
                }
            }

            @Override
            public Dish fromString(String string) {
                return null;
            }
        });
        chooseDishes.valueProperty().addListener(new ChangeListener<Dish>() {
            @Override
            public void changed(ObservableValue<? extends Dish> observable, Dish oldValue, Dish newValue) {
                selectedDish = newValue;
            }
        });

        chosenDishTable.setEditable(true);
        dishNameCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish")); //dishName
        quantityCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Integer>("amount")); //qty
        priceCol.setCellValueFactory(new PropertyValueFactory<OrderLine, Dish>("dish")); //price

        dishNameCol.setCellFactory(column -> {
            return new TableCell<OrderLine, Dish>() {
                @Override
                protected void updateItem(Dish dish, boolean empty) {
                    if(dish == null || empty) {
                        setText(null);
                    } else {
                        setText(dish.getDishName());
                    }
                }
            };
        });

        quantityCol.setCellFactory(TextFieldTableCell.<OrderLine, Integer>forTableColumn(new IntegerStringConverter()));
        quantityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<OrderLine, Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<OrderLine, Integer> event) {
                        event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                        double totalPrice = 0;
                        for (OrderLine ol : chosenDishes){
                            totalPrice += ol.getTotal();
                        }
                        priceField.setText(nf.format(totalPrice));
                    }
                }
        );

        priceField.setDisable(true);
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

        createButton.setOnAction(createOrderFieldEvent);
        addDishButton.setOnAction(addDishToOrderEvent);
        removeOrderDishButton.setOnAction(removeDishFromOrderEvent);
    }
}