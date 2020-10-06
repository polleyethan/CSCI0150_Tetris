package Tetris;

/**
 * Represents an X, Y Coordinate on the board
 *
 */
public class Coordinate {

	private int _x;
	private int _y;
	
	/**
	 * Constructor
	 */
	public Coordinate(int x, int y) {
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
	
	/**
	 * setter
	 */
	public void setX(int x) {
		_x = x;
	}
	
	/**
	 * setter
	 */
	public void setY(int y) {
		_y = y;
	}
	
	/**
	 * moves y down 1
	 */
	public void moveDown() {
		_y+=1;
	}
	
	/**
	 * moves x left 1
	 */
	public void moveLeft() {
		_x-=1;
	}
	
	/**
	 * moves x right one
	 */
	public void moveRight() {
		_x+=1;
	}
	
	 /**
	 * compares to see if two coordiantes are equal
	 */
	@Override
	  public boolean equals(Object o) {
		 if(((Coordinate) o).getX()==_x && ((Coordinate) o).getY()==_y) {
			 return true;
		 }else {
			 return false;
		 }
	  }
	 
	 /**
	 * hashcode
	 */
	@Override
	  public int hashCode() {
	    return 1;
	 }
	  
}
