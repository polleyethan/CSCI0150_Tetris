package Tetris;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Subclass of the player class. AIGenome
 *
 */
public class AIGenome extends Player {
	
	private AIWeight _weights;
	private int _numMoves;
	
	/**
	 * Constructor randomizes genome
	 */
	public AIGenome() {
		super("AI");
		_weights = new AIWeight();
		_numMoves = 0;
	}
	
	/**
	 * Constructor with weights
	 */
	public AIGenome(AIWeight weights) {
		super("AI");
		_weights = weights;
		_numMoves = 0;
	}
	
	/**
	 * accessor
	 */
	public AIWeight getWeights() {
		return _weights;
	}
	
	/**
	 * accessor
	 */
	public int getNumMoves() {
		return _numMoves;
	}
	
	/**
	 * Finds the best move this genome can make in a game
	 */
	public Move getBestMove(Game game){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int r = 0; r<3; r++) {
			for(int x = -1*Constants.NUM_COLS/2; x<Constants.NUM_COLS/2; x++) {
				moves.add(new Move(x,r,game,_weights));
			}
		}
		Collections.sort(moves);
		_numMoves+=1;
		
		return moves.get(0);
	}
	
	/**
	 * Constructor creates genome from two parents, possibly mutating it along the way
	 */
	public AIGenome(AIGenome mom, AIGenome dad, double mutateRate) {
		super("AI");
		_weights = new AIWeight(mom.getWeights(), dad.getWeights());
		_numMoves = 0;
		
		if(Math.random()<mutateRate) {
			_weights.mutate(0,0.05);
		} else if(Math.random()<mutateRate) {
			_weights.mutate(1,0.05);
		} else if(Math.random()<mutateRate) {
			_weights.mutate(2,0.05);
		} else if(Math.random()<mutateRate) {
			_weights.mutate(3,0.05);
		} else if(Math.random()<mutateRate) {
			_weights.mutate(4,0.05);
		} else if(Math.random()<mutateRate) {
			_weights.mutate(5,0.05);
		}
	}

}
