package Tetris;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract GameORganizer Class. Organizes game pane
 *
 */
public abstract class GameOrganizer {

	protected Stage _stage;
	protected Timeline _timeline;
	
	/**
	 * Constructor
	 */
	public GameOrganizer(Stage stage) {
		_stage = stage;
	}
	 /**
	 * Creates timeline
	 */
	protected void createTimeline(double dur){
			KeyFrame kf = new KeyFrame(Duration.seconds(dur), new TimeHandler());
			_timeline = new Timeline(kf);
			_timeline.setCycleCount(Animation.INDEFINITE);
		}
	
	/**
	 * creates the games
	 */
	protected abstract void createGames();
	
	/**
	 * called when game ends
	 */
	protected abstract void onEnd(Player loser);

	/**
	 * updates game
	 */
	protected abstract void update();
	
	/**
	 * accessor
	 */
	public abstract Pane getRootPane();
	
	

/**
 * Timehandler to call when timeline updates
 *
 */
private class TimeHandler implements EventHandler<ActionEvent> {

	public void handle(ActionEvent event) {
		update();
	}
}
}
