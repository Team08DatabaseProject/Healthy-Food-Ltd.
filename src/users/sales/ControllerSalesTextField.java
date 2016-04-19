package users.sales;

import classpackage.*;
import java.lang.String;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 07/04/2016.
 */

public class ControllerSalesTextField extends ControllerSales implements Initializable{

    @FXML
    public TextField orderIdField;
    public TextField customerIdField;
    public TextField subscriptionIdField;
    public TextField startSubscription;
    public TextField endSubscription;
    public TextField fNameField;
    public TextField lNameField;
    public CheckBox businessBox;
    public TextField businessNameField;
    public TextField emailField;
    public TextField phoneField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField placeField;
    public TextArea customerRequestsField;
    public DatePicker deadlinePicker;
    public TextField priceField;
    public Button createButton;
    public ComboBox<Dish> chooseDishes;
    public Button addDishButton;

    ObservableList<String> statusComboBoxValues = FXCollections.observableArrayList(
        "Created", "In preparation", "Ready for delivery", "Under delivery", "Delivered"
    );

    public ComboBox statusBox = new ComboBox(statusComboBoxValues);

    EventHandler<ActionEvent> createOrderEventField = new EventHandler<ActionEvent>() {
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
                ZipCode zipCode = new ZipCode(zipCodeInt, place);
                String customerRequests = customerRequestsField.getText();
                LocalDate deadline = deadlinePicker.getValue();
                double price = Double.parseDouble(priceField.getText());
                String status = (String)statusBox.getValue();
                int orderId = Integer.parseInt(orderIdField.getText());
                int customerId = Integer.parseInt(customerIdField.getText());
                int subcriptionId = Integer.parseInt(subscriptionIdField.getText());
                LocalDate startSubscription = deadlinePicker.getValue();
                LocalDate endSubscription = deadlinePicker.getValue();
                String dishName = "";
                Address newAddress = new Address(address, zipCodeInt, place);
                Supplier supplier = new Supplier(12345678, newAddress, "Business as");
                Ingredient ingredient = new Ingredient("Ravioli", "grams", 2000, 20, supplier);
                ObservableList<DishLine> dishLines = FXCollections.observableArrayList(
                        new DishLine(ingredient, 7.0),
                        new DishLine(ingredient, 2.0)
                );
                Dish dishes = new Dish(price, dishName, dishLines);
                ObservableList<Dish> dishesInThisOrder = FXCollections.observableArrayList();
                ObservableList<Order> order = FXCollections.observableArrayList(
                        new Order(customerRequests, deadline, price, status, dishesInThisOrder)
                );
                Subscription subscription = new Subscription(startSubscription, endSubscription, order);
                Customer customer = new Customer(isBusiness, email, firstName, lastName, phoneNumber,
                        newAddress, businessName, subscription, order);

            } catch(Exception exc) {
                System.out.println("createOrderEventField: " + exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
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

        createButton.setOnAction(createOrderEventField);
    }
}