package Tetris;

import java.util.ArrayList;

import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Subclass of game. Single Player
 *
 */
public class SinglePlayerGame extends Game{
	
	

	/**
	 * Constructor
	 */
	public SinglePlayerGame(Player p, Timeline t, GameOrganizer organizer){
		super(p,t, organizer);
	}
	
	/**
	 * Updates Game
	 */
	public void update() {

		if(this.currentCanMoveDown(1, _currentPiece.getSquares())) {
			_currentPiece.moveDown();
			_currentPiece.update();
		}else {
			if(this.checkGameOver()) {
				this.gameOver();
			}else {
				this.setPiece();
			}
		}
		_gamePane.requestFocus();
	}



}
