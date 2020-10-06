package Tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Instantiate Menu Organizer and show it in the scene
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {
		MenuOrganizer organizer = new MenuOrganizer(stage);
		Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
		stage.setScene(scene);
		stage.show();
	}

	/*
	 * Here is the mainline! No need to change this.
	 */
	public static void main(String[] argv) {
		// launch is a static method inherited from Application.
		launch(argv);
	}
}
