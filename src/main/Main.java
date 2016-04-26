package main; /**
 * Created by axelkvistad on 3/17/16.
 */
import classpackage.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Optional;

public class Main extends Application {

    protected static SqlQueries db = new SqlQueries();
    protected static Employee loggedInEmp;
    protected static BorderPane rootBorderPane;

    @Override
    public void start(Stage primaryStage) {
        try {
            boolean loggedIn = false;
            while(!loggedIn) {
                PopupDialog dialog = new PopupDialog();
                Optional<Pair<String, String>> loginFields = dialog.loginDialog();
                if(loginFields.isPresent()) {
                    loggedInEmp = db.getEmployeeByUsername(loginFields.get().getKey());
                    if(loggedInEmp == null || !loggedInEmp.getPassHash().equals(hashPassword(loginFields.get().getValue()))) {
                        PopupDialog.errorDialog("Error", "Wrong username and/or password. Please try again.");
                    } else if(loggedInEmp.getPassHash().equals(hashPassword(loginFields.get().getValue()))) {
                        loggedIn = true;
                    }
                } else {
                    System.exit(0);
                }
            }
            primaryStage.setTitle("Healthy Catering System. Logged in as: " + loggedInEmp.getUsername() + " - " + loggedInEmp.getPosition().getDescription());
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(root, 1200, 800);
            rootBorderPane = (BorderPane) scene.lookup("#rootPane");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedPassword = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte b : hashedPassword) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            PopupDialog.errorDialog("Error", "Something went wrong during user authentication.");
        }
        return null;
    }


    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }
}