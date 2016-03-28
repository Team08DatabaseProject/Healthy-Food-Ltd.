//import classpackage.EncryptionService;
import classpackage.SqlQueries;

import java.util.Map;

/**
 * Created by roger on 10.03.2016.
 */

public class Main extends Login {



    // Currently using a login template I stole and made some changes to. Guide below.
/*
    @Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLLoader loader = new FXMLLoader();
        if ("driver".equals(userChoice)) {
            Parent root = FXMLLoader.load(getClass().getResource("driver/DriverWindow.fxml"));
            primaryStage.setTitle("Healthy Catering - driver");
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
     * Two possible username/password combinations: "driver" and "Driver123" or "Test" and "Test123"
     */

    public static void main(String[] args) {
        launch(args);
    }
}
