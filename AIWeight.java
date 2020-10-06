package Tetris;

/**
 * Class representing AIWeights used to help ai make decisions
 *
 */
public class AIWeight {
	
	private double _rclearW;
	private double _tallHW;
	private double _totHW;
	private double _relHW;
	private double _holesW;
	private double _bumpW;
	
	/**
	 * Randomized constructor
	 */
	public AIWeight() {
		 _rclearW = Math.random();
		 _tallHW = -1*Math.random();
		 _totHW = -1*Math.random();
		 _relHW = -1*Math.random();
		 _holesW = -1*Math.random();
		 _bumpW = -1*Math.random();
	}
	
	/**
	 * COnstructor for specific weights
	 */
	public AIWeight(double rclear, double tall, double tot, double rel, double holes, double bumps) {
		 _rclearW = rclear;
		 _tallHW = tall;
		 _totHW = tot;
		 _relHW = rel;
		 _holesW = holes;
		 _bumpW = bumps;
	}

	/**
	 * accessor
	 */
	public double getrclearW() {
		return _rclearW;
	}

	/**
	 * accessor
	 */
	public double gettallHW() {
		return _tallHW;
	}

	/**
	 * accessor
	 */
	public double gettotHW() {
		return _totHW;
	}

	/**
	 * accessor
	 */
	public double getrelHW() {
		return _relHW;
	}

	/**
	 * accessor
	 */
	public double getholesW() {
		return _holesW;
	}

	/**
	 * accessor
	 */
	public double getbumpW() {
		return _bumpW;
	}
	
	/**
	 * mutates a weight
	 */
	public void mutate(int w, double amount) {
		System.out.println("MUTATE");
		switch(w){
		case 0:
			_rclearW += (Math.random()*amount);
			break;
		case 1:
			_tallHW += (Math.random()*amount);
			break;
		case 2:
			_totHW += (Math.random()*amount);
			break;
		case 3:
			_relHW += (Math.random()*amount);
			break;
		case 4:
			_holesW += (Math.random()*amount);
			break;
		case 5:
			_bumpW += (Math.random()*amount);
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * creates a weight from two parent weights
	 */
	public AIWeight(AIWeight mom, AIWeight dad) {
		_rclearW = chooseRandom(mom.getrclearW(), dad.getrclearW());
		 _tallHW = chooseRandom(mom.gettallHW(), dad.gettallHW());
		 _totHW = chooseRandom(mom.gettotHW(), dad.gettotHW());
		 _relHW = chooseRandom(mom.getrelHW(), dad.getrelHW());
		 _holesW = chooseRandom(mom.getholesW(), dad.getholesW());
		 _bumpW = chooseRandom(mom.getbumpW(), dad.getbumpW());
	}
	
	/**
	 * chooses a random weight
	 */
	public static double chooseRandom(double mom, double dad) {
		if(Math.round(Math.random()) == 0){			
			return mom;
		}else {
			return dad;
		}
	}
	
	
	/**
	 * String representaiton of AIWeights
	 */
	@Override 
	public String toString() {
		return "Rows Cleared Weight:" + _rclearW+"\n"
				+ "Tallest Height Weight:"+ _tallHW +"\n"
				+ "Total Height Weight:" + _totHW+"\n"
				+ "Relative Height Weight:" + _relHW+"\n"
				+ "Holes Weight:" + _holesW+"\n"
				+ "Bumps Weight:" +_bumpW;
	}
	

}
