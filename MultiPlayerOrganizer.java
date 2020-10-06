package Tetris;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Extends Game Organizer. Used when more than one player is playing
 *
 */
public class MultiPlayerOrganizer extends GameOrganizer {

	private MultiPlayerIndividualOrganizer _game1Organizer;
	private MultiPlayerIndividualOrganizer _game2Organizer;
	private Player _p1;
	private Label _p1Score;
	private Player _p2;
	private Label _p2Score;
	private Stage _s;
	private Stage _s2;
	private Pane _root;
	private Button _pause;
	private Boolean _isPaused;
	private Boolean _isOver;
	
	/**
	 * Constructor
	 */
	public MultiPlayerOrganizer(Stage stage, Player p1, Player p2) {
		super(stage);
		_p1 = p1;
		_p2 = p2;
		this.createTimeline(Constants.TIMELINE_DELAY);
			_game1Organizer = new MultiPlayerIndividualOrganizer(new SinglePlayerGame(p1,_timeline, this));
		if(p2 instanceof AIGenome) {
			_game2Organizer = new MultiPlayerIndividualOrganizer(new AIGame(_timeline,(AIGenome)p2, this, false, 0));
		}else {
			_game2Organizer = new MultiPlayerIndividualOrganizer(new SinglePlayerGame(p2,_timeline, this));
		}
		_isOver = false;
		_isPaused = false;
		this.createGames();
		this.createButtons();
		
	}

	/**
	 * creates the games and the game screens
	 */
	protected void createGames() {
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		
		_s = new Stage();
		Scene sscene = new Scene(_game1Organizer.getRootPane(), Constants.GAME_PANE_WIDTH, Constants.GAME_PANE_HEIGHT);
		_s.setScene(sscene);
		_s2 = new Stage();
		Scene s2scene = new Scene(_game2Organizer.getRootPane(), Constants.GAME_PANE_WIDTH, Constants.GAME_PANE_HEIGHT);
		_s2.setScene(s2scene);

		_s.initOwner(_stage);
		_s2.initOwner(_stage);
		
		_s.setX(primaryScreenBounds.getMinX());
		_stage.setX(2*primaryScreenBounds.getWidth()/5);
		_s2.setX(3*primaryScreenBounds.getWidth()/5);
		
		_s.setY(primaryScreenBounds.getMinY());
		_stage.setY(primaryScreenBounds.getMinY());
		_s2.setY(primaryScreenBounds.getMinY());
		
		_s.setWidth(2*primaryScreenBounds.getWidth()/5);
		_stage.setWidth(primaryScreenBounds.getWidth()/5);
		_s2.setWidth(2*primaryScreenBounds.getWidth()/5);
		
		
		_s.setHeight(primaryScreenBounds.getHeight());
		_stage.setHeight(primaryScreenBounds.getHeight());
		_s2.setHeight(primaryScreenBounds.getHeight());
		
		
		if(_game2Organizer.getGame() instanceof AIGame) {
			AIKeyHandler keyHandler = new AIKeyHandler(_game1Organizer.getGame(), _game2Organizer.getGame());
			_stage.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
			_s.addEventHandler(KeyEvent.KEY_PRESSED,  keyHandler);
			_s2.addEventHandler(KeyEvent.KEY_PRESSED,  keyHandler);
		}else {
			MultiPersonKeyHandler keyHandler = new MultiPersonKeyHandler(_game2Organizer.getGame(),_game1Organizer.getGame());
			_stage.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler);
			_s.addEventHandler(KeyEvent.KEY_PRESSED,  keyHandler);
			_s2.addEventHandler(KeyEvent.KEY_PRESSED,  keyHandler);
		}
		

