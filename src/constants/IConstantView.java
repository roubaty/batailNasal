package constants;

import java.awt.Color;

public interface IConstantView {
	public static final int NUMBER_CASE_X = 10;
	public static final int NUMBER_CASE_Y = 10;
	public static final int BORDER = 10;
	
	public static final String BIG_MORVE_URL = "src/ressources/morves/grande_morve.gif";
	public static final int BIG_MORVE_SIZE_X = 425;
	public static final int BIG_MORVE_SIZE_Y = 85;
	
	public static final String MEDIUM_MORVE_URL = "src/ressources/morves/moyenne_morve.gif";
	public static final int MEDIUM_MORVE_SIZE_X = 340;
	public static final int MEDIUM_MORVE_SIZE_Y = 85;
	
	public static final String SMALL_MORVE_URL = "src/ressources/morves/petite_morve.gif";
	public static final int SMALL_MORVE_SIZE_X = 255;
	public static final int SMALL_MORVE_SIZE_Y = 85;
	
	public static final double ANGLE_90 = Math.PI/2.0;
	
	public static final Color CROSS_COLOR = Color.RED;
	public static final int CROSS_STROKE = 3;
	
	public static final Color SCAN_COLOR = Color.RED;
	public static final int SCAN_STROKE = 3;
	public static final float SCAN_TRANSPARENCE = 0.5f;
	
	public static final Color CASE_NONVISIBLE_COLOR = new Color(204, 204, 255);
	
	public static final Color BACKGROUND_COLOR = new Color(204, 255, 255);
	
	public static final Color GRID_COLOR = Color.BLACK;
	public static final float GRID_STROKE = 1f;
}