package users.chef;

/**
 * Created by Axel Kvistad on 13.04.2016
 */
import div.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import classpackage.Menu;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChefMenus implements Initializable{

    @FXML
    public GridPane ChefMenus;
    public Button editMenuButton;
    public Button addMenuButton;
    public ComboBox chooseMenuCB;

    private ObservableList<Menu> menus = FXCollections.observableArrayList();

    public void addMenu(Menu newMenu) {
        menus.add(newMenu);
        chooseMenuCB.setItems(menus);
    }

    EventHandler<ActionEvent> addMenuButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                final Stage addMenuStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("ChefAddMenu.fxml"));
                addMenuStage.setTitle("Add new menu");
                addMenuStage.setScene(new Scene(root, 500, 500));
                addMenuStage.show();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        addMenuButton.setOnAction(addMenuButtonClick);
    }
}
