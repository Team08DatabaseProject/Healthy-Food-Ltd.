package users.chef;

/**
 * Created by axelkvistad on 4/14/16.
 */
import classpackage.*;
import classpackage.Menu;
import div.Login;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerChefAddMenu implements Initializable{

    @FXML
    public GridPane addMenuGP;
    public TextField menuNameTF;
    public TableView selectDishes;
    public TextField discountTF;
    public TableView selectedDishes;
    public TableColumn nameLeft;
    public TableColumn priceLeft;
    public TableColumn checkBoxLeft;
    public TableColumn selectedRight;
    public Button addMenuButton;

    TestObjects to = new TestObjects();
    final ObservableList<DishLine> ingredients = to.ingredientListDL1;

    final ObservableList<Dish> dishesLeft = FXCollections.observableArrayList(
            new Dish(100, "Bacon", ingredients),
            new Dish(150, "Taco", ingredients),
            new Dish(245, "Fish 'n' chips", ingredients),
            new Dish(130, "Steak", ingredients)
    );

    final ObservableList<Dish> dishesRight = FXCollections.observableArrayList();

    EventHandler<ActionEvent> addMenu = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String menuName = menuNameTF.getText();
                String mealType = "dinner";
                Menu newMenu = new Menu(menuName, mealType, dishesRight);
                ControllerChefMenus ccm = new ControllerChefMenus();
                ccm.addMenu(newMenu);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };





    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

        nameLeft.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        priceLeft.setCellValueFactory(new PropertyValueFactory<Dish, Double>("price"));
        selectedRight.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        selectDishes.setEditable(true);
        selectedDishes.setEditable(true);

        checkBoxLeft.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer index) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            dishesRight.add(dishesLeft.get(index));
                        } else {
                            dishesRight.remove(dishesLeft.get(index));
                        }
                    }
                });
                return observable;
            }
        }));

        checkBoxLeft.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Dish, Boolean>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Dish, Boolean> event) {
                        if (event.getNewValue()) {
                            dishesRight.add(event.getRowValue());
                            selectedDishes.setItems(dishesRight);
                        }
                    }
                });



        ObservableList<TableColumn> leftColumns = FXCollections.observableArrayList(nameLeft, priceLeft, checkBoxLeft);
        selectDishes.getColumns().setAll(leftColumns);
        selectDishes.setItems(dishesLeft);

        ObservableList<TableColumn> rightColumns = FXCollections.observableArrayList(selectedRight);
        selectedDishes.getColumns().setAll(rightColumns);
        selectedDishes.setItems(dishesRight);
        addMenuButton.setOnAction(addMenu);

    }
}