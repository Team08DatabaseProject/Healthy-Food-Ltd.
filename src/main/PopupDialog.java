package main;

import classpackage.*;
import fields.DoubleField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.awt.*;
import java.util.Optional;

/**
 * Created by HUMBUG on 18.04.2016.
 */
public class PopupDialog {

	public static void errorDialog(String title, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void informationDialog(String title, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static boolean confirmationDialog(String title, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

	public static void newUserEmail(Employee employee, String password) {
		String content = "Dear " + employee.getFirstName() + " " + employee.getLastName() + "\n\n" +
						"An administrator has created an account for you in the Healthy Catering Ltd. System\nYour login details are as follows:\n\n" +
						"Username: " + employee.getUsername() + "\nPassword: " + password;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("E-mail sent");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void newPasswordEmail(Employee employee, String password) {
		String content = "Dear " + employee.getFirstName() + " " + employee.getLastName() + "\n\n" +
						"An administrator has changed your password in the Healthy Catering Ltd. System\nYour new password is as follows:\n\n" +
						"Password: " + password;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("E-mail sent");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void newPOrderEmail(POrder pOrder, Supplier supplier) {
		Dialog<Object> dialog = new Dialog<>();
		dialog.setTitle("Phone call made or e-mail sent");
		dialog.setHeaderText(null);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
		String firstHalf = "Dear " + supplier.getBusinessName() + ".\n\n" +
						"We wish to place an order for the following ingredients:";
		String secondHalf = "Grand total: " + pOrder.getGrandTotal() + "\nPurchase order ID: " + pOrder.getpOrderId() + "\n\nRegards\n" +
						"Healthy Catering Ltd.";
		GridPane grid = new GridPane();
		dialog.getDialogPane().setContent(grid);
		grid.setHgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));
		grid.add(new Label(firstHalf), 0, 0);
		TableView<POrderLine> table = new TableView<>();
		grid.add(table, 0, 1);
		grid.add(new Label(secondHalf), 0, 2);
		System.out.println(pOrder.getpOrderLines());
		TableColumn<POrderLine, Ingredient> ingCol = new TableColumn<>("Ingredient");
		TableColumn<POrderLine, Ingredient> priceCol = new TableColumn<>("Price");
		TableColumn<POrderLine, Double> quantityCol = new TableColumn<>("Quantity");
		TableColumn<POrderLine, Double> totalCol = new TableColumn<>("Total");
		ingCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		ingCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Ingredient>() {
				@Override
				protected void updateItem(Ingredient item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(item.getIngredientName());
					}
				}
			};
		});
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
		priceCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Ingredient>() {
				@Override
				protected void updateItem(Ingredient item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item.getPrice()));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
		totalCol.setCellFactory(column -> {
			return new TableCell<POrderLine, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					if(item == null || empty) {
						setText(null);
					} else {
						setText(String.format("%.2f", item));
						setAlignment(Pos.BASELINE_RIGHT);
					}
				}
			};
		});
		table.getColumns().addAll(ingCol, priceCol, quantityCol, totalCol);
		table.setItems(pOrder.getpOrderLines());
		dialog.showAndWait();
	}

	public Optional<Pair<String, String>> loginDialog() {
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Welcome to the Healthy Catering System.");
		dialog.setGraphic(new ImageView(getClass().getResource("/icons/Key-48.png").toString()));
		ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);
		username.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> username.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(username.getText(), password.getText());
			} else if(dialogButton == ButtonType.CANCEL) {
				return null;
			}
			return null;
		});
		Optional<Pair<String, String>> result = dialog.showAndWait();
		return result;
	}

	public static String StringDialog(String title, String content) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		dialog.setContentText(content);
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		} else {
			return null;
		}
	}

	public static Double doubleDialog(String title, String content) {
		Dialog<Double> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		DoubleField doubleField = new DoubleField();
		grid.add(new Label(content), 0, 0);
		grid.add(doubleField, 1, 0);
		Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
		okButton.setDisable(true);
		doubleField.textProperty().addListener((observable, oldValue, newValue) -> {
			okButton.setDisable(newValue.trim().isEmpty());
		});
		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> doubleField.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == okButtonType) {
				return doubleField.getDouble();
			}
			return null;
		});
		Optional<Double> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}


	public static Customer createOrderDialog(String title, String content, ObservableList<Customer> customerList) {
		Dialog<Customer> dialog = new Dialog<>();
		dialog.setTitle(title);
		dialog.setHeaderText(null);
		ComboBox<Customer> customerCB = new ComboBox<>();
		ButtonType existingCustomerButtonType = new ButtonType("Existing customer", ButtonBar.ButtonData.LEFT);
		ButtonType newCustomerButtonType = new ButtonType("New customer", ButtonBar.ButtonData.RIGHT);
		dialog.getDialogPane().getButtonTypes().addAll(existingCustomerButtonType, newCustomerButtonType);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 10, 10, 10));
		grid.add(new Label(content), 0, 0);
		grid.add(customerCB, 1, 0);
		Node existingButton = dialog.getDialogPane().lookupButton(existingCustomerButtonType);
		Node newButton = dialog.getDialogPane().lookupButton(newCustomerButtonType);
		existingButton.setDisable(true);
        Customer newCustomer = new Customer();


		customerCB.setItems(customerList);
		customerCB.setCellFactory(column -> {
			return new ListCell<Customer>() {
				@Override
				public void updateItem(Customer customer, boolean empty) {
					super.updateItem(customer, empty);
					if (!(customer == null || empty)) {
						setText(customer.toString());
					}
				}
			};
		});
		customerCB.valueProperty().addListener(new ChangeListener<Customer>() {
			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
					existingButton.setDisable(newValue == null);
				}
			});


		dialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> customerCB.requestFocus());
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == existingCustomerButtonType) {
				return customerCB.getValue();
			} else if (dialogButton == newCustomerButtonType) {
				return newCustomer;
			}
			return newCustomer;
		});
		Optional<Customer> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		}
		return newCustomer;
	}
}
