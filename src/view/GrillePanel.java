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
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import constants.IConstantView;
import constants.IConstantsGlobal;

import model.MorveBean;

public class GrillePanel extends JPanel implements IConstantView,
		IConstantsGlobal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nb_case_x = NUMBER_CASE_X;
	private int nb_case_y = NUMBER_CASE_Y;
	private ArrayList<MorveBean> my_table_movre = new ArrayList<MorveBean>();
	private boolean[][] my_table_visibility;
	private boolean[][] my_table_shot;
	private int grid_size_x;
	private int grid_size_y;
	private int case_size_x;
	private int case_size_y;
	private int no_grid;
	private int state;
	private KeyListenerAddMorveAndSpray keyListener;

	public GrillePanel(MainView view, int no_grid,
			KeyListenerAddMorveAndSpray keyListener) {
		this.addMouseListener(new MyMouseListener(view));
		this.keyListener = keyListener;
		nb_case_x=NUMBER_CASE_X;
		nb_case_y=NUMBER_CASE_Y;
		this.no_grid=no_grid;
		my_table_visibility= new boolean[nb_case_x][nb_case_y];
		my_table_shot= new boolean[nb_case_x][nb_case_y];;
	}

	private GridThreadScanAndAddMorve modificationState = new GridThreadScanAndAddMorve(this);

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		initSize();
		setBackground(BACKGROUND_COLOR);
		Graphics2D g2d = (Graphics2D) g;
		drawMorve(g2d);
		drawShot(g2d);
		if(no_grid==1){
			drawVisibility(g2d);
		}
		drawGrid(g2d);
		if (state == TYPESCAN) {
			if(no_grid==1){
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawScan(g2d);
			}
		} else if (state == TYPESPRAY) {
			if(no_grid==1){
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawSpray(g2d);
			}
		} else if (state == TYPEADDMORVE) {
			if(no_grid==0){
				if (!modificationState.isAlive()) {
					modificationState.start();
				}
				drawAddMorve(g2d, 3);
			}
		}
	}

	private void initSize() {
		grid_size_x = (getWidth() - (BORDER * 2));
		grid_size_y = (getHeight() - (BORDER * 2));
		case_size_x = grid_size_x / nb_case_x;
		case_size_y = grid_size_y / nb_case_y;
	}

	private void drawVisibility(Graphics2D g2d) {
		saveG2dState(g2d);
		g2d.setColor(CASE_NONVISIBLE_COLOR);
		for (int i = 0; i < my_table_visibility.length; i++) {
			for (int j = 0; j < my_table_visibility[i].length; j++) {
				if (!my_table_visibility[i][j]) {
					Point down_left_corner = getCoordinateCase(i, j);
					int down_left_corner_x = down_left_corner.x;
					int down_left_corner_y = down_left_corner.y;
					g2d.fillRect(down_left_corner_x, down_left_corner_y,
							case_size_x, case_size_y);
				}
			}
		}
		retriveG2dState(g2d);
	}

	private void drawShot(Graphics2D g2d) {
		saveG2dState(g2d);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke stroke = new BasicStroke(CROSS_STROKE, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		g2d.setStroke(stroke);
		g2d.setColor(CROSS_COLOR);
		for (int i = 0; i < my_table_shot.length; i++) {
			for (int j = 0; j < my_table_shot[i].length; j++) {
				if (my_table_shot[i][j]) {
					Point down_left_corner = getCoordinateCase(i, j);
					int down_left_corner_x = down_left_corner.x;
					int down_left_corner_y = down_left_corner.y;
					int down_right_corner_x = down_left_corner_x + case_size_x;
					int down_right_corner_y = down_left_corner_y;
					int up_left_corner_x = down_left_corner_x;
					int up_left_corner_y = down_left_corner_y + case_size_y;
					int up_right_corner_x = down_left_corner_x + case_size_x;
					int up_right_corner_y = down_left_corner_y + case_size_y;
					g2d.drawLine(down_right_corner_x, down_right_corner_y,
							up_left_corner_x, up_left_corner_y);
					g2d.drawLine(down_left_corner_x, down_left_corner_y,
							up_right_corner_x, up_right_corner_y);
				}
			}
		}
		retriveG2dState(g2d);
	}

	private void drawScan(Graphics2D g2d) {
		saveG2dState(g2d);
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		this.getLocationOnScreen();
		SwingUtilities.convertPointFromScreen(p, this);
		int[] cas = getCaseFromPosition(p);
		if (cas[0] != -1) {
			p = getCoordinateCase(cas[0], cas[1]);
			int x = p.x - (((SCAN_SIZE_X - 1) / 2)) * case_size_x;
			int y = p.y - ((SCAN_SIZE_Y - 1) / 2) * case_size_y;
			int w = case_size_x * SCAN_SIZE_X;
			int h = case_size_y * SCAN_SIZE_Y;
			Rectangle2D.Float rec = new Rectangle2D.Float(x, y, w, h);
			Rectangle2D.Float rec2 = new Rectangle2D.Float(p.x
					- (2 * case_size_x), y + case_size_y, 5 * case_size_x,
					case_size_y);
			Rectangle2D.Float rec3 = new Rectangle2D.Float(p.x, y
					- (case_size_y), case_size_x, 5 * case_size_y);
			g2d.setStroke(new BasicStroke(SCAN_STROKE));
			g2d.setColor(SCAN_COLOR);
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			g2d.fill(rec);
			g2d.fill(rec2);
			g2d.fill(rec3);
		}
		retriveG2dState(g2d);
	}

	private void drawAddMorve(Graphics2D g2d, int morve_size) {
		saveG2dState(g2d);
		boolean ctrl = keyListener.isCrltPress();
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		this.getLocationOnScreen();
		SwingUtilities.convertPointFromScreen(p, this);
		int[] cas = getCaseFromPosition(p);
		if (cas[0] != -1) {
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			Image img;
			int image_origine_size_x;
			int image_origine_size_y;
			MorveBean morve;
			if (ctrl) {
				morve = new MorveBean(cas[0], cas[1], morve_size, 0);
			} else {
				morve = new MorveBean(cas[0], cas[1], morve_size, 1);
			}
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
			if (ctrl) {
				drawAMorveVertically(g2d, morve, img, image_origine_size_x,
						image_origine_size_y);
			} else {
				drawAMorveHorivontally(g2d, morve, img, image_origine_size_x,
						image_origine_size_y);
			}
			boolean cross_over = false;
			
			Point line2_p1 = getCoordinateCase(cas[0], cas[1]);
			Point line2_p2;
			
			if (!ctrl) {
				line2_p2 = getCoordinateCase(cas[0]+morve_size, cas[1]);
				line2_p1.y += case_size_y/2;
				line2_p2.y += case_size_y/2;
				line2_p1.x += case_size_x/3;
				line2_p2.x -= case_size_x/3;
			} else {
				line2_p1.x += case_size_x/2;
				line2_p2 = getCoordinateCase(cas[0], cas[1]+morve_size);
				line2_p2.x += case_size_x/2;
				line2_p1.y += case_size_y/3;
				line2_p2.y -= case_size_y/3;
			}
			Line2D.Float line2 = new Line2D.Float(line2_p1, line2_p2);
			for (MorveBean i : my_table_movre) {
				Point line1_p1 = getCoordinateCase(i.getStart_case_x(),
						i.getStart_case_y());
				Point line1_p2;
				if (i.getDirection() == 0) {
					line1_p2 = getCoordinateCase(i.getStart_case_x() + i.getSize(),
							i.getStart_case_y());
					line1_p1.y += case_size_y/2;
					line1_p2.y += case_size_y/2;
					line1_p1.x += case_size_x/3;
					line1_p2.x -= case_size_x/3;
				} else {
					line1_p2 = getCoordinateCase(i.getStart_case_x(),
							i.getStart_case_y() + i.getSize());
					line1_p1.x += case_size_x/2;
					line1_p2.x += case_size_x/2;
					line1_p1.y += case_size_y/3;
					line1_p2.y -= case_size_y/3;
				}
				Line2D.Float line1 = new Line2D.Float(line1_p1, line1_p2);
				if (line1.intersectsLine(line2)) {
					cross_over = true;
				}

			}
			if (!ctrl) {
				if (cross_over || (cas[0] + morve_size > nb_case_x)) {
					Point point = getCoordinateCase(cas[0], cas[1]);
					g2d.fill(new Rectangle2D.Float(point.x, point.y,
							case_size_x * morve_size, case_size_y));
				}
			} else {
				if (cross_over || (cas[1] + morve_size > nb_case_y)) {
					cross_over = false;
					Point point = getCoordinateCase(cas[0], cas[1]);
					g2d.fill(new Rectangle2D.Float(point.x, point.y,
							case_size_x, case_size_y * morve_size));
				}
			}
		}
		retriveG2dState(g2d);
	}

	private Random rnd = new Random();

	private void drawSpray(Graphics2D g2d) {
		saveG2dState(g2d);
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		this.getLocationOnScreen();
		SwingUtilities.convertPointFromScreen(p, this);
		int[] cas = getCaseFromPosition(p);
		if (cas[0] != -1) {
			p = getCoordinateCase(cas[0], cas[1]);

			int x = p.x - (((SCAN_SIZE_X - 1) / 2)) * case_size_x;
			int y = p.y;
			int w = case_size_x * SCAN_SIZE_X;
			int h = case_size_y;
			int x2 = p.x;
			int y2 = p.y - (((SCAN_SIZE_Y - 1) / 2)) * case_size_y;
			int w2 = case_size_x;
			int h2 = case_size_y * SCAN_SIZE_X;
			
			g2d.setStroke(new BasicStroke(SCAN_STROKE));
			g2d.setColor(SCAN_COLOR);
			g2d.setComposite(java.awt.AlphaComposite.getInstance(
					java.awt.AlphaComposite.SRC_OVER,
					Math.min(SCAN_TRANSPARENCE, 1.0f)));
			g2d.setStroke(new BasicStroke(1));
			for (int i = 0; i < 2000; i++) {
				int pos_x = rnd.nextInt(w) + x;
				int pos_y = rnd.nextInt(h) + y;
				g2d.drawLine(pos_x, pos_y, pos_x, pos_y);
			}
			for (int i = 0; i < 2000; i++) {
				int pos_x = rnd.nextInt(w2) + x2;
				int pos_y = rnd.nextInt(h2) + y2;
				g2d.drawLine(pos_x, pos_y, pos_x, pos_y);
			}
		}
		retriveG2dState(g2d);
	}

	private Stroke g2dStroke;
	private Color g2dColor;
	private Composite g2dComposite;

	private void saveG2dState(Graphics2D g2d) {
		g2dStroke = g2d.getStroke();
		g2dColor = g2d.getColor();
		g2dComposite = g2d.getComposite();
	}

	private void retriveG2dState(Graphics2D g2d) {
		g2d.setStroke(g2dStroke);
		g2d.setColor(g2dColor);
		g2d.setComposite(g2dComposite);
	}

	private Point getCoordinateCase(int i, int j) {
		int x = case_size_x * i + BORDER;
		int y = case_size_y * j + BORDER;

		Point down_left_corner = new Point();
		down_left_corner.x = x;
		down_left_corner.y = y;
		return down_left_corner;
	}

	private void drawMorve(Graphics2D g2d) {
		saveG2dState(g2d);
		for (MorveBean i : my_table_movre) {
			Image img;
			int image_origine_size_x;
			int image_origine_size_y;
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
				drawAMorveHorivontally(g2d, i, img, image_origine_size_x,
						image_origine_size_y);
			} else {
				drawAMorveVertically(g2d, i, img, image_origine_size_x,
						image_origine_size_y);
			}
		}
		retriveG2dState(g2d);
	}

	private void drawAMorveVertically(Graphics2D g2d, MorveBean morve,
			Image img, double image_origine_size_x, double image_origine_size_y) {
		saveG2dState(g2d);
		Point p = getCoordinateCase(morve.getStart_case_x(),
				morve.getStart_case_y());
		int x = p.x + case_size_x;
		int y = p.y;
		int width;
		int height;
		width = case_size_y * morve.getSize();
		height = case_size_x;

		double scale_x = width / (double) image_origine_size_x;
		double scale_y = height / (double) image_origine_size_y;

		AffineTransform af = AffineTransform.getTranslateInstance(x, y);
		af.rotate(ANGLE_90);
		af.concatenate(AffineTransform.getScaleInstance(scale_x, scale_y));

		g2d.drawImage(img, af, null);
		retriveG2dState(g2d);
	}

	private void drawAMorveHorivontally(Graphics2D g2d, MorveBean morve,
			Image img, double image_origine_size_x, double image_origine_size_y) {
		saveG2dState(g2d);
		Point p = getCoordinateCase(morve.getStart_case_x(),
				morve.getStart_case_y());
		int x = p.x;
		int y = p.y;
		int width;
		int height;
		width = case_size_x * morve.getSize();
		height = case_size_y;

		double scale_x = width / (double) image_origine_size_x;
		double scale_y = height / (double) image_origine_size_y;

		AffineTransform af = AffineTransform.getTranslateInstance(x, y);

		af.concatenate(AffineTransform.getScaleInstance(scale_x, scale_y));
		g2d.drawImage(img, af, null);
		retriveG2dState(g2d);
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

	public void setState(int state, ArrayList<MorveBean> my_table_morve,
			boolean[][] my_table_visibility, boolean[][] my_table_shot) {
		this.my_table_movre = my_table_morve;
		this.my_table_visibility = my_table_visibility;
		this.my_table_shot = my_table_shot;
		this.state = state;
	}
}
