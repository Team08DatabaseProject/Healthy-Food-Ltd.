/**
 * Created by roger on 10.03.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javax.swing.JOptionPane.*;

public class Main extends Application {
    private static String userChoice = ""; // Temporary way to choose which employee-type window appears.
                                            // Will be replaced with proper login window once we figure that out.

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
        if ("Driver".equals(userChoice)) {
            Parent root = FXMLLoader.load(getClass().getResource("Driver/DriverWindow.fxml"));
            primaryStage.setTitle("Healthy Catering");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("Testvindu.fxml"));
            primaryStage.setTitle("Healthy Catering");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        }

	}

	public static void main(String[] args) {
        userChoice = showInputDialog("Choose a user type");
        launch(args);
	}
}
