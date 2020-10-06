package Tetris;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Subclass of gameorganizer. User watches top AI play a game
 *
 */
public class WatchAIOrganizer extends GameOrganizer {
	
	private Game _game;
	private BorderPane _root;
	private Label _scoreLbl;
	private Button _pause;
	
	/**
	 * Constructor
	 */
	public WatchAIOrganizer(Stage stage) {
		super(stage);
		this.createTimeline(Constants.WATCH_AI_DELAY);
		this.createGames();
		
	}

	/**
	 * Creates the game
	 */
	@Override
	protected void createGames() {
		_game = new AIGame(_timeline, Constants.SUPERIOR_GENOME, this, false, 0);
		_root = new BorderPane();
		_root.setCenter(_game.getRootPane());
		Label scorehead = new Label("Score:");
		_scoreLbl = new Label(""+_game.getPlayer().getScore());
		VBox labels = new VBox(scorehead,_scoreLbl);
		_pause = new Button("Pause");
		
		_pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				togglePause();
			}
		});
		
		Button backToHome = new Button("Back To Home");
		
		backToHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MenuOrganizer organizer = new MenuOrganizer(_stage);
				Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
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
		VBox buttons = new VBox(_pause,backToHome,quit);
		_root.setLeft(buttons);
		labels.setAlignment(Pos.CENTER);
		buttons.setAlignment(Pos.CENTER);
		_root.setRight(labels);
	}
	
	
	/**
	 * Toggles the games pause
	 */
	protected void togglePause() {
		_game.togglePause();
		if(_game.isPaused()) {
			_timeline.play();
			_pause.setText("Pause");
			
		}
		else {
			_timeline.pause();
			_pause.setText("Play");
		}
	}

	/**
	 * Called when game ends
	 */
	@Override
	protected void onEnd(Player loser) {
		System.out.println("SCORE "+loser.getScore());
		System.out.println("OVER");
		_root.setTop(new Label("Game Over"));

	}

	/**
	 * updates game
	 */
	@Override
	protected void update() {
		_game.update();
		_scoreLbl.setText(""+_game.getPlayer().getScore());

	}

	/**
	 * accessor
	 */
	@Override
	public Pane getRootPane() {
		return _root;
	}

}
