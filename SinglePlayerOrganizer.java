package Tetris;



import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Subclass of game organizer
 *
 */
public class SinglePlayerOrganizer extends GameOrganizer {

	private Game _game;
	private BorderPane _root;
	private Button _pause;
	
	/**
	 * Constructor
	 */
	public SinglePlayerOrganizer(Stage stage, Player p) {
		super(stage);
		this.createTimeline(Constants.TIMELINE_DELAY);
		_game = new SinglePlayerGame(p,_timeline, this);
		this.createGames();
	}
	
	
	/**
	 * Creates the games
	 */
	protected void createGames() {
		_root = new BorderPane();
		_root.setCenter(_game.getRootPane());
		
		_root.requestFocus();
		
		_root.setOnKeyPressed(new KeyHandler(_game));
		
	}
	

	/**
	 * updates game
	 */
	protected void update() {
		_game.update();
		
	}
	
	/**
	 * accessor
	 */
	public Pane getRootPane() {
		return _root;
	}
	



/**
 *Called when game ends
 */
@Override
protected void onEnd(Player loser) {
	_root.setTop(new Label("Game Over"));
	
}

/**
 * Single Player KeyHandler
 *
 */
private class KeyHandler implements EventHandler<KeyEvent>{
	
	private Game _game;
	public KeyHandler(Game game) {
		_game = game;
	}
	@Override
	public void handle(KeyEvent e) {
		if(!_game.isOver()) {
			switch(e.getCode()) {
				case LEFT:{
					if(_game.canMoveLeft(_game.getCurrentPiece().getSquares()) && !_game.isPaused()) {
						_game.getCurrentPiece().moveLeft();
						_game.getCurrentPiece().update();
					}
					break;
				}
				case RIGHT:{
					if(_game.canMoveRight(_game.getCurrentPiece().getSquares()) && !_game.isPaused()) {
						_game.getCurrentPiece().moveRight();
						_game.getCurrentPiece().update();
					}
					break;
				}
				case P:{
					_game.togglePause();
					break;
				}
				case UP:{
					if(_game.canRotate(_game.getCurrentPiece().getShape(),_game.getCurrentPiece().getSquares()) && !_game.isPaused()) {
						_game.getCurrentPiece().rotateCounterClock();
						_game.getCurrentPiece().update();
					}
					break;
				}
				case DOWN:{
					if(_game.currentCanMoveDown(1, _game.getCurrentPiece().getSquares()) && !_game.isPaused()) {
						_game.getCurrentPiece().moveDown();
						_game.getCurrentPiece().update();
						break;
					}
				}
				case SPACE:{
					while(_game.currentCanMoveDown(1, _game.getCurrentPiece().getSquares()) && !_game.isPaused()) {
						_game.getCurrentPiece().moveDown();
						_game.getCurrentPiece().update();
					}
				}
			default:
				break;
				}
		}
		e.consume();
	}
	
}


}
