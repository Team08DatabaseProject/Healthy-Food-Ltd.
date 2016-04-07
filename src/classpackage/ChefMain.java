package classpackage;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableMapValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

import java.util.*;


public class ChefMain extends Application {

// TODO: 4/6/16 change  
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        // List of default test menus
        ObservableList<Menu> currentMenus = FXCollections.observableArrayList();
        currentMenus.add(new Menu(1, "veggie", "vegitarian"));
        currentMenus.add(new Menu(2, "vegan", "vegan"));
        currentMenus.add(new Menu(1, "meatlovers", ""));

        // Menu which is currently displayed on screen
        ObservableList<Menu> displayedMenu = FXCollections.observableArrayList();

        // List of default test dishes
        ObservableList<Dish> currentDishes = FXCollections.observableArrayList();
        currentDishes.add(new Dish(1, 29, "spaghetti"));
        currentDishes.add(new Dish(2, 234, "ravioli"));
        currentDishes.add(new Dish(3, 2, "lasagna"));

        Map<Dish, Integer> dishMap = new HashMap<>();

        for (Dish d : currentDishes) {
            dishMap.put(d, 1);
        }
        
        // Dishes which are currently displayed on screen
        ObservableList<Dish> displayedDishes = FXCollections.observableArrayList();

        // List of default test ingredients
        ObservableList<Ingredient> currentIngredients = FXCollections.observableArrayList(
                new Ingredient(1, "sugar", "kg", 22, 30, 234),
                new Ingredient(2, "flour", "kg", 20, 30, 234),
                new Ingredient(3, "baking soda", "kg", 20, 30, 234)
        );
        
        ObservableList<Ingredient> newIngredients = FXCollections.observableArrayList();

        //First Combobox
        ComboBox menuComboBox = new ComboBox(currentMenus);
        menuComboBox.getSelectionModel().clearSelection();

        menuComboBox.setPromptText("choose menu");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(menuComboBox);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        Label chosenMenuLabel = new Label("Chosen menu:");
        GridPane.setHalignment(chosenMenuLabel, HPos.CENTER);
        gridpane.add(chosenMenuLabel, 0, 0);

        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);


        // Listener for changing list under


        menuComboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                displayedMenu.clear();
                displayedMenu.add((Menu) newValue);
                chosenMenuLabel.setText("Chosen menu: " + ((Menu) newValue).getName());
            }
        });

        // Listener for changing text in menuComboBox when a menu is clicked in the box
        menuComboBox.setCellFactory(new Callback<ListView<Menu>, ListCell<Menu>>() {

            @Override
            public ListCell<Menu> call(ListView<Menu> p) {

                final ListCell<Menu> cell = new ListCell<Menu>() {

                    @Override
                    protected void updateItem(Menu t, boolean bln) {

                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.toString());
                        } else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });



        VBox vbox = new VBox(5);
        gridpane.add(vbox, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);

        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.VERTICAL);


        ObservableList<Dish> myDishes = currentDishes;
        ListView<Dish> dishListView = new ListView<>();
        TableView<Dish> tempDishTable = new TableView<>();
        ObservableList<Dish> data = displayedDishes;

        tempDishTable.setEditable(true);

        TableColumn firstNameCol = new TableColumn("Dish name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        TableColumn lastNameCol = new TableColumn("Price");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Dish, DoubleProperty>("price")
        );

        dishListView.getItems().addAll(myDishes);
        dishListView.setCellFactory(CheckBoxListCell.forListView(new Callback<Dish, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Dish item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            displayedDishes.add(item);
                        } else {
                            displayedDishes.remove(item);
                        }
                    }
                });
                return observable;
            }
        }));

        Button removeDish = new Button("remove Dish");
        removeDish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Dish item = tempDishTable.getSelectionModel().getSelectedItem();
                if (item != null) {
                    tempDishTable.getSelectionModel().clearSelection();
                    displayedDishes.remove(item);
                }
            }
        });

        // TODO: 05.04.2016 TRYING TO TIE <INTEGER>QUANTITY AND MENU IN AN OBSERVABLELIST

// sample data



        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<Dish, Integer>, String> column1 = new TableColumn<>("Dish name");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey().toString());
            }
        });

        ObservableList<String> cbValues = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6",
                "7", "8", "9");

        TableColumn<Map.Entry<Dish, Integer>, String> column2 = new TableColumn<>("Amount");
        column2.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), cbValues));
        // This is where the magic happens
        column2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Map.Entry<Dish, Integer>, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Map.Entry<Dish, Integer>, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(Integer.parseInt(event.getNewValue()));
            }
        });


        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }

        });
        column2.setEditable(true);


        ObservableList<Map.Entry<Dish, Integer>> dishObservableList = FXCollections.observableArrayList(dishMap.entrySet());
        final TableView<Map.Entry<Dish, Integer>> dishesInChosenMenu = new TableView<>(dishObservableList);
        Button applyButton = new Button("Apply");

        // dette tar KUN å printer ut ting som allerede er endret! Knappen gjør ingenting ellers.
        applyButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                for (Map.Entry<Dish, Integer> entry : dishMap.entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue());
                }
            }
        });


        dishesInChosenMenu.getColumns().setAll(column1, column2);
        dishesInChosenMenu.setEditable(true);
        dishesInChosenMenu.setPrefSize(200, 150);
        dishesInChosenMenu.getColumns().get(0).setPrefWidth(100);
        dishesInChosenMenu.getColumns().get(1).setPrefWidth(100);

        tempDishTable.setItems(data);
        tempDishTable.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox3 = new VBox();
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 0, 0, 10));
        vbox3.getChildren().addAll(tempDishTable);

        VBox vbox2 = new VBox(5);
        vbox2.getChildren().addAll(applyButton, dishesInChosenMenu);

        gridpane.add(vbox2, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);

        HBox upper = new HBox(menuComboBox, dishListView);

        HBox lower = new HBox(gridpane, vbox2, vbox3);

        splitPane2.getItems().add(upper);
        splitPane2.getItems().add(lower);

        StackPane root = new StackPane(splitPane2);
        root.getChildren().addAll();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 350, 250, Color.WHITE);
        splitPane2.prefWidthProperty().bind(scene.widthProperty());
        splitPane2.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