		_s.show();
		_s2.show();
		
	
	}
	
	/**
	 * creates the buttons
	 */
	private void createButtons() {
		Label p1ScoreHead = new Label(_p1.getName()+" score:");
		Label p2ScoreHead = new Label(_p2.getName()+" score:");
		_p1Score = new Label(_p1.getScore()+"");
		_p2Score = new Label(_p2.getScore()+"");

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
				_s.close();
				_s2.close();
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
		
		_root = new VBox(p1ScoreHead,_p1Score,p2ScoreHead,_p2Score,_pause,backToHome,quit);
	}
	

	/**
	 * updates the games
	 */
	protected void update() {
		_s.requestFocus();
		_s2.requestFocus();
		if(!_isOver && !_isPaused) {
			_p1Score.setText(_p1.getScore()+"");
			_p2Score.setText(_p2.getScore()+"");
			_game1Organizer.update();
			_game2Organizer.update();
		}
		
	}
	
	/**
	 * toggles pause for games
	 */
	protected void togglePause() {
		_game1Organizer.getGame().togglePause();
		_game2Organizer.getGame().togglePause();
		if(_isPaused) {
			_isPaused = false;
			_timeline.play();
			_pause.setText("Pause");
			
		}
		else {
			_isPaused = true;
			_timeline.pause();
			_pause.setText("Play");
		}
	}
	
	

	/**
	 * accessor
	 */
	public Pane getRootPane() {
			return _root;
	}
	
	/**
	 * Key Handler for an AIGame
	 *
	 */
	private class AIKeyHandler implements EventHandler<KeyEvent>{
		
		private Game _game1;
		private Game _game2;
		public AIKeyHandler(Game game, Game game2) {
			_game1 = game;
			_game2 = game2;
		}
		@Override
		public void handle(KeyEvent e) {
			if(!_game1.isOver() && !_game2.isOver()) {
				switch(e.getCode()) {
					case LEFT:{
						if(_game1.canMoveLeft(_game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveLeft();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case RIGHT:{
						if(_game1.canMoveRight(_game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveRight();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case P:{
						togglePause();
						break;
					}
					case UP:{
						if(_game1.canRotate(_game1.getCurrentPiece().getShape(), _game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().rotateCounterClock();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case DOWN:{
						if(_game1.currentCanMoveDown(1, _game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveDown();
							_game1.getCurrentPiece().update();
						}
						if(_game2.currentCanMoveDown(1, _game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveDown();
							_game2.getCurrentPiece().update();
						}
						break;
					}
					case SPACE:{
						while(_game1.currentCanMoveDown(1, _game1.getCurrentPiece().getSquares())&& !_isPaused) {
							_game1.getCurrentPiece().moveDown();
							_game1.getCurrentPiece().update();
						}
						while(_game2.currentCanMoveDown(1, _game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveDown();
							_game2.getCurrentPiece().update();
						}
						
						
					}
				default:
					break;
					}
			}
			e.consume();
		}
	}
	
/**
 * KeyHandler for a two person game
 *
 */
private class MultiPersonKeyHandler implements EventHandler<KeyEvent>{
		
		private Game _game1;
		private Game _game2;
		public MultiPersonKeyHandler(Game game, Game game2) {
			_game1 = game;
			_game2 = game2;
		}
		@Override
		public void handle(KeyEvent e) {
			if(!_isOver) {
				switch(e.getCode()) {
					case LEFT:{
						if(_game1.canMoveLeft(_game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveLeft();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case RIGHT:{
						if(_game1.canMoveRight(_game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveRight();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case P:{
						togglePause();
						break;
					}
					case UP:{
						if(_game1.canRotate(_game1.getCurrentPiece().getShape(),_game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().rotateCounterClock();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case DOWN:{
						if(_game1.currentCanMoveDown(1, _game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveDown();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case SPACE:{
						while(_game1.currentCanMoveDown(1, _game1.getCurrentPiece().getSquares()) && !_isPaused) {
							_game1.getCurrentPiece().moveDown();
							_game1.getCurrentPiece().update();
						}
						break;
					}
					case W:{
						if(_game2.canRotate(_game2.getCurrentPiece().getShape(),_game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().rotateCounterClock();
							_game2.getCurrentPiece().update();
						}
						
						break;
					}
					case A:{
						if(_game2.canMoveLeft(_game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveLeft();
							_game2.getCurrentPiece().update();
						}
						
						break;
					}
					case S:{
						if(_game2.currentCanMoveDown(1, _game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveDown();
							_game2.getCurrentPiece().update();
						}
						break;
					}
					case D:{
						if(_game2.canMoveRight(_game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveRight();
							_game2.getCurrentPiece().update();
						}
						break;
					}
					case TAB:{
						while(_game2.currentCanMoveDown(1, _game2.getCurrentPiece().getSquares()) && !_isPaused) {
							_game2.getCurrentPiece().moveDown();
							_game2.getCurrentPiece().update();
						}
						break;
					}
				default:
					break;
					}
			}
			e.consume();
		}
	}

	/**
	 * Called when game ends
	 */
	@Override
	protected void onEnd(Player loser) {
		_isOver = true;
		_root.getChildren().add(new Label("Game Over"));
		
		
	}

}
