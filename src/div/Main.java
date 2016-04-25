package div;//import classpackage.EncryptionService;


import java.util.Locale;

/**
 * Created by roger on 10.03.2016.
 */

public class Main extends Login {



    // Currently using a login template I stole and made some changes to. Guide below.
/*
    @Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
        if ("users.driver".equals(userChoice)) {
            Parent root = FXMLLoader.load(getClass().getResource("users.driver/DriverWindow.fxml"));
            primaryStage.setTitle("Healthy Catering - users.driver");
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
     * Two possible username/password combinations: "users.driver" and "Driver123" or "Test" and "Test123"
     */

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }
}
