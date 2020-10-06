package Tetris;

/**
 * Abstract class for a player of a game
 *
 */
public abstract class Player implements Comparable<Player>{
	
	private String _name;
	private int _score;

	/**
	 * Constructor
	 */
	public Player(String name) {
		_name = name;
		_score = 0;
	}
	
	/**
	 * accessor
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * adds to the players score
	 */
	public void addToScore(int s) {
		_score += s;
	}
	
	/**
	 * accessor to get the score
	 */
	public int getScore() {
		return _score;
	}
	
	/**
	 * Compares to other players
	 */
	@Override
	public int compareTo(Player p) {
		if(p.getScore()>_score) {
			return 1;
		}
		else if(_score>p.getScore()) {
			return -1;
		}
		else {
			return 0;
		}

	}
}
