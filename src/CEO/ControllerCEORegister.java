package CEO;
/**
 * Created by Axel 16.03.2016
 * Controller for the Driver
 */

import classpackage.Employee;
import classpackage.SqlQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCEORegister implements Initializable {

    @FXML
    //public TableView tables; // Retrieves TableView with fx:id="tables"
    public Button finalRegisterButton;
    public GridPane textGrid;
    public TextField idField;
    public TextField fNameField;
    public TextField lNameField;
    public TextField phoneField;
    public TextField emailField;
    public TextField addressField;
    public TextField usernameField;
    public TextField posIdField;
    public TextField salaryField;
    public PasswordField passwordField;
    private SqlQueries query = new SqlQueries();

    EventHandler<ActionEvent> finalRegistrationEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                int employeeId = Integer.parseInt(idField.getText());
                String fName = fNameField.getText();
                String lName = lNameField.getText();
                int phone = Integer.parseInt(phoneField.getText());
                String email = emailField.getText();
                int addressId = Integer.parseInt(addressField.getText());
                String username = usernameField.getText();
                int posId = Integer.parseInt(posIdField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                String passhash = passwordField.getText();

                Employee newEmp = new Employee(employeeId, username, posId, salary, passhash);
                query.addEmployee(newEmp, fName, lName, phone, email, addressId);

            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
        finalRegisterButton.setOnAction(finalRegistrationEvent);
    }
}
