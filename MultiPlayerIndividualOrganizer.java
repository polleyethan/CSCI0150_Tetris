package Tetris;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Organizes each individual screen on the multiplayer game
 *
 */
public class MultiPlayerIndividualOrganizer {
	
	private BorderPane _root;
	private Game _game;
	
	/**
	 * Constructor
	 */
	public MultiPlayerIndividualOrganizer(Game game) {
		_game = game;
		_root = new BorderPane();
		_root.setCenter(_game.getRootPane());
		Label playername = new Label(_game.getPlayer().getName());
		HBox topBox = new HBox(playername);
		topBox.setAlignment(Pos.CENTER);
		_root.setTop(topBox);
	}
	
	/**
	 * accessor
	 */
	public Game getGame() {
		return _game;
	}
	
	/**
	 * accessor
	 */
	public Pane getRootPane() {
		return _root;
	}
	
	/**
	 * updates game
	 */
	public void update() {
		_game.update();
	}


}
