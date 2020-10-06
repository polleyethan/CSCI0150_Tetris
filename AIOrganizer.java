package Tetris;



import java.util.Collections;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Subclass of GameOrganizer Class
 *
 */
public class AIOrganizer extends GameOrganizer {

	private Game _game;
	private BorderPane _root;
	private AIGeneration _gen;
	private Label _currentGeneration;
	private Label _currentGenome;
	private Label _genScores;
	private int _genNum;
	private int _genSize;
	private int _numberOfGens;
	
	/**
	 * Constructor
	 */
	public AIOrganizer(Stage stage, int genSize, int numberOfGens) {
		super(stage);
		_genSize = genSize;
		_numberOfGens = numberOfGens;
		this.createTimeline(Constants.AI_TIMELINE_DELAY);
		this.createGames();
	}

	
	/**
	 * Creates the initial games
	 */
	protected void createGames() {
		_gen = new AIGeneration(_genSize);
		_genNum = 0;
		_gen.moveToNext();
		_game = new AIGame(_timeline, _gen.getNext(), this, false, 300);
		this.setupPane();
	}
	
	/**
	 * Sets up the AIPanes
	 */
	private void setupPane() {
		_root = new BorderPane();
		
		VBox center = new VBox();
		Label l = new Label("AI is training... See console for more details.");
		Button backToHome = new Button("Back To Home");
		
		backToHome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				MenuOrganizer organizer = new MenuOrganizer(_stage);
				Scene scene = new Scene(organizer.getRoot(), Constants.SCENE_WIDTH, Constants.SCENE_HEIGHT);
				_stage.setScene(scene);
				_stage.show();
			}
		});
		
		Button quit = new Button("Quit");
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		HBox buttons = new HBox(backToHome,quit);
		center.getChildren().addAll(l,buttons);
		_root.setCenter(center);
	}

	
	/**
	 * Creates a new generation
	 */
	protected void createNewGen() {
		if(_genNum<_numberOfGens) {
			_gen = new AIGeneration(_gen,_gen.getSize(),_gen.getMutateRate()-0.01);
			_genNum += 1;
			_gen.moveToNext();
			_game = new AIGame(_timeline, _gen.getNext(), this, false, 300);
		}
		else {
			System.out.println("*******GENERATION "+_genNum+"********");
			Collections.sort(_gen.getPopulation());
			int tots = 0;
			for(int i = 0; i<10; i++){
				tots += _gen.getPopulation().get(i).getScore();
				System.out.println("SCORE: "+_gen.getPopulation().get(i).getScore());
			}
			System.out.println("*********** Top 10 AVG: "+tots/10);
			System.out.println("*********** Top Weights: ***********");
			System.out.println(_gen.getPopulation().get(0).getWeights().toString());
			System.out.println("**********************");
		}
	}
	

	/**
	 * Updates game
	 */
	protected void update() {
		_game.update();
	}
	
	/**
	 * accessor
	 */
	public Pane getRootPane() {
		return _root;
	}
	



/**
 * called when a game is over
 */
@Override
protected void onEnd(Player loser) {
	System.out.println("GENOME DEAD: "+_gen.getCurrent().getScore());
	_gen.moveToNext();
	if(!(_gen.getNext() == null)) {
		this.createTimeline(Constants.AI_TIMELINE_DELAY);
		_game = new AIGame(_timeline, _gen.getNext(), this, false, 300);
	}else {
		
		System.out.println("*******GENERATION "+_genNum+"********");
		Collections.sort(_gen.getPopulation());
		Collections.sort(_gen.getPopulation());
		int tots = 0;
		for(int i = 0; i<10; i++){
			tots += _gen.getPopulation().get(i).getScore();
			System.out.println("SCORE: "+_gen.getPopulation().get(i).getScore());
		}
		System.out.println("*********** Top 10 AVG: "+tots/10);
		System.out.println("*********** Top Weights: ***********");
		System.out.println(_gen.getPopulation().get(0).getWeights().toString());
		System.out.println("**********************");
		
		this.createNewGen();
	}
	
}

}