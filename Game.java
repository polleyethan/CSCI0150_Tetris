package Tetris;

import java.util.*;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * Abstract Game Class. Controls all game elements.
 *
 */
public abstract class Game {

	protected Pane _gamePane;
	protected HashMap<Coordinate,TetrisSquare> _squares;
	protected TetrisPiece _currentPiece;
	protected Timeline _timeline;
	protected Boolean _isOver;
	protected int[][] _board;
	protected Player _player;
	protected Boolean _isPaused;
	protected GameOrganizer _organizer;
	
	/**
	 * Constructor
	 */
	public Game(Player p, Timeline t, GameOrganizer organizer) {
		_timeline = t;
		_player = p;
		_organizer = organizer;
		this.createGame();
		_isOver = false;
		_timeline.play();
		_isPaused = false;
	}

	/**
	 * Creates the game
	 */
	protected void createGame() {
		this.createGamePane();
		this.createBoard();
		this.createNewPiece();
		_squares = new HashMap<Coordinate,TetrisSquare>();
		
	}

	
	/**
	 * Creates the gamepane
	 */
	protected void createGamePane() {
		
		_gamePane = new Pane();
		
		// Ensure all of the heights and widths are constant
		_gamePane.setPrefHeight(Constants.GAME_PANE_HEIGHT);
		_gamePane.setMinHeight(Constants.GAME_PANE_HEIGHT);
		_gamePane.setMaxHeight(Constants.GAME_PANE_HEIGHT);
		_gamePane.setPrefWidth(Constants.GAME_PANE_WIDTH);
		_gamePane.setMinWidth(Constants.GAME_PANE_WIDTH);
		_gamePane.setMaxWidth(Constants.GAME_PANE_WIDTH);

		for(int i=0; i< Constants.NUM_ROWS+1; i++) {
			Line l = new Line(0,Constants.ROW_HEIGHT*i,Constants.GAME_PANE_WIDTH,Constants.ROW_HEIGHT*i);
			l.setFill(Color.BLACK);
			_gamePane.getChildren().add(l);
		}
		
		for(int i=0; i< Constants.NUM_COLS+1; i++) {
			Line l = new Line(Constants.COL_WIDTH*i,0,Constants.COL_WIDTH*i,Constants.GAME_PANE_HEIGHT);
			l.setFill(Color.BLACK);
			_gamePane.getChildren().add(l);
		}
	}
	
	/**
	 * Creates the board
	 */
	protected void createBoard() {
		_squares = new HashMap<Coordinate,TetrisSquare>();
		_board = new int[Constants.NUM_ROWS][Constants.NUM_COLS];
		for(int y=0; y<Constants.NUM_ROWS; y++) {
			for(int x=0; x<Constants.NUM_COLS; x++) {
				_board[y][x] = 0;
			}
		}
	}
	
	
	/**
	 * Creates a new piece
	 */
	protected void createNewPiece() {
		int piecetype = (int)(7*Math.random());
		
		_currentPiece = new TetrisPiece(PieceShape.values()[piecetype], _gamePane);
		
	}
	
	/**
	 * Checks if current piece can move down
	 */
	public Boolean currentCanMoveDown(int yOffset, ArrayList<TetrisSquare> squares) {
			for(TetrisSquare s :squares) {
					if(!this.squareCanMoveDown(s.getX(), s.getY(), yOffset)) {
						return false;
				}
			}
		return true;
		
	}
	
	
	/**
	 * Checks if a square can move down
	 */
	public Boolean squareCanMoveDown(int x, int y, int amount) {
				if(this.squareIsAtBottom(y, amount)) {
					return false;
				}
				else if(this.squareWillCollide(x, y, amount)) {
					return false;
			}else{ 
				return true;
			}
	
	}
	
