/**
 * Created by roger on 10.03.2016.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("Testvindu.fxml"));
		primaryStage.setTitle("Healthy Catering");
		primaryStage.setScene(new Scene(root, 800, 600));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
