package main;

import classpackage.Employee;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by HUMBUG on 26.04.2016.
 */
public class MainController extends Main implements Initializable {

	@FXML
	HBox topMenuHBox;
    public BorderPane rootPane;

	// Constants for views
	private final int ORDERS = 1;
	private final int MENUS = 2;
	private final int DISHES = 3;
	private final int INGREDIENTS = 4;
	private final int EMPLOYEES = 5;
	private final int DRIVER = 6;
	private final int INVENTORY = 7;
	private final int SALES = 8;
	private final int CUSTOMERS = 9;
	private int currentView;

	protected static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy \n HH:mm");



	private Button createOrdersButton() {
		ImageView icon = new ImageView("/icons/Numbered_List-48.png");
		Button button = new Button("Orders", icon);
		button.getStyleClass().add("TopButton");
		button.setId("ordersButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != ORDERS) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/orders/Orders.fxml"));
						GridPane inventoryPane = loader.load();
						rootBorderPane.setCenter(inventoryPane);
						currentView = ORDERS;
					}
				} catch(IOException e) {
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createMenusButton() {
		ImageView icon = new ImageView("/icons/Meal-48.png");
		Button button = new Button("Menus", icon);
		button.getStyleClass().add("TopButton");
		button.setId("menusButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != MENUS) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/menus/Menus.fxml"));
						GridPane menusPane = loader.load();
						rootBorderPane.setCenter(menusPane);
						currentView = MENUS;
					}
				} catch(IOException e) {
					e.printStackTrace();
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createDishesButton() {
		ImageView icon = new ImageView("/icons/Noodles-48.png");
		Button button = new Button("Dishes", icon);
		button.getStyleClass().add("TopButton");
		button.setId("dishesButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != DISHES) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/dishes/Dishes.fxml"));
						GridPane menusPane = loader.load();
						rootBorderPane.setCenter(menusPane);
						currentView = DISHES;
					}
				} catch(IOException e) {
					e.printStackTrace();
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createIngredientsButton() {
		ImageView icon = new ImageView("/icons/Mushroom-48.png");
		Button button = new Button("Ingredients", icon);
		button.getStyleClass().add("TopButton");
		button.setId("ingredientsButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != INGREDIENTS) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/ingredients/Ingredients.fxml"));
						GridPane ingredientsPane = loader.load();
						rootBorderPane.setCenter(ingredientsPane);
						currentView = INGREDIENTS;
					}
				} catch(IOException e) {
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createEmployeesButton() {
		ImageView icon = new ImageView("/icons/Manager-48.png");
		Button button = new Button("Employees", icon);
		button.getStyleClass().add("TopButton");
		button.setId("employeesButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != EMPLOYEES) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/employees/Employees.fxml"));
						GridPane employeesPane = loader.load();
						rootBorderPane.setCenter(employeesPane);
						currentView = EMPLOYEES;
					}
				} catch(IOException e) {
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createDriverButton() {
		ImageView icon = new ImageView("/icons/Deliver_Food-48.png");
		Button button = new Button("Driver", icon);
		button.getStyleClass().add("TopButton");
		button.setId("driverButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != DRIVER) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/driver/Driver.fxml"));
						GridPane driverPane = loader.load();
						rootBorderPane.setCenter(driverPane);
						currentView = DRIVER;
					}
				} catch(IOException e) {
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createInventoryButton() {
		ImageView icon = new ImageView("/icons/Move_by_Trolley-48.png");
		Button button = new Button("Inventory", icon);
		button.getStyleClass().add("TopButton");
		button.setId("inventoryButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != INVENTORY) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/inventory/Inventory.fxml"));
						GridPane inventoryPane = loader.load();
						rootBorderPane.setCenter(inventoryPane);
						currentView = INVENTORY;
					}
				} catch(IOException e) {
					e.printStackTrace();
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}


	private Button createSalesButton() {
		ImageView icon = new ImageView("/icons/sales/Service_Bell.png");
		Button button = new Button("Sales", icon);
		button.getStyleClass().add("TopButton");
		button.setId("salesButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != SALES) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/sales/Sales.fxml"));
						GridPane salesPane = loader.load();
						rootBorderPane.setCenter(salesPane);
						currentView = SALES;
					}
				} catch(IOException e) {
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	private Button createCustomersButton() {
		ImageView icon = new ImageView("/icons/sales/User_Group_Woman_Woman.png");
		Button button = new Button("Customers", icon);
		button.getStyleClass().add("TopButton");
		button.setId("customersButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if(currentView != CUSTOMERS) {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(getClass().getResource("/views/customers/Customers.fxml"));
						GridPane custPane = loader.load();
						rootBorderPane.setCenter(custPane);
						currentView = CUSTOMERS;
					}
				} catch(IOException e) {
					e.printStackTrace();
					PopupDialog.errorDialog("Error", "Error generating GUI.");
				}
			}
		});
		return button;
	}

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		ObservableList<Button> topMenuButtons = FXCollections.observableArrayList();
		switch(loggedInEmp.getPosition().getId()) {
			case Employee.CEO :
				topMenuButtons.add(createEmployeesButton());
				topMenuButtons.add(createInventoryButton());
				topMenuButtons.add(createOrdersButton());
				topMenuButtons.add(createMenusButton());
				topMenuButtons.add(createDishesButton());
				topMenuButtons.add(createIngredientsButton());
				topMenuButtons.add(createDriverButton());
				topMenuButtons.add(createSalesButton());
				topMenuButtons.add(createCustomersButton());
				break;
			case Employee.CHEF :
				topMenuButtons.add(createOrdersButton());
				topMenuButtons.add(createMenusButton());
				topMenuButtons.add(createDishesButton());
				topMenuButtons.add(createIngredientsButton());
				break;
			case Employee.DRIVER :
				topMenuButtons.add(createDriverButton());
				break;
			case Employee.SALES :
				topMenuButtons.add(createInventoryButton());
      	topMenuButtons.add(createSalesButton());
        topMenuButtons.add(createCustomersButton());
				break;
			case Employee.NUTRITION :
				topMenuButtons.add(createMenusButton());
				topMenuButtons.add(createDishesButton());
				topMenuButtons.add(createIngredientsButton());
				break;
		}
		topMenuHBox.getChildren().addAll(topMenuButtons);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootPane.requestFocus();
            }
        });
	}
}