	/**
	 * Checks if a square is at the bottom of the board
	 */
	public Boolean squareIsAtBottom(int y, int amount) {
		if(y+amount>=Constants.NUM_ROWS) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Checks if a square can move down a certain number of y spaces
	 */
	public Boolean squareWillCollide(int x, int y, int amount) {
			if(coordinateIsOccupied(x,y+amount)) {
				return true;
			}else {
				return false;
			}
	}
	
	
	/**
	 * Sets the current piece
	 */
	public void setPiece() {
		
		for(TetrisSquare s : _currentPiece.getSquares()) {
			if(!s.isDrawn()) {
				this.gameOver();
				break;
			}else {
				_board[s.getY()][s.getX()] = 1;
				_player.addToScore(5);
				_squares.put(s.getCoord(),s);
			}
			
		}
		this.createNewPiece();
		this.checkCleared();
	}
	

	/**
	 * Checks for cleared rows
	 */
	private void checkCleared() {
		ArrayList<Integer> rows = new ArrayList<Integer>();
		for(int y= 0; y<Constants.NUM_ROWS; y++) {
			if(this.isRowFull(y)) {
				rows.add(y);
				this.clearAndMoveDown(y);
			}
		}
		
		switch(rows.size()){
			case 0:
				break;
			case 1:
				_player.addToScore(400);
				break;
			case 2:
				_player.addToScore(1000);
				break;
			case 3:
				_player.addToScore(3000);
				break;
			case 4:
				_player.addToScore(12000);
				break;
		}
		
	}
	
	
	/**
	 * Clears a row and moves it down
	 */
	private void clearAndMoveDown(int row) {
		for(int x=0;x<Constants.NUM_COLS; x++) {
			if(_squares.containsKey(new Coordinate(x,row))) {
				_squares.get(new Coordinate(x,row)).remove(_gamePane);
				_squares.remove(new Coordinate(x,row));
				_board[row][x] = 0;
			}
			for(int y=row;y>0; y--) {
				if(_squares.containsKey(new Coordinate(x,y))) {
					_squares.get(new Coordinate(x,y)).moveDown();
					_squares.get(new Coordinate(x,y+1)).redraw();
					_board[y][x] = 0;
					_board[y+1][x] = 1;
				}
			}
		}
	}
	/**
	 * checks if a row is full
	 */
	private Boolean isRowFull(int row) {
		for(int x=0; x<Constants.NUM_COLS; x++) {
			if(!coordinateIsOccupied(x,row)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Toggles the games pause
	 */
	public void togglePause() {
		if(_isPaused) {
			this.play();
		}
		else {
			this.pause();
		}
	}
	
	/**
	 * plays the game
	 */
	public void play() {
		_isPaused = false;
		_timeline.play();
	}
	
	/**
	 * pauses the game
	 */
	public void pause() {
		_isPaused = true;
		_timeline.pause();
	}
	
	/**
	 * accessor
	 */
	public Boolean isPaused() {
		return _isPaused;
	}
	
	
	/**
	 * Checks if a coordinate is occupied
	 */
	public Boolean coordinateIsOccupied(int x, int y) {
		return _squares.containsKey(new Coordinate(x,y));
	}
	/**
	 * accessor
	 */
	public TetrisPiece getCurrentPiece() {
		return _currentPiece;
	}
	
	/**
	 * accessor
	 */
	public int[][] getBoard(){
		return _board;
	}

	
	
	/**
	 * checks if squares can move right
	 */
	public Boolean canMoveRight(ArrayList<TetrisSquare> squares) {
		for(TetrisSquare s :squares) {
			if(s.getX()< Constants.NUM_COLS-1) {
				if(s.isDrawn()) {
					if(_board[s.getY()][s.getX()+1] == 1) {
						return false;
					}
				}
			}else {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks if squares can move left
	 */
	public Boolean canMoveLeft(ArrayList<TetrisSquare> squares) {
		for(TetrisSquare s :squares) {
				if(s.getX()>0) {
					if(s.isDrawn()) {
						if(_board[s.getY()][s.getX()-1] == 1) {
							return false;
						}
					}
				}else {
					return false;
				}
		}
		return true;
	}
	

	
	
	/**
	 * checks if squares can rotate 
	 */
	public Boolean squarescanRotate(ArrayList<TetrisSquare> squares) {
		int xAxel = squares.get(1).getX();
		int yAxel = squares.get(1).getY();
		
		for(TetrisSquare s : squares) {
			int newX = xAxel - yAxel + s.getY();
			int newY = yAxel + xAxel - s.getX();
			if(s.isDrawn()) {
				if(newY>0 && newY<Constants.NUM_ROWS-1 && newX>0 &&  newX<Constants.NUM_COLS) {
					if(_board[newY][newX]==1) {
						return false;
					}
				}else {
					return false;
				}
			}else if(newX<0 &&  newX>Constants.NUM_COLS) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * checks if a piece can rotate
	 */
	public Boolean canRotate(PieceShape shape, ArrayList<TetrisSquare> squares) {
		if(shape == PieceShape.TWO) {
			return false;
		}
		else if(!this.squarescanRotate(squares)){
			return false;
		}
		else {
			return true;
		}
	}
	
	
	/**
	 * accessor
	 */
	public Timeline getTimeline() {
		return _timeline;
	}
	
	/**
	 * checks if the game is over
	 */
	public Boolean checkGameOver() {
		for(int x=0; x<Constants.NUM_COLS; x++) {
			if(coordinateIsOccupied(x,0)) {
				return true;
			}
		}
		for(TetrisSquare s : _currentPiece.getSquares()) {
			if(!s.isDrawn()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * called when the game ends
	 */
	protected void gameOver() {
		System.out.println("over");
		_isOver = true;
		_timeline.stop();
		_organizer.onEnd(_player);
	}
	
	
	/**
	 * abstract method to update the game
	 */
	public abstract void update();

	
	/**
	 * accessor
	 */
	public Player getPlayer() {
		return _player;
	}

	/**
	 * accessor
	 */
	public Boolean isOver() {
		return _isOver;
	}
	/**
	 * accessor
	 */
	public Pane getRootPane() {
		return _gamePane;
	}

	
	
}
