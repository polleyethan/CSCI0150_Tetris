package Tetris;

import javafx.animation.Timeline;

/**
 * Subclass of game class
 *
 */
public class AIGame extends Game {
	
	private AIGenome _genome;
	private Boolean _hasMovesLimit;
	private int _moveLimit;

	/**
	 * Constructor
	 */
	public AIGame(Timeline t, AIGenome genome, GameOrganizer organizer, Boolean haslimit, int movelimit) {
		super(genome,t, organizer);
		_genome = genome;
		_hasMovesLimit = haslimit;
		_moveLimit = movelimit;
	}

	/**
	 * Updates the game
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
				if(!_hasMovesLimit || _genome.getNumMoves()<_moveLimit ) {
					Move best = _genome.getBestMove(this);
					this.aiMove(best);
				}else {
					this.gameOver();
				}
			}
		}
		_gamePane.requestFocus();

	}
	
	/**
	 * Allows AIGenome to make a move
	 */
	public void aiMove(Move move) {
		for(int r=0; r<move.getRot(); r++) {
			if(this.canRotate(_currentPiece.getShape(),_currentPiece.getSquares())) {
				_currentPiece.rotateCounterClock();
			}	
		}
		
		if(move.getX()<0) {
			for(int x=0; x>move.getX(); x--) {
				if(this.canMoveLeft(_currentPiece.getSquares())) {
					for(TetrisSquare s : _currentPiece.getSquares()) {
						s.moveLeft();
					}
				}
			}
		}
		else {
			for(int x=0; x<move.getX(); x++) {
				if(this.canMoveRight(_currentPiece.getSquares())) {
					for(TetrisSquare s : _currentPiece.getSquares()) {
						s.moveRight();
					}
				}
			}
		}
	}
	

}
