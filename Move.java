package Tetris;

import java.util.ArrayList;

/**
 * Represents a move in a game
 *
 */
public class Move implements Comparable<Move>{
	
	private int _rowsCleared;
	private int _tallestHeight;
	private double _totalHeight;
	private double _relativeHeight;
	private int _holes;
	private double _bumpiness;
	
	private int _xMove;
	private int _rot;
	
	private double _moveScore;
	
	/**
	 * constructor
	 */
	public Move(int x, int rot, Game game, AIWeight weight) {
		_xMove = x;
		_rot = rot;
		this.setResultValues(x, rot, game);
		this.calculateScore(weight);
	}
	
	
	/**
	 * calculates the score of each move based on AI's weights
	 */
	public void calculateScore(AIWeight weights) {
		_moveScore = _rowsCleared * weights.getrclearW() + _tallestHeight * weights.gettallHW() + _totalHeight * weights.gettotHW() + _relativeHeight * weights.getrelHW() + _holes * weights.getholesW() + _bumpiness * weights.getbumpW();
		
	}
	
	/**
	 * Gets the results from a specific move.
	 */
	public void setResultValues(int xMove, int rot, Game game){
		int[][] projected = this.copyArray(game.getBoard());
		ArrayList<TetrisSquare> squares = this.copyPieces(game.getCurrentPiece().getSquares());
		for(int r=0; r<rot; r++) {
			if(game.canRotate(game.getCurrentPiece().getShape(), squares)) {
				int xAxel = squares.get(1).getX();
				int yAxel = squares.get(1).getY();
				for(int i = 0; i<squares.size(); i++) {
					int newX = xAxel - yAxel + squares.get(i).getY();
					int newY = yAxel + xAxel - squares.get(i).getX();	
					squares.get(i).setX(newX);
					squares.get(i).setY(newY);
				}
			}	
		}
		
		if(xMove<0) {
			for(int x=0; x>xMove; x--) {
				if(game.canMoveLeft(squares)) {
					for(TetrisSquare s : squares) {
						s.moveLeft();
					}
				}
			}
		}
		else {
			for(int x=0; x<xMove; x++) {
				if(game.canMoveRight(squares)) {
					for(TetrisSquare s : squares) {
						s.moveRight();
					}
				}
			}
		}
		
		
		int yOffset = 0;
		while(game.currentCanMoveDown(yOffset, squares)){
			yOffset +=1;
		}

		for(TetrisSquare s :squares) {
			if(s.getY()+yOffset-1>0 && s.getY()+yOffset-1<Constants.NUM_ROWS)
			projected[s.getY()+yOffset-1][s.getX()] = 1;
		}
		
		int[] columnHeights = new int[Constants.NUM_COLS];
		_rowsCleared = 0;
		_holes = 0;
		_tallestHeight =0;
		_totalHeight =0;
		_bumpiness = 0;
		int shortestheight = 0;
		
		for(int y=projected.length-1; y>0; y--) {
			Boolean rowFull = true;
			for(int x=0; x<projected[y].length; x++) {
				if(projected[y][x]==0) {
					rowFull = false;
					
					if(y<projected.length-1 && projected[y-1][x]==1) {
						_holes +=1;
					}
				}else {
					columnHeights[x] = Constants.NUM_ROWS - y ;
				}
			}
			if(rowFull) {
				_rowsCleared +=1;
			}
		}
		
		for(int n = 0; n<columnHeights.length; n++) {
				_totalHeight += columnHeights[n];
			if(columnHeights[n]>_tallestHeight) {
				_tallestHeight = columnHeights[n];
			}
			
			if(columnHeights[n]<shortestheight) {
				shortestheight = columnHeights[n];
			}
			if(n>0) {
				_bumpiness += Math.abs(columnHeights[n]-columnHeights[n-1]);
			}
		}
		_relativeHeight = _tallestHeight - shortestheight;
		
		/*
		System.out.println("ROWS CLEARED: "+_rowsCleared);
		System.out.println("HOLES: "+_holes);
		System.out.println("TOTAL HEIGHT: "+_totalHeight);
		System.out.println("RELATIVE HEIGHT: "+_relativeHeight);
		System.out.println("TALLEST HEIGHT: "+_tallestHeight);
		System.out.println("BUMPINESS: "+_bumpiness);
		
		*/
		
	}
	
	
	
	
	
	/**
	 * copys an array
	 */
	public int[][] copyArray(int[][] arrayToCopy){
		int[][] newArray = new int[Constants.NUM_ROWS][Constants.NUM_COLS];
		for(int i=0; i<arrayToCopy.length; i++) {
			  for(int j=0; j<arrayToCopy[i].length; j++) {
				  newArray[i][j]=arrayToCopy[i][j];
			  }
		}
		return newArray;
		
	}
	
	
	/**
	 * copys squares
	 */
	public ArrayList<TetrisSquare> copyPieces(ArrayList<TetrisSquare> squares){
		ArrayList<TetrisSquare> newArrayList = new ArrayList<TetrisSquare>();
		for(TetrisSquare s : squares) {
			newArrayList.add(new TetrisSquare(s.getX(),s.getY(),null));
		}
		return newArrayList;
		
	}
	/**
	 * accessor
	 */
	public int getX() {
		return _xMove;
	}
	
	/**
	 * accessor
	 */
	public int getRot() {
		return _rot;
	}

	/**
	 * accessor
	 */
	public double getScore() {
		return _moveScore;
	}

	/**
	 * Compares moves based on score
	 */
	@Override
	public int compareTo(Move m) {
		if(m.getScore()>_moveScore) {
			return 1;
		}
		else if(_moveScore>m.getScore()) {
			return -1;
		}
		else {
			return 0;
		}

	}
	
}
