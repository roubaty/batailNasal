package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class GrillePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int BORDER = 10;
	private int nb_case_x = 10;
	private int nb_case_y = 10;
	public GrillePanel(int nb_cases_x, int nb_cases_y) {
		nb_case_x=nb_cases_x;
		nb_case_y=nb_cases_y;
	}
	public GrillePanel(MainView view){
		this.addMouseListener(new MyMouseListener(view));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
		Graphics2D g2d = (Graphics2D) g;
		printGrille(g2d);
	}
	private void printGrille(Graphics2D g2d){
		int size_x = getWidth();
		int size_y = getHeight();
		AffineTransform af = AffineTransform.getTranslateInstance(0,size_y);
		af.concatenate(AffineTransform.getScaleInstance(size_x,-size_y));
		g2d.setPaint(Color.BLACK);
		Line2D.Float line;
		int spacing_x = (size_x-(2*BORDER))/nb_case_x;
		int spacing_y = (size_y-(2*BORDER))/nb_case_y;
		for (int i = 0; i < nb_case_y+1; i++) {
			int pos_y = BORDER+(i*spacing_y);
			line = new Line2D.Float(BORDER, pos_y, size_x-BORDER , pos_y);
			g2d.draw(line);
		}
		for (int i = 0; i < nb_case_x+1; i++) {
			int pos_x = BORDER+(i*spacing_x);
			line = new Line2D.Float(pos_x, BORDER, pos_x, size_y-BORDER);
			g2d.draw(line);
		}
	}
}
