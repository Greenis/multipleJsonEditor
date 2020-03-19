package org.me;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.me.controller.GlobalController;

import java.io.IOException;

public class App extends Application {

	public static final String title = "Multiple::Editor";

	public static Stage getStage() {
		return __Stage;
	}

	private static Stage __Stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		__Stage = stage;
		stage.setTitle(title);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icon.png")));
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 600);
		stage.setScene(scene);
		stage.show();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new GlobalController());
		Parent adding = loader.load(getClass().getResource("/view/app.fxml"));
		root.setMinWidth(800);
		stage.setMinHeight(400);
		root.setCenter(adding);
	}

}
