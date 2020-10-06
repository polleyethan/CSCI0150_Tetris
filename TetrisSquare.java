package Tetris;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Class representing a square.
 *
 */
public class TetrisSquare{
	

	private Color _color;
	private Rectangle _rect;
	private Boolean _isDrawn;
	private Coordinate _coords;
	
	/**
	 * Constructor
	 */
	public TetrisSquare(int x, int y, Color color) {
		_coords = new Coordinate(x,y);
		_color = color;
		_isDrawn = false;
	}
	

	
	/**
	 * Creates the square graphically
	 */
	public void createSquare(Pane gamePane) {
		_isDrawn = true;
		_rect = new Rectangle(_coords.getX()*Constants.COL_WIDTH, _coords.getY()*Constants.ROW_HEIGHT, Constants.COL_WIDTH, Constants.ROW_HEIGHT);
		_rect.setFill(_color);
		_rect.setFocusTraversable(false);
		gamePane.getChildren().addAll(_rect);
	}
	
	/**
	 * redraws the square
	 */
	public void redraw() {
		_rect.setX(_coords.getX()*Constants.COL_WIDTH);
		_rect.setY(_coords.getY()*Constants.ROW_HEIGHT);
		
	}

	
	
	/**
	 * removes square from gamepane
	 */
	public void remove(Pane gamePane) {
         gamePane.getChildren().removeAll(_rect);
 
	}
	/**
	 * moves square down
	 */
	public void moveDown() {
		_coords.moveDown();
	}
	
	/**
	 * moves square right
	 */
	public void moveRight() {
		_coords.moveRight();
	}
	
	/**
	 * moves square left
	 */
	public void moveLeft() {
		_coords.moveLeft();
	}
	
	/**
	 * accessor
	 */
	public Boolean isDrawn() {
		return _isDrawn;
	}
	
	/**
	 * accessor
	 */
	public int getX() {
		return _coords.getX();
	}
	
	/**
	 * accessor
	 */
	public int getY() {
		return _coords.getY();
	}
	
	/**
	 * sets the x value
	 */
	public void setX(int x) {
		_coords.setX(x);
	}

	/**
	 * sets the y value
	 */
	public void setY(int y) {
		_coords.setY(y);
	}
	
	/**
	 * accessor
	 */
	public Coordinate getCoord() {
		return _coords;
	}
	

}
