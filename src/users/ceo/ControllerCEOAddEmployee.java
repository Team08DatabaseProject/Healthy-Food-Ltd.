package users.ceo;
/**
 * Created by Axel 16.03.2016
 * Controller for the ceo
 */

import classpackage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCEOAddEmployee extends ControllerCEOEmployees implements Initializable {

    @FXML
    //public TableView tables2; // Retrieves TableView with fx:id="tables2"
    public Button addEmployeeButton;
    public GridPane textGrid;
    public TextField idField;
    public TextField fNameField;
    public TextField lNameField;
    public TextField phoneField;
    public TextField emailField;
    public TextField addressField;
    public TextField zipCodeField;
    public TextField usernameField;
    public TextField posIdField;
    public TextField salaryField;
    public PasswordField passwordField;
    private SqlQueries query = new SqlQueries();

    EventHandler<ActionEvent> addEmployee = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                String firstName = fNameField.getText();
                String lastName = lNameField.getText();
                int phoneNo = Integer.parseInt(phoneField.getText());
                String eMail = emailField.getText();
                String address = addressField.getText();
                int zipCode = Integer.parseInt(zipCodeField.getText());
                String username = usernameField.getText();
                int posId = Integer.parseInt(posIdField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                String passhash = passwordField.getText();
                ZipCode zipCodeObject = new ZipCode(zipCode, "testtest");
                Address addressObject = new Address(address, zipCodeObject);
                EmployeePosition employeePosition = new EmployeePosition(posId, "test", 1234);
                Employee newEmp = new Employee(username, firstName, lastName, phoneNo, eMail, salary, passhash, addressObject, employeePosition);
                query.addEmployee(newEmp);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };


    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
        addEmployeeButton.setOnAction(addEmployee);
    }
}
