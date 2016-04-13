/*
package users.sales;

import classpackage.Order;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.beans.value.*;
import javafx.beans.*;
import javafx.beans.property.*;

import java.net.URL;
import java.util.ResourceBundle;

*/
/**
 * Created by Trym Todalshaug on 07/04/2016.
 *//*


public class ControllerSalesDelete extends Application {
    public Button deleteButton;

    private IntegerProperty index = new SimpleIntegerProperty();

    public final double getIndex() {
        return index.get();
    }

    public final void setIndex(Integer value) {
        index.set(value);
    }

    public IntegerProperty indexProperty() {
        return index;
    }

    BorderPane rootPaneCreate;
    TableView ordersTable;
    public ObservableList<Order> orders = FXCollections.observableArrayList();

    EventHandler<ActionEvent> deleteOrderEvent = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("OrdersTable.fxml"));
                rootPaneCreate = loader.load();

                indexProperty().addListener(new ChangeListener() {
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        System.out.println("Index is changed");
                    }
                });

                ordersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                        Order order = (Order) newValue;
                        setIndex(orders.indexOf(newValue));
                        System.out.println("OK");
                    }
                });

            }catch (Exception e){
                System.out.println("deleteOrderEvent" + e);
            }
        }
    };

    public void initialize(URL fxmlFileLocation, ResourceBundle resources)

        indexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal, Object newVal) {
                System.out.println("Index has changed!");
            }
        });

        deleteButton.setOnAction(deleteOrderEvent);

        @Override
        public void handle(ActionEvent e) {

            orders.remove(index.get());
            ordersTable.getSelectionModel().clearSelection();

        }

    }
}
*/
