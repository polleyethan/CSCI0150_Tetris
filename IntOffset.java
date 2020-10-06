package Tetris;

/**
 * Represents the initial offsets of coordinates for each game piece
 *
 */
public class IntOffset {

	private int _x;
	private int _y;
	
	/**
	 * Constructor
	 */
	public IntOffset(int x, int y) {
		_x = x;
		_y = y;
	}
	
	/**
	 * accessor
	 */
	public int getX() {
		return _x;
	}
	
	/**
	 * accessor
	 */
	public int getY() {
		return _y;
	}
	
}
