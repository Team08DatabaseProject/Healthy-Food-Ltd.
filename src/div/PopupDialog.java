package div;

import classpackage.Employee;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

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
		alert.setTitle("Email sent");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void newPasswordEmail(Employee employee, String password) {
		String content = "Dear " + employee.getFirstName() + " " + employee.getLastName() + "\n\n" +
						"An administrator has changed your password in the Healthy Catering Ltd. System\nYour new password is as follows:\n\n" +
						"Password: " + password;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Email sent");
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
