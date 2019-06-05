package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utils.Colorify;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		setupMain(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void setupMain(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
			VBox root = loader.load();
			Scene scene = new Scene(root, 700, 400);
			scene.getStylesheets().add(getClass().getResource("/view/bootstrap3.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setMinWidth(700);
			primaryStage.setMinHeight(400);
			primaryStage.setMaxWidth(700);
			primaryStage.setMaxHeight(400);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/view/images/icon.png")));
			primaryStage.setTitle("Colorify");

			// Completely closes the application and all threads.
			primaryStage.setOnCloseRequest(e -> {
				Platform.exit();
				System.exit(0);
			});
			primaryStage.show();

			// Read colors from JSON on start to avoid doing it every click. (STATIC FILES)
			Colorify c = new Colorify();
			c.readColors();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
