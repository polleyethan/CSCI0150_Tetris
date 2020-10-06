package Tetris;

import java.util.List;
import java.util.ArrayList;
import java.util.*;

import javafx.scene.paint.Color;

/**
 * Initializes the squares for a piece.
 *
 */
public enum PieceShape {
	
	ONE(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(0,-2),
			new IntOffset(0,-3)
			)), 
			Color.RED), 
	TWO(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(1,0),
			new IntOffset(1,-1)
			)), 
			Color.PINK), 
	THREE(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(1,-1),
			new IntOffset(0,-2)
			)), 
			Color.ORANGE), 
	FOUR(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(0,-2),
			new IntOffset(1,-2)
			)), 
			Color.YELLOW), 
	FIVE(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(0,-2),
			new IntOffset(-1,-2)
			)), 
			Color.GREEN), 
	SIX(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(-1,-1),
			new IntOffset(-1,-2)
			)), 
			Color.BLUE), 
	SEVEN(new ArrayList<>(Arrays.asList(
			new IntOffset(0,0),
			new IntOffset(0,-1),
			new IntOffset(1,-1),
			new IntOffset(1,-2)
			)), 	
			Color.PURPLE);
	
	
	private ArrayList<IntOffset> _coordOffsets;
	private Color _color;
	
	/**
	 * Constructor
	 */
	private PieceShape(ArrayList<IntOffset> offsets, Color color) {
        _coordOffsets = offsets;
        _color = color;
    }
	
	/**
	 * Returns the squres for a given piece
	 */
	public ArrayList<TetrisSquare> getSquares(int x, int y){
		ArrayList<TetrisSquare> squaresToReturn = new ArrayList<TetrisSquare>();
		for(int i = 0; i<_coordOffsets.size(); i++) {
			squaresToReturn.add(new TetrisSquare(x+_coordOffsets.get(i).getX(),y+_coordOffsets.get(i).getY(), _color));
		}
		return squaresToReturn;
	}
	
	
	/**
	 * Gets an arraylist of offsets for a given piece
	 */
	public ArrayList<IntOffset> getOffsets(){
		return _coordOffsets;
	}
	
	/**
	 * accessor
	 */
	public Color getColor() {
		return _color;
	}
}
