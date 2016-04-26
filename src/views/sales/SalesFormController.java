package views.sales;

import classpackage.*;
import classpackage.Menu;
import javafx.collections.ListChangeListener;
import main.PopupDialog;
import javafx.application.Platform;
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
    public GridPane subMenuGP;
    public TextField customerIdField;
    public TextField subscriptionIdField;
    public TextField fNameField;
    public TextField lNameField;
    public TextField customerAddressField;
    public TextField customerZipCodeField;
    public TextField customerPlaceField;
    public CheckBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField placeField;
    public Button deliveryToCustomerAddressButton;
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
    public ComboBox<Menu> chooseMenus;
    public Button setToMenuButton;
    public Button removeMenuButton;

    private double totalPrice = 0;
    private Address deliveryAddress;

    ObservableList<OrderLine> oldOrderLines = FXCollections.observableArrayList();
    ObservableList<OrderLine> chosenOrderLines = FXCollections.observableArrayList();
    ObservableList<OrderLine> addOrderLines = FXCollections.observableArrayList();
    ObservableList<OrderLine> removeOrderLines = FXCollections.observableArrayList();

    //ObservableLists for the hour and minute ComboBoxes:
    //Opening/delivery hours from 7 AM to 10 PM
    final ObservableList<Integer> deadlineHourList = FXCollections.observableArrayList(
            7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22);
    //Minute selection for every 15 minutes of every hour
    final ObservableList<Integer> deadlineMinuteList = FXCollections.observableArrayList(0, 15, 30, 45);


    EventHandler<ActionEvent> setDeliveryToCustomerAddressEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if (selectedCustomer != null) {
                    addressField.setText(selectedCustomer.getAddress().getAddress());
                    zipCodeField.setText(String.valueOf(selectedCustomer.getAddress().getZipCode()));
                    placeField.setText(selectedCustomer.getAddress().getPlace());
                    deliveryAddress = new Address(selectedCustomer.getAddress().getAddressId(), selectedCustomer.getAddress().getAddress(),
                            selectedCustomer.getAddress().getZipCode(), selectedCustomer.getAddress().getPlace());

                } else {
                    PopupDialog.errorDialog("Error", "No customer-registered address could be found.\nPlease enter delivery location manually.");
                }
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    EventHandler<ActionEvent> addDishToOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                if (selectedDish != null){
                    boolean add = true;
                    for (OrderLine ol : chosenOrderLines) {
                        if (ol.getDish().equals(selectedDish)){
                            add = false;
                        }
                    } if (add){

                        for (OrderLine ol : addOrderLines) {
                            if (ol.getDish().equals(selectedDish)) {
                                addOrderLines.remove(ol);
                            }
                        }
                        OrderLine newOL = new OrderLine(selectedDish);
                        newOL.setOriginal(false);
                        chosenOrderLines.add(newOL);
                        addOrderLines.add(newOL);
                        chosenDishTable.setItems(chosenOrderLines);
                        totalPrice = 0;
                        for (OrderLine ol : chosenOrderLines){
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

    EventHandler<ActionEvent> addMenuToOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                if (selectedMenu != null && chosenOrderLines.isEmpty()){

                    ObservableList<MenuLine> tempMenuLines = selectedMenu.getMenuLines();
                    for (MenuLine ml : tempMenuLines) {
                        OrderLine newOL = new OrderLine(ml.getDish(), ml.getAmount());
                        chosenOrderLines.add(newOL);
                    }
                    totalPrice = selectedMenu.getTotalPrice();
                    priceField.setText(nf.format(totalPrice));
                    priceField.setEditable(false);
                    chooseDishes.setDisable(true);
                    chooseMenus.setDisable(true);
                    addDishButton.setDisable(true);
                    setToMenuButton.setDisable(true);
                    removeMenuButton.setDisable(false);
                    chosenDishTable.setEditable(false);
                    addOrderLines.clear();
                    addOrderLines.addAll(chosenOrderLines);
                    if (selectedOrder != null) {
                        removeOrderLines.clear();
                        removeOrderLines.addAll(oldOrderLines);
                    }
                } else {
                    PopupDialog.informationDialog("Information", "To set an order as a menu, you must make sure\n" +
                            "that the chosen dish table is empty and that you have selected a menu to add.");
                }
            }catch (Exception e){
                System.out.println("addDishToOrderEvent " + e);
            }
        }
    };

    EventHandler<ActionEvent> removeMenuFromOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                chosenDishTable.setEditable(true);
                chosenOrderLines.clear();
                addOrderLines.clear();
                totalPrice = 0;
                priceField.setText(nf.format(totalPrice));
                priceField.setEditable(true);
                chooseDishes.setDisable(false);
                chooseMenus.setDisable(false);
                addDishButton.setDisable(false);
                setToMenuButton.setDisable(false);
                removeMenuButton.setDisable(true);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    EventHandler<ActionEvent> removeDishFromOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                selectedOrderLine = (OrderLine) chosenDishTable.getSelectionModel().getSelectedItem();
                if (selectedOrderLine != null) {
                    if (selectedOrderLine.isOriginal() && !selectedOrderLine.isChanged()) {
                        removeOrderLines.add(selectedOrderLine);
                    }

                    addOrderLines.remove(selectedOrderLine);
                    chosenOrderLines.remove(selectedOrderLine);

                    totalPrice = 0;
                    for (OrderLine ol : chosenOrderLines){
                        totalPrice += ol.getTotal();
                    }
                    priceField.setText(nf.format(totalPrice));

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
                double price = totalPrice;
                OrderStatus status = statusBox.getValue();
                if (deliveryAddress.getAddressId() == 0) {
                    deliveryAddress = new Address(address, zipCodeInt, place);
                }
                Order newOrder = new Order(customerRequests, deadlineLDT, price, status, chosenOrderLines, deliveryAddress);
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                String email = emailField.getText();
                int phoneNumber = Integer.parseInt(phoneField.getText());
                String customerAddress = customerAddressField.getText();
                int customerZipCode = Integer.parseInt(customerZipCodeField.getText());
                String customerPlace = customerPlaceField.getText();
                Address customerAddressObject = new Address(customerAddress, customerZipCode, customerPlace);
                boolean isBusiness = businessBox.isArmed();
                Customer newCustomer;
                orders.add(newOrder);

                if (selectedOrder != null && selectedCustomer != null) {
                    selectedOrder.setDeadlineTime(deadlineLDT);
                    selectedOrder.setPrice(price);
                    selectedOrder.getAddress().setAddress(address);
                    selectedOrder.getAddress().setZipCode(zipCodeInt);
                    selectedOrder.getAddress().setPlace(place);
                    selectedOrder.setStatus(status);
                    selectedOrder.setCustomerRequests(customerRequests);
                    boolean ok = true;
                    if (!removeOrderLines.isEmpty()) {
                        if (!db.deleteOrderLine(selectedOrder, removeOrderLines)) {
                            PopupDialog.errorDialog("Error", "Unable to remove dishes from order.");
                            ok = false;
                        }
                    }
                    if (!addOrderLines.isEmpty() && ok) {
                        if (!db.addOrderLines(selectedOrder, addOrderLines)) {
                            PopupDialog.errorDialog("Error", "Unable to add dishes to order.");
                            ok = false;
                        }
                    }
                    if (!chosenOrderLines.isEmpty() && ok) {
                        selectedOrder.setDishesInThisOrder(chosenOrderLines);
                    } else {
                        ObservableList<OrderLine> emptyOL = FXCollections.observableArrayList();
                        selectedOrder.setDishesInThisOrder(emptyOL);
                    }
                    if (ok && db.updateOrder(selectedOrder)) {
                        PopupDialog.confirmationDialog("Result", "Order successfully updated.");
                    } else if (ok) {
                        PopupDialog.errorDialog("Error", "Order failed to update.");
                    }
                } else if (selectedCustomer == null){
                    ObservableList<Order> newOrderList = FXCollections.observableArrayList(newOrder);
                    if (isBusiness && !businessNameField.getText().isEmpty()) {
                        String businessName = businessNameField.getText();
                        newCustomer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                                customerAddressObject, businessName, null, newOrderList);
                    } else {
                        newCustomer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                                customerAddressObject, null, null, newOrderList);
                    }
                    ObservableList<Order> firstOrder = FXCollections.observableArrayList(newOrder);
                    newCustomer.setOrders(firstOrder);
                    if (db.addCustomer(newCustomer) && db.addOrder(null, newOrder, newCustomer)) {
                        PopupDialog.confirmationDialog("Result", "New customer and order registered");
                    } else {
                        PopupDialog.errorDialog("Error", "Customer and order failed to register.");
                    }
                } else {
                        if (selectedCustomer.getSubscription() != null && !db.updateSubscription(selectedCustomer.getSubscription())) {
                            PopupDialog.errorDialog("Error", "Failed to update subscription.");
                        }
                        if(db.addOrder(null, newOrder, selectedCustomer)) {
                            selectedCustomer.getOrders().add(newOrder);
                            PopupDialog.confirmationDialog("Result", "Order successfully registered to customer.");
                        } else {
                            PopupDialog.errorDialog("Error", "Failed to update order.");
                        }

                }
            } catch(Exception exc) {
                System.out.println("createOrderFieldEvent: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                subMenuGP.requestFocus();
            }
        });



        subscriptionIdField.setDisable(true);
        customerIdField.setDisable(true);
        removeMenuButton.setDisable(true);

        if (selectedCustomer != null) {
            subscriptionIdField.setDisable(true);
            fNameField.setDisable(true);
            lNameField.setDisable(true);
            businessBox.setDisable(true);
            businessNameField.setDisable(true);
            emailField.setDisable(true);
            phoneField.setDisable(true);
            customerAddressField.setDisable(true);
            customerZipCodeField.setDisable(true);
            customerPlaceField.setDisable(true);

            if (selectedCustomer.getSubscription() != null) {
                subscriptionIdField.setText("Subscription ID: " + selectedCustomer.getSubscription().getSubscriptionId());
            }
            fNameField.setText(selectedCustomer.getFirstName());
            lNameField.setText(selectedCustomer.getLastName());
            businessBox.setSelected(selectedCustomer.getIsBusiness());
            if (selectedCustomer.getIsBusiness()) {
                businessNameField.setText(selectedCustomer.getBusinessName());
            }
            emailField.setText(selectedCustomer.getEmail());
            phoneField.setText(String.valueOf(selectedCustomer.getPhoneNumber()));
            customerAddressField.setText(selectedCustomer.getAddress().getAddress());
            customerZipCodeField.setText(String.valueOf(selectedCustomer.getAddress().getZipCode()));
            customerPlaceField.setText(selectedCustomer.getAddress().getPlace());
            customerIdField.setText("Customer ID: " + selectedCustomer.getCustomerId());
        } else {
            deliveryToCustomerAddressButton.setDisable(true);
        }

        if (selectedOrder != null) {
            for (OrderLine orderLine : selectedOrder.getDishesInThisOrder()) {
                orderLine.setOriginal(true);
            }
            setToMenuButton.setDisable(true);
            chooseMenus.setDisable(true);
            createButton.setText("Apply changes");
            addressField.setText(selectedOrder.getAddress().getAddress());
            zipCodeField.setText(String.valueOf(selectedOrder.getAddress().getZipCode()));
            placeField.setText(selectedOrder.getAddress().getPlace());
            deadlineDatePicker.setValue(selectedOrder.getDeadlineTime().toLocalDate());
            deadlineHrBox.setValue(selectedOrder.getDeadlineTime().getHour());
            deadlineMinBox.setValue(selectedOrder.getDeadlineTime().getMinute());
            statusBox.setValue(selectedOrder.getStatus());
            totalPrice = selectedOrder.getPrice();
            priceField.setText(nf.format(totalPrice));
            chosenOrderLines = selectedOrder.getDishesInThisOrder();
            oldOrderLines.setAll(chosenOrderLines);
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

        chooseMenus.setItems(menus);
        chooseMenus.setConverter(new StringConverter<Menu>() {
            @Override
            public String toString(Menu menu) {
                if (menu == null){
                    return null;
                } else {
                    return menu.getName();
                }
            }
            @Override
            public Menu fromString(String string) {
                return null;
            }
        });
        chooseMenus.valueProperty().addListener(new ChangeListener<Menu>() {
            @Override
            public void changed(ObservableValue<? extends Menu> observable, Menu oldValue, Menu newValue) {
                selectedMenu = newValue;
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

                        if (event.getTableView().getItems().get(event.getTablePosition().getRow()).isOriginal() && !event.getTableView().getItems().get(event.getTablePosition().getRow()).isChanged()) {
                            removeOrderLines.add(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setChanged(true);
                            System.out.println("Adding pasta to removeOL");
                        } else {
                            addOrderLines.remove(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAmount(event.getNewValue());
                            addOrderLines.add(event.getTableView().getItems().get(event.getTablePosition().getRow()));
                        }
                        double totalPrice = 0;
                        for (OrderLine ol : chosenOrderLines){
                            totalPrice += ol.getTotal();
                        }
                        priceField.setText(nf.format(totalPrice));
                    }
                });

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

        chosenDishTable.setItems(chosenOrderLines);

        createButton.setOnAction(createOrderFieldEvent);
        addDishButton.setOnAction(addDishToOrderEvent);
        removeOrderDishButton.setOnAction(removeDishFromOrderEvent);
        deliveryToCustomerAddressButton.setOnAction(setDeliveryToCustomerAddressEvent);
        setToMenuButton.setOnAction(addMenuToOrderEvent);
        removeMenuButton.setOnAction(removeMenuFromOrderEvent);
    }
}