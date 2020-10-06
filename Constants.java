package Tetris;

/**
 * Constants class.
 *
 */
public class Constants {
	public static final double TIMELINE_DELAY = 0.5;
	public static final double AI_TIMELINE_DELAY = 0.0001;
	public static final double WATCH_AI_DELAY = 0.005;
	public static final double GAME_PANE_WIDTH = 400;
	public static final double GAME_PANE_HEIGHT = 600;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLS = 12;
	public static final double ROW_HEIGHT = GAME_PANE_HEIGHT/NUM_ROWS;
	public static final double COL_WIDTH = GAME_PANE_WIDTH/NUM_COLS;
	public static final double SCENE_WIDTH = 700;
	public static final double SCENE_HEIGHT = 700;
	public static final AIGenome SUPERIOR_GENOME = new AIGenome(new AIWeight(0.39148232682071793,-0.007275604015763659,-0.5062250065462781,-0.03853525242238931,-0.4311634080611051,-0.15752590093463437));

	private Constants() {

	}
}