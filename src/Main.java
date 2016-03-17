/**
 * Created by roger on 10.03.2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javax.swing.JOptionPane.*;

public class Main extends Login {

    // Currently using a login template I stole and made some changes to. Guide below.
/*
    @Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
        if ("Driver".equals(userChoice)) {
            Parent root = FXMLLoader.load(getClass().getResource("Driver/DriverWindow.fxml"));
            primaryStage.setTitle("Healthy Catering - Driver");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("Testvindu.fxml"));
            primaryStage.setTitle("Healthy Catering - Test");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        }

	} */

    /*
     * Guide to logging in:
     * Two possible username/password combinations: "Driver" and "Driver123" or "Test" and "Test123"
     */

    public static void main(String[] args) {
        launch(args);
    }
}
