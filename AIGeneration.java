package Tetris;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A generation of AIGenomes
 *
 */
public class AIGeneration {

	private ArrayList<AIGenome> _population;
	private int _popSize;
	private int _currentGenome;
	private double _mutateRate;
	
	/**
	 * Constructor that randomizes a generation solely based on a size input
	 */
	public AIGeneration(int size) {
		_popSize = size;
		_currentGenome = -1;
		_mutateRate = 0.05;
		_population = new ArrayList<AIGenome>();
		
		for(int n =0; n<size; n++) {
			_population.add(new AIGenome());
		}
		
	}
	
	/**
	 * accessor
	 */
	public double getMutateRate() {
		return _mutateRate;
	}
	
	/**
	 * accessor
	 */
	public int getSize() {
		return _popSize;
	}
	
	/**
	 * accessor
	 */
	public AIGenome getCurrent() {
		return _population.get(_currentGenome);
	}
	
	
	
	/**
	 * Constructor which mutates a previous generation
	 */
	public AIGeneration(AIGeneration previousGen, int size, double mutateRate) {
		previousGen.getTopGenomes((int)size/2);
		_popSize = size;
		_mutateRate = mutateRate;
		_population = new ArrayList<AIGenome>();
		_population.add(new AIGenome(previousGen.getPopulation().get(0).getWeights()));
		_population.add(new AIGenome(previousGen.getPopulation().get(1).getWeights()));
		_population.add(new AIGenome(previousGen.getPopulation().get(2).getWeights()));
		_population.add(new AIGenome(previousGen.getPopulation().get(3).getWeights()));
		_population.add(new AIGenome(previousGen.getPopulation().get(4).getWeights()));
		
		
		for(int i = 0; i<size-_population.size(); i++) {
			_population.add(previousGen.makeChild(_mutateRate));
		}
		
		
		
	}
	
	
	/**
	 * accessor
	 */
	public ArrayList<AIGenome> getPopulation(){
		return _population;
	}
	
	/**
	 * moves to the next genome in this generation
	 */
	public void moveToNext() {
		_currentGenome += 1;
	}
	
	/**
	 * accesses next genome
	 */
	public AIGenome getNext() {
		if(_currentGenome<_population.size()) {
			return _population.get(_currentGenome);
		}else {
			return null;
		}
	}
	
	/**
	 * returns the fittest genomes
	 */
	public void getTopGenomes(int size){
		ArrayList<AIGenome> toReturn = new ArrayList<AIGenome>();
		Collections.sort(_population);
		for(int n = 0; n<size; n++) {
			toReturn.add(_population.get(n));
		}
		_population.clear();
		_population.addAll(toReturn);
		
	}
	
	/**
	 * returns a random genome
	 */
	public AIGenome getRandomGenome() {
		int index = (int) (_population.size() * Math.random());
		return _population.get(index);
	}
	
	/**
	 * Uses a mom and dad genome to create a child genome
	 */
	public AIGenome makeChild(double mutateRate) {
		AIGenome dad = this.getRandomGenome();
		AIGenome mom = this.getRandomGenome();
		
		return new AIGenome(mom, dad, mutateRate);
		
	}
}
