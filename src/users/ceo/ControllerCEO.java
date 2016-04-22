package users.ceo;
/**
 * Created by Axel 16.03.2016
 * Controller for the CEO
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import classpackage.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCEO implements Initializable {

    @FXML
    //public TableView tables2; // Retrieves TableView with fx:id="tables2"
    public Button employeesButton;
    public Button inventoryButton;
    public BorderPane rootPaneDriver;
    protected static SqlQueries db = new SqlQueries();

    private final int EMPLOYEES = 1;
    private final int INVENTORY = 2;
    private int currentView;

    EventHandler<ActionEvent> viewEmployees = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            if(currentView != EMPLOYEES) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("CEOEmployees.fxml"));
                    GridPane employeesTable = loader.load();
                    rootPaneDriver.setCenter(employeesTable);
                    currentView = EMPLOYEES;
                } catch(Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };

    EventHandler<ActionEvent> viewInventory = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            if(currentView != INVENTORY) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("CEOInventory.fxml"));
                    TabPane inventoryTabs = loader.load();
                    rootPaneDriver.setCenter(inventoryTabs);
                    currentView = INVENTORY;
                } catch(Exception exc) {
                    System.out.println(exc);
                }
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
        employeesButton.setOnAction(viewEmployees);
        inventoryButton.setOnAction(viewInventory);

    }
}
