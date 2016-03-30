package CEO;
/**
 * Created by Axel 16.03.2016
 * Controller for the driver
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCEO implements Initializable {

    @FXML
    //public TableView tables; // Retrieves TableView with fx:id="tables"
    public Button registerEmployeeButton;
    public BorderPane rootPaneDriver;
    public Button registerButton;


    EventHandler<ActionEvent> registerEmployeeEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("CEOTextField.fxml"));
                GridPane registerEmployeePane = loader.load();
                rootPaneDriver.setCenter(registerEmployeePane);
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) { // Required method for Initializable, runs at program launch
        registerEmployeeButton.setOnAction(registerEmployeeEvent);
    }
}
