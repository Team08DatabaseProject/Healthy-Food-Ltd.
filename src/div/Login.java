package div; /**
 * Created by axelkvistad on 3/17/16.
 * Stole the template from a website
 */
import classpackage.*;
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

/**
 *
 * http://zoranpavlovic.blogspot.com/
 */
public class Login extends Application {

    String checkUser, checkPw;
    SqlQueries query = new SqlQueries();
    private static final int CEO = 1;
    private static final int CHEF = 2;
    private static final int DRIVER = 3;
    private static final int SALES = 4;
    private static final int NUTRITION = 5;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Healthy Catering System Login");

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10,50,50,50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20,20,20,30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20,20,20,20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        Button btnLogin2 = new Button("Login2");
        final Label lblMessage = new Label();

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 2, 1);
        gridPane.add(lblMessage, 1, 2);


        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.7f);
        gridPane.setEffect(r);

        //DropShadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Healthy Catering System");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        text.setEffect(dropShadow);

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");

        //Action for btnLogin
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                try {
                    /* Commented out user check against database and creates test user
                    checkUser = txtUserName.getText().toString();
                    checkPw = pf.getText().toString();
                    Employee emp = query.getUser(checkUser, checkPw);
                    */
                    Employee emp = new Employee(1, "test", "test", "test", 1234, "test", 1234.56,
                             "hash", new Address("Testveien 1", new ZipCode(1234, "testZip")), new EmployeePosition(1, "test", 23.34));
                    if (emp != null) {
                        switch(emp.getPosition().getId()) {
                            case CEO : {
                                Parent root = FXMLLoader.load(getClass().getResource("../users/ceo/CEOWindow.fxml"));
                                primaryStage.setTitle("Healthy Catering - users.ceo");
                                primaryStage.setScene(new Scene(root, 800, 600));
                                primaryStage.show();
                                break;
                            }
                            case CHEF : {
                                break;
                            }
                            case DRIVER : {
                                Parent root = FXMLLoader.load(getClass().getResource("users/driver/DriverWindow.fxml"));
                                primaryStage.setTitle("Healthy Catering - users.driver");
                                primaryStage.setScene(new Scene(root, 800, 600));
                                primaryStage.show();
                                break;
                            }
                            case SALES : {
                                Parent root = FXMLLoader.load(getClass().getResource("users/sales/SalesWindow.fxml"));
                                primaryStage.setTitle("Healthy Catering - users.sales");
                                primaryStage.setScene(new Scene(root, 800, 600));
                                primaryStage.show();
                                break;
                            }
                            case NUTRITION : {
                                break;
                            }
                            default : {
                                System.out.println("Error during login. Exiting system.");
                                System.exit(0);
                            }
                        }
                    } else {
                        lblMessage.setText("Incorrect user or pw.");
                        lblMessage.setTextFill(Color.RED);
                    }
                    txtUserName.setText("");
                    pf.setText("");
                } catch (Exception e) {
                    System.out.println("asdf" + e);
                }
            }
        });



        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("div/login.css").toExternalForm());
        primaryStage.setScene(scene);
        /*
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        //primaryStage.setResizable(false);
        */
        primaryStage.show();
    }
}