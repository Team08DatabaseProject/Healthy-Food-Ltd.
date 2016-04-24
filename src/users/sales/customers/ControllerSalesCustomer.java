package users.sales.customers;

import classpackage.Customer;
import classpackage.Subscription;
import div.PopupDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import users.sales.ControllerSales;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Trym Todalshaug on 20/04/2016.
 */
public class ControllerSalesCustomer extends ControllerSales implements Initializable {

    @FXML
    public TableView customersTable;
    public TableColumn customerIdCol;
    public TableColumn subscriptionIdCol;
    public TableColumn fNameCol;
    public TableColumn lNameCol;
    public Button addCustomerButton;
    public Button deleteCustomerButton;

    protected static ObservableList<Customer> customer;

    protected static boolean customerFormUpdate = false;

    EventHandler<ActionEvent> createCustomerEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                customerFormUpdate = false;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CustomersCreateForm.fxml"));
                GridPane addCustomerTable = loader.load();
                Scene formScene = new Scene(addCustomerTable, 300, 550);
                Stage formStage = new Stage();
                formStage.setTitle("New customer");
                formStage.setScene(formScene);
                formStage.show();
            }catch (Exception e){
                System.out.println("createCustomerEvent " + e);
            }
        }
    };

    private EventHandler<ActionEvent> refreshCustomers = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            customers = db.getAllCustomers(orders);
            customersTable.setItems(customers);
        }
    };

    /*private EventHandler<MouseEvent> updateCustomerForm = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
                try {
                    customerFormUpdate = true;
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("CustomersCreateForm.fxml"));
                    GridPane addCustomerTable = loader.load();
                    Scene formScene = new Scene(addCustomerTable, 300, 550);
                    Stage formStage = new Stage();
                    formStage.setTitle("Update Customer");
                    formStage.setScene(formScene);
                    formStage.show();
                } catch (Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };*/

    EventHandler<ActionEvent> deleteCustomerEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
                if(selectedIndex >= 0){
                    customersTable.getItems().remove(selectedIndex);
                }else{
                    //Nothing selected
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No selection");
                    alert.setHeaderText("No customer selected");
                    alert.setContentText("Select a customer to delete it");

                    alert.showAndWait();
                }
            }catch (Exception e){
                System.out.println("deleteCustomerEvent" + e);
            }
        }
    };

    EventHandler<MouseEvent> customersInfoEvent = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            try{
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1){
                    selectedCustomer = (Customer) customersTable.getSelectionModel().getSelectedItem();
                    for (Customer customer : customers){
                        if (customer.getCustomerId() == selectedCustomer.getCustomerId()
                                && customer.getFirstName() == selectedCustomer.getFirstName()
                                && customer.getLastName() == selectedCustomer.getLastName()){
                            selectedCustomer = customer;
                        }
                    }
                }
            }catch (Exception e) {
                System.out.println("customerInfoEvent " + e);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources){

        customersTable.setEditable(true);
        ObservableList<TableColumn> columns = customersTable.getColumns();
        TableColumn<Customer,Integer> customerIdCol = columns.get(0);
        TableColumn<Customer, Subscription> subscriptionIdCol = columns.get(1);
        TableColumn<Customer,String> fNameCol = columns.get(2);
        TableColumn<Customer,String> lNameCol = columns.get(3);


        customerIdCol.setCellValueFactory(new PropertyValueFactory<Customer,Integer>("customerId")); //customerId
        subscriptionIdCol.setCellValueFactory(new PropertyValueFactory<Customer,Subscription>("subscription")); //subscriptionId
        fNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("firstName")); //firstName
        lNameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("lastName")); //lastName

        customersTable.getColumns().setAll(customerIdCol, subscriptionIdCol, fNameCol, lNameCol);
        customersTable.setOnMousePressed(customersInfoEvent);
        addCustomerButton.setOnAction(createCustomerEvent);
        deleteCustomerButton.setOnAction(deleteCustomerEvent);
    }
}
