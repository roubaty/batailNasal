package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import constants.IConstantView;
import constants.IConstantsGlobal;

import model.MorveBean;

public class GridPanel extends JPanel implements IConstantView,
		IConstantsGlobal {
	private static final long serialVersionUID = 1L;

	private int nb_case_x = NUMBER_CASE_X; 										// number of case in grid on x
	private int nb_case_y = NUMBER_CASE_Y; 										// number of case in grid on y
	private ArrayList<MorveBean> my_table_movre = new ArrayList<MorveBean>(); 	// List of morve on the grid
	private boolean[][] my_table_visibility; // table of visibility of the grid
	private boolean[][] my_table_shot; // table of shot of the grid
	private int grid_size_x; // Size x of the grid without border
	private int grid_size_y; // Size y of the grid without border
	private static int case_size_x; // Size in x of a case in the grid
	private static int case_size_y; // Size in y of a case in the grid
	private int no_grid; // Number of the grid 0 for player_grid and 1 for
							// ai_grid
	private int state = TYPEADDMORVE; // State of the program
	private KeyListenerAddMorveAndSpray keyListener; // keyListener to go if the
														// touch CTRL is press
	private GridThreadScanAndAddMorve modificationState = new GridThreadScanAndAddMorve(
			this); // Process to repaint the grid at 10Hz when we need
	private MorveBean morve; // Morve that is going to be had on state :
								// TYPEADDMORVE
	private boolean morveAddValid = true; // Define if the morve is valid to be
											// add
	private Random rnd = new Random(); // Random
	private Stroke g2dStroke; // Stroke use the save and retrieve g2d
	private Color g2dColor; // Color use the save and retrieve g2d
	private Composite g2dComposite; // Composite use the save and retrieve g2d

	/*
	 * Initialization section
	 */
	/**
	 * Constructor
	 */
	public GridPanel(MainView view, int no_grid,
			KeyListenerAddMorveAndSpray keyListener) {
		this.addMouseListener(new MyMouseListener(view));
		this.keyListener = keyListener;
		nb_case_x = NUMBER_CASE_X;
		nb_case_y = NUMBER_CASE_Y;
		this.no_grid = no_grid;
		my_table_visibility = new boolean[nb_case_x][nb_case_y];
		my_table_shot = new boolean[nb_case_x][nb_case_y];
		;
	}

	/**
	 * Define of grid size variables
	 */
	private void initSize() {
		grid_size_x = (getWidth() - (BORDER * 2));
		grid_size_y = (getHeight() - (BORDER * 2));
		case_size_x = grid_size_x / nb_case_x;
		case_size_y = grid_size_y / nb_case_y;
	}

	/*
	 * draw section
	 */
	/**
	 * Call on repaint
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		initSize();
		setBackground(BACKGROUND_COLOR);
		Graphics2D g2d = (Graphics2D) g;
		drawMorve(g2d);
		drawShot(g2d);
		if (no_grid == 1) {
			drawVisibility(g2d);
		}
		drawGrid(g2d);
		if (state == TYPESCAN) {
			if (no_grid == 1) {
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawScan(g2d);
			}
			drawBorder(g2d);
		} else if (state == TYPESPRAY) {
			if (no_grid == 1) {
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawSpray(g2d);
			}
			drawBorder(g2d);
		} else if (state == TYPEADDMORVE) {
			if (no_grid == 0) {
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawAddMorve(g2d, 3);
			}
			drawBorder(g2d);
		} else if (state == TYPENORMAL || state == TYPETRIPLE) {
			if (no_grid == 0) {
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawScope(g2d);
			}
		}
	}

	/**
	 * draw all the morve for the ArrayList my_table_movre
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	private void drawMorve(Graphics2D g2d) {
		saveG2dState(g2d);
		for (MorveBean i : my_table_movre) {
			Image img;
			int image_origine_size_x;
			int image_origine_size_y;
			// Choice the right image and define the size of this image from the
			// size of the morve
			if (i.getSize() == 4) {
				img = Toolkit.getDefaultToolkit().getImage(BIG_MORVE_URL);
				image_origine_size_x = BIG_MORVE_SIZE_X;
				image_origine_size_y = BIG_MORVE_SIZE_Y;
			} else if (i.getSize() == 3) {
				img = Toolkit.getDefaultToolkit().getImage(MEDIUM_MORVE_URL);
				image_origine_size_x = MEDIUM_MORVE_SIZE_X;
				image_origine_size_y = MEDIUM_MORVE_SIZE_Y;
			} else {
				img = Toolkit.getDefaultToolkit().getImage(SMALL_MORVE_URL);
				image_origine_size_x = SMALL_MORVE_SIZE_X;
				image_origine_size_y = SMALL_MORVE_SIZE_Y;
			}
			if (i.getDirection() == 0) {
				// Draw a horizontally morve
				drawAMorveHorivontally(g2d, i, img, image_origine_size_x,
						image_origine_size_y);
			} else {
				// Draw a vertically morve
				drawAMorveVertically(g2d, i, img, image_origine_size_x,
						image_origine_size_y);
			}
		}
		retriveG2dState(g2d);
	}

	/**
	 * Draw a cross where the ai or player has already shot
	 * 
	 * @param g2d
	 */
	private void drawShot(Graphics2D g2d) {
		saveG2dState(g2d);
		// Define the quality of the line
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		// Define the Stroke of the line
		Stroke stroke = new BasicStroke(CROSS_STROKE, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		g2d.setStroke(stroke);
		// Define the color of the line
		g2d.setColor(CROSS_COLOR);
		for (int i = 0; i < my_table_shot.length; i++) {
			for (int j = 0; j < my_table_shot[i].length; j++) {
				if (my_table_shot[i][j]) {
					// get coordinate of the case in the gridPanel
					Point down_left_corner = getCoordinateCase(i, j);
					// Define the left down corner coordinate
					int down_left_corner_x = down_left_corner.x;
					int down_left_corner_y = down_left_corner.y;
					// Define the right down corner coordinate
					int down_right_corner_x = down_left_corner_x + case_size_x;
					int down_right_corner_y = down_left_corner_y;
					// Define the left up corner coordinate
					int up_left_corner_x = down_left_corner_x;
					int up_left_corner_y = down_left_corner_y + case_size_y;
					// Define the right up corner coordinate
					int up_right_corner_x = down_left_corner_x + case_size_x;
					int up_right_corner_y = down_left_corner_y + case_size_y;
					// Draw a line from the right down corner to the left up
					// corner
					g2d.drawLine(down_right_corner_x, down_right_corner_y,
							up_left_corner_x, up_left_corner_y);
					// Draw a line from the left down corner to the right up
					// corner
					g2d.drawLine(down_left_corner_x, down_left_corner_y,
							up_right_corner_x, up_right_corner_y);
				}
			}
		}
		retriveG2dState(g2d);
	}

	/**
	 * Color cases that aren't visible for the player on the ai grid
	 * 
	 * @param g2d
	 */
	private void drawVisibility(Graphics2D g2d) {
		saveG2dState(g2d);
		g2d.setColor(CASE_NONVISIBLE_COLOR);
		for (int i = 0; i < my_table_visibility.length; i++) {
			for (int j = 0; j < my_table_visibility[i].length; j++) {
				if (!my_table_visibility[i][j]) {
					Point down_left_corner = getCoordinateCase(i, j);
					int down_left_corner_x = down_left_corner.x;
					int down_left_corner_y = down_left_corner.y;
					// fill a rectangle of the case
					g2d.fillRect(down_left_corner_x, down_left_corner_y,
							case_size_x, case_size_y);
				}
			}
		}
		retriveG2dState(g2d);
	}

	/**
	 * draw a scan on the ai grid the scan follow the mouse
	 * 
	 * @param g2d
	 */
	private void drawScan(Graphics2D g2d) {
		saveG2dState(g2d);
		// Get the mouse point position
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		// convert the pointer global location to a relative location on the
		// panel
		SwingUtilities.convertPointFromScreen(p, this);
		// find the case that the mouse pointer is in.
		int[] cas = getCaseFromPosition(p);
		// if the case is valid
		if (cas[0] != -1) {
			// get coordinate of the case in the gridPanel
			p = getCoordinateCase(cas[0], cas[1]);
			// create a rectangle size 3x3 with the middle at the mouse pointer
			// case
			int x = p.x - (((SCAN_SIZE_X - 1) / 2)) * case_size_x;
			int y = p.y - ((SCAN_SIZE_Y - 1) / 2) * case_size_y;
			int w = case_size_x * SCAN_SIZE_X;
			int h = case_size_y * SCAN_SIZE_Y;
			Rectangle2D.Float rec = new Rectangle2D.Float(x, y, w, h);
			// Create a rectangle 5x1 with the middle at the mouse pointer case
			Rectangle2D.Float rec2 = new Rectangle2D.Float(p.x
					- (2 * case_size_x), y + case_size_y, 5 * case_size_x,
					case_size_y);
			// Create a rectangle 1x5 with the middle at the mouse pointer case
			Rectangle2D.Float rec3 = new Rectangle2D.Float(p.x, y
					- (case_size_y), case_size_x, 5 * case_size_y);
			// Define the color of the scan
			g2d.setColor(SCAN_COLOR);
			// Define the transparency of the scan
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			// Draw all 3 rectangle
			g2d.fill(rec);
			g2d.fill(rec2);
			g2d.fill(rec3);
		}
		retriveG2dState(g2d);
	}

	/**
	 * Draw a spray on the ai grid the spray follow the mouse
	 * 
	 * @param g2d
	 */
	private void drawSpray(Graphics2D g2d) {
		saveG2dState(g2d);
		// Get the mouse point position
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		// convert the pointer global location to a relative location on the
		// panel
		SwingUtilities.convertPointFromScreen(p, this);
		// find the case that the mouse pointer is in.
		int[] cas = getCaseFromPosition(p);
		// if the case is valid
		if (cas[0] != -1) {
			// get coordinate of the case in the gridPanel
			p = getCoordinateCase(cas[0], cas[1]);
			// define the vertical area of the spray
			int x = p.x - (((SPRAY_SIZE - 1) / 2)) * case_size_x;
			int y = p.y;
			int w = case_size_x * SPRAY_SIZE;
			int h = case_size_y;
			// define the horizontal area of the spray
			int x2 = p.x;
			int y2 = p.y - (((SPRAY_SIZE - 1) / 2)) * case_size_y;
			int w2 = case_size_x;
			int h2 = case_size_y * SPRAY_SIZE;

			g2d.setStroke(new BasicStroke(SPRAY_STROKE));
			g2d.setColor(SPRAY_COLOR);
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			g2d.setStroke(new BasicStroke(SPRAY_STROKE));
			// Draw random points in the vertical area
			for (int i = 0; i < SPRAY_DENSITY; i++) {
				int pos_x = rnd.nextInt(w) + x;
				int pos_y = rnd.nextInt(h) + y;
				g2d.drawLine(pos_x, pos_y, pos_x, pos_y);
			}
			// Draw random points in the horizontal area
			for (int i = 0; i < SPRAY_DENSITY; i++) {
				int pos_x = rnd.nextInt(w2) + x2;
				int pos_y = rnd.nextInt(h2) + y2;
				g2d.drawLine(pos_x, pos_y, pos_x, pos_y);
			}
		}
		retriveG2dState(g2d);
	}

	/**
	 * Draw a morve that follow the mouse pointer
	 * 
	 * @param g2d
	 * @param morve_size
	 */
	private void drawAddMorve(Graphics2D g2d, int morve_size) {
		saveG2dState(g2d);
		// look if the touch CTRL is press
		boolean ctrl = keyListener.isCrltPress();
		// Get the mouse point position
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		// convert the pointer global location to a relative location on the
		// panel
		SwingUtilities.convertPointFromScreen(p, this);
		// find the case that the mouse pointer is in.
		int[] cas = getCaseFromPosition(p);
		// if the case is valid
		if (cas[0] != -1) {
			// Define the transparency
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			Image img;
			int image_origine_size_x;
			int image_origine_size_y;
			// Create the morve that is going to be print
			if (ctrl) {
				morve = new MorveBean(cas[0], cas[1], morve_size, false);
			} else {
				morve = new MorveBean(cas[0], cas[1], morve_size, true);
			}
			// TODO add constant
			// choose the right picture from th morve size
			if (morve.getSize() == 4) {
				img = Toolkit.getDefaultToolkit().getImage(BIG_MORVE_URL);
				image_origine_size_x = BIG_MORVE_SIZE_X;
				image_origine_size_y = BIG_MORVE_SIZE_Y;
			} else if (morve.getSize() == 3) {
				img = Toolkit.getDefaultToolkit().getImage(MEDIUM_MORVE_URL);
				image_origine_size_x = MEDIUM_MORVE_SIZE_X;
				image_origine_size_y = MEDIUM_MORVE_SIZE_Y;
			} else {
				img = Toolkit.getDefaultToolkit().getImage(SMALL_MORVE_URL);
				image_origine_size_x = SMALL_MORVE_SIZE_X;
				image_origine_size_y = SMALL_MORVE_SIZE_Y;
			}
			// Draw the morve
			if (ctrl) {
				drawAMorveVertically(g2d, morve, img, image_origine_size_x,
						image_origine_size_y);
			} else {
				drawAMorveHorivontally(g2d, morve, img, image_origine_size_x,
						image_origine_size_y);
			}
			// Draw a red rectangle around the morve if you can't add it
			if (!isValideAdd(morve, my_table_movre)) {
				if (!ctrl) {
					Point point = getCoordinateCase(cas[0], cas[1]);
					g2d.fill(new Rectangle2D.Float(point.x, point.y,
							case_size_x * morve_size, case_size_y));
				} else {
					Point point = getCoordinateCase(cas[0], cas[1]);
					g2d.fill(new Rectangle2D.Float(point.x, point.y,
							case_size_x, case_size_y * morve_size));
					morveAddValid = false;
				}
			}
		}
		retriveG2dState(g2d);
	}

	/**
	 * Draw a vertically morve
	 * 
	 * @param g2d
	 * @param morve
	 *            morve that you're going to draw
	 * @param img
	 *            image of the morve
	 * @param image_origine_size_x
	 * @param image_origine_size_y
	 */
	private void drawAMorveVertically(Graphics2D g2d, MorveBean morve,
			Image img, double image_origine_size_x, double image_origine_size_y) {
		saveG2dState(g2d);
		// the local grid coordinate of the morve
		Point p = getCoordinateCase(morve.getStart_case_x(),
				morve.getStart_case_y());
		// calculate the position and size at disposition on the grid
		int x = p.x + case_size_x;
		int y = p.y;
		int width = case_size_y * morve.getSize();
		int height = case_size_x;

		// calculate the scale to concatenate the image
		double scale_x = width / (double) image_origine_size_x;
		double scale_y = height / (double) image_origine_size_y;

		// Translate the image position
		AffineTransform af = AffineTransform.getTranslateInstance(x, y);
		// Rotate the image of 90°
		af.rotate(ANGLE_90);
		// concatenate the image the right size
		af.concatenate(AffineTransform.getScaleInstance(scale_x, scale_y));
		// Draw the image with the transformation
		g2d.drawImage(img, af, null);
		retriveG2dState(g2d);
	}
	
	/**
	 * Draw a horizontally morve
	 * 
	 * @param g2d
	 * @param morve
	 *            morve that you're going to draw
	 * @param img
	 *            image of the morve
	 * @param image_origine_size_x
	 * @param image_origine_size_y
	 */
	private void drawAMorveHorivontally(Graphics2D g2d, MorveBean morve,
			Image img, double image_origine_size_x, double image_origine_size_y) {
		saveG2dState(g2d);
		// the local grid coordinate of the morve
		Point p = getCoordinateCase(morve.getStart_case_x(),
				morve.getStart_case_y());
		// calculate the position and size at disposition on the grid
		int x = p.x;
		int y = p.y;
		int width = case_size_x * morve.getSize();
		int height = case_size_y;
		
		// calculate the scale to concatenate the image
		double scale_x = width / (double) image_origine_size_x;
		double scale_y = height / (double) image_origine_size_y;

		// Translate the image position
		AffineTransform af = AffineTransform.getTranslateInstance(x, y);
		// concatenate the image the right size
		af.concatenate(AffineTransform.getScaleInstance(scale_x, scale_y));
		// Draw the image with the transformation
		g2d.drawImage(img, af, null);
		retriveG2dState(g2d);
	}

	/**
	 * Draw the border of the grid
	 * @param g2d
	 */
	private void drawBorder(Graphics2D g2d) {
		saveG2dState(g2d);
		//Create rectangle of all border
		Rectangle2D.Float recBorderUp = new Rectangle2D.Float(0, 0, getWidth(),
				BORDER);
		int borderDown = BORDER + (nb_case_y * case_size_y);
		Rectangle2D.Float recBorderDown = new Rectangle2D.Float(0,
				borderDown + 1, getWidth(), BORDER);
		Rectangle2D.Float recBorderleft = new Rectangle2D.Float(0, 0, BORDER,
				getHeight());
		int borderRight = BORDER + (nb_case_x * case_size_x);
		Rectangle2D.Float recBorderRight = new Rectangle2D.Float(
				borderRight + 1, 0, BORDER, getHeight());
		//define the color of the border
		g2d.setColor(BACKGROUND_COLOR);
		//Fill the rectangle
		g2d.fill(recBorderUp);
		g2d.fill(recBorderDown);
		g2d.fill(recBorderleft);
		g2d.fill(recBorderRight);
		retriveG2dState(g2d);
	}
	/**
	 * Draw the grid
	 * @param g2d
	 */
	private void drawGrid(Graphics2D g2d) {
		saveG2dState(g2d);
		g2d.setPaint(GRID_COLOR);
		g2d.setStroke(new BasicStroke(GRID_STROKE));
		Line2D.Float line;
		for (int i = 0; i < nb_case_y + 1; i++) {
			int pos_y = BORDER + (i * case_size_y);
			line = new Line2D.Float(BORDER, pos_y, case_size_x * nb_case_x
					+ BORDER, pos_y);
			g2d.draw(line);
		}
		for (int i = 0; i < nb_case_x + 1; i++) {
			int pos_x = BORDER + (i * case_size_x);
			line = new Line2D.Float(pos_x, BORDER, pos_x, case_size_y
					* nb_case_y + BORDER);
			g2d.draw(line);
		}
		retriveG2dState(g2d);
	}
	/**
	 * Draw a scope when the state is adequate
	 * @param g2d
	 */
	private void drawScope(Graphics2D g2d) {
		saveG2dState(g2d);
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		this.getLocationOnScreen();
		SwingUtilities.convertPointFromScreen(p, this);
		int[] cas = getCaseFromPosition(p);
		if (cas[0] != -1) {
			p = getCoordinateCase(cas[0], cas[1]);
			float r = (float) Math.sqrt((case_size_x * case_size_x)
					+ (case_size_y * case_size_y));
			float declage_x = (r - case_size_x) / 2;
			float declage_y = (r - case_size_y) / 2;
			Point2D.Float point = new Point2D.Float(p.x - declage_x, p.y
					- declage_y);
			Ellipse2D.Float circle = new Ellipse2D.Float(point.x, point.y, r, r);
			Point2D.Float line1_p1 = new Point2D.Float(p.x - (case_size_x / 2),
					p.y + (case_size_y / 2));
			Point2D.Float line1_p2 = new Point2D.Float(p.x
					+ (3 * case_size_x / 2), p.y + (case_size_y / 2));
			Line2D.Float line1 = new Line2D.Float(line1_p1, line1_p2);
			Point2D.Float line2_p1 = new Point2D.Float(p.x + (case_size_x / 2),
					p.y - (case_size_y / 2));
			Point2D.Float line2_p2 = new Point2D.Float(p.x + (case_size_x / 2),
					p.y + (3 * case_size_y / 2));
			Line2D.Float line2 = new Line2D.Float(line2_p1, line2_p2);

			g2d.setStroke(new BasicStroke(SCOPE_STROKE));
			g2d.setColor(SCOPE_COLOR);
			g2d.draw(line1);
			g2d.draw(line2);
			g2d.draw(circle);
		}
		retriveG2dState(g2d);
	}
	
	/**
	 * Save the g2d state - you can then use retriveG2dState to retrieve it
	 * @param g2d
	 */
	private void saveG2dState(Graphics2D g2d) {
		g2dStroke = g2d.getStroke();
		g2dColor = g2d.getColor();
		g2dComposite = g2d.getComposite();
	}
	/**
	 * Retrieve the g2d state - you have to call saveG2dState before this one
	 * @param g2d
	 */
	private void retriveG2dState(Graphics2D g2d) {
		g2d.setStroke(g2dStroke);
		g2d.setColor(g2dColor);
		g2d.setComposite(g2dComposite);
	}

	

	/*
	 * Getter Setter section
	 */
	private static Point getCoordinateCase(int i, int j) {
		int x = case_size_x * i + BORDER;
		int y = case_size_y * j + BORDER;

		Point down_left_corner = new Point();
		down_left_corner.x = x;
		down_left_corner.y = y;
		return down_left_corner;
	}

	public int[] getCaseFromPosition(Point p) {
		int[] caseInGrid = new int[2];
		int x = p.x - BORDER;
		int y = p.y - BORDER;
		int case_x = x / case_size_x;
		int case_y = y / case_size_y;
		if (case_x < 0 || case_x >= nb_case_x || case_y < 0
				|| case_y >= nb_case_y) {
			case_x = -1;
			case_y = -1;
		}
		caseInGrid[0] = case_x;
		caseInGrid[1] = case_y;
		return caseInGrid;
	}

	public int getState() {
		return state;
	}

	public boolean isMorveAddValid() {
		return morveAddValid;
	}

	public MorveBean getMorve() {
		return morve;
	}

	public void setState(int state, ArrayList<MorveBean> my_table_morve,
			boolean[][] my_table_visibility, boolean[][] my_table_shot) {
		this.my_table_movre = my_table_morve;
		this.my_table_visibility = my_table_visibility;
		this.my_table_shot = my_table_shot;
		this.state = state;
	}

	public static boolean isValideAdd(MorveBean morve,
			ArrayList<MorveBean> list) {
		boolean cross_over = false;

		Point line2_p1 = getCoordinateCase(morve.getStart_case_x(),
				morve.getStart_case_y());
		Point line2_p2 = getCoordinateCase(morve.getEnd_case_x(),
				morve.getEnd_case_y());

		if (morve.getDirection() == 0) {
			line2_p1.y += case_size_y / 2;
			line2_p2.y += case_size_y / 2;
			line2_p1.x += case_size_x / 3;
			line2_p2.x -= case_size_x / 3;
		} else {
			line2_p1.x += case_size_x / 2;
			line2_p2.x += case_size_x / 2;
			line2_p1.y += case_size_y / 3;
			line2_p2.y -= case_size_y / 3;
		}
		Line2D.Float line2 = new Line2D.Float(line2_p1, line2_p2);
		for (MorveBean i : list) {
			Point line1_p1 = getCoordinateCase(i.getStart_case_x(),
					i.getStart_case_y());
			Point line1_p2;
			if (i.getDirection() == 0) {
				line1_p2 = getCoordinateCase(i.getStart_case_x() + i.getSize(),
						i.getStart_case_y());
				line1_p1.y += case_size_x / 2;
				line1_p2.y += case_size_y / 2;
				line1_p1.x += case_size_x / 3;
				line1_p2.x -= case_size_x / 3;
			} else {
				line1_p2 = getCoordinateCase(i.getStart_case_x(),
						i.getStart_case_y() + i.getSize());
				line1_p1.x += case_size_x / 2;
				line1_p2.x += case_size_x / 2;
				line1_p1.y += case_size_y / 3;
				line1_p2.y -= case_size_y / 3;
			}
			Line2D.Float line1 = new Line2D.Float(line1_p1, line1_p2);
			if (line1.intersectsLine(line2)) {
				cross_over = true;
			}

		}
		if (morve.getDirection() == 1) {
			System.out.println("0 : " + morve.getEnd_case_x());
			if (cross_over || (morve.getEnd_case_x() > NUMBER_CASE_X)) {
				return false;
			}
		} else {
			System.out.println("1 : " + morve.getEnd_case_y());
			if (cross_over || (morve.getEnd_case_y() > NUMBER_CASE_Y)) {
				return false;
			}
		}
		return true;
	}
}
