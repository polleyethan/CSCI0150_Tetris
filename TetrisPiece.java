package Tetris;

import java.util.ArrayList;

import javafx.scene.layout.Pane;

/**
 * Class representing a piece
 *
 */
public class TetrisPiece {
	
	private ArrayList<TetrisSquare> _squares;
	private Pane _gamePane;
	private int _rotations;
	private PieceShape _shape;
	
	
	/**
	 * Constructor
	 */
	public TetrisPiece(PieceShape shape, Pane gamepane) {
		_shape = shape;
		_squares = _shape.getSquares(Constants.NUM_COLS/2, -1);
		_gamePane = gamepane;
		_rotations = 0;
	}
	
	/**
	 * updates the piece
	 */
	public void update() {
		for(TetrisSquare s: _squares) {
			if(s.getY()>=0) {
				if(!s.isDrawn()) {
					s.createSquare(_gamePane);
				}else {
					s.redraw();
				}
			}
		}
	}
	
	
	/**
	 * accessor
	 */
	public PieceShape getShape() {
		return _shape;
	}
	
	/**
	 * moves piece down
	 */
	public void moveDown() {
		for(TetrisSquare s : _squares) {
			s.moveDown();
		}
	}
	
	/**
	 * moves piece left
	 */
	public void moveLeft() {
		for(TetrisSquare s : _squares) {
			s.moveLeft();
		}
	}
	
	/**
	 * moves piece right
	 */
	public void moveRight() {
		for(TetrisSquare s : _squares) {
			s.moveRight();
		}
	}
	
	/**
	 * rotates piece counterclockwise
	 */
	public void rotateCounterClock() {
			int xAxel = _squares.get(1).getX();
			int yAxel = _squares.get(1).getY();
			for(int i = 0; i<_squares.size(); i++) {
				int newX = xAxel - yAxel + _squares.get(i).getY();
				int newY = yAxel + xAxel - _squares.get(i).getX();	
				_squares.get(i).setX(newX);
				_squares.get(i).setY(newY);
			}
			if(_rotations<3) {
				_rotations +=1;
			}
			else {
				_rotations =0;
			}
	}
	
	/**
	 * accessor
	 */
	public ArrayList<TetrisSquare> getSquares(){
		return _squares;
	}
	
	

}
