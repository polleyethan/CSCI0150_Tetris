package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Organizes the menu page
 *
 */
public class MenuOrganizer {

	private Stage _stage;
	private BorderPane _root;
	
	/**
	 * constructor
	 */
	public MenuOrganizer(Stage stage) {
		_stage = stage;
		this.setupView();
	}
	
	/**
	 * sets up the view and all the buttons
	 */
	private void setupView() {
		_root = new BorderPane();
		
		Button singleplayergame = new Button("Single Player");
		
		singleplayergame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SinglePlayerOrganizer organizer = new SinglePlayerOrganizer(_stage, new Human("Player 1"));
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});

		
		Button multiplayergame = new Button("MultiPlayer");
		
		multiplayergame.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MultiPlayerOrganizer organizer = new MultiPlayerOrganizer(_stage, new Human("Player 1"), new Human("Player 2"));
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});
		
		Button playversusAI = new Button("Play Vs. AI");
		
		playversusAI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AIGenome ai = new AIGenome();
				MultiPlayerOrganizer organizer = new MultiPlayerOrganizer(_stage, new Human("Player 1"), ai);
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});
		
		Button watchAIplay = new Button("Watch AI Play");
		
		watchAIplay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				WatchAIOrganizer organizer = new WatchAIOrganizer(_stage);
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});
		
		Button trainAI = new Button("Train AI");
		
		trainAI.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				AIOrganizer organizer = new AIOrganizer(_stage, 100, 20);
				Scene scene = new Scene(organizer.getRootPane(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});
		
		Button quit = new Button("Quit");
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		
		VBox buttons = new VBox();
		
		buttons.getChildren().addAll(singleplayergame,multiplayergame,playversusAI,watchAIplay,trainAI, quit);
		
		
		_root.setCenter(buttons);
	}
	
	/**
	 * accessor
	 */
	public Pane getRoot() {
		return _root;
	}
}
