/*
 * DisplayViewPanel.java
 *
 * Created on January 22, 2007, 2:36 PM
 */

package view;

import constants.IConstantsGlobal;
import controller.IController;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

import javax.swing.*;

import model.GameBean;

public class SecondaryView implements ActionListener, IView, IConstantsGlobal {
	// The components used by this view
	private IController controller;
	private JFrame frame;
	private ResourceBundle rLabels;
	private JPanel logPanel = null;
	private TextArea logArea;
	private JScrollPane scroll;
	private JLabel imgLabel;
	private JPanel secondaryPanel = null;

	/**
	 * Creates new form TextElementDisplayPanel
	 * 
	 * @param controller2
	 *            An object implenting the controller interface that this view
	 *            can use to process GUI actions
	 */
	public SecondaryView(IController controller2) {

		this.controller = controller2;

		rLabels = ResourceBundle.getBundle("properties/vue_secondaire_fr");

		initFrame();
		initComponents();
		localInitialization();
		frame.getRootPane().revalidate();
	}

	/**
	 * Initialization method called from the constructor to init the frame
	 */
	public void initFrame() {
		frame = new JFrame(rLabels.getString("title"));
		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setPreferredSize(new Dimension(700, 250));
		frame.pack();
		frame.setLocation(50, 550);
		frame.setVisible(true);
	}

	private JPanel getSecondaryPanel() {
		if (secondaryPanel == null) {
			secondaryPanel = new JPanel();
			logArea = new TextArea();
			logArea.setEditable(false);
			logArea.setBounds(new Rectangle(0, 0, 400, 200));
			imgLabel = new JLabel();
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/init.gif")));
			logPanel = new JPanel();
			logPanel.setBounds(new Rectangle(400, 0, 200, 200));
			logPanel.add(imgLabel, null);
			secondaryPanel.add(logArea, null);
			secondaryPanel.add(logPanel, null);
		}
		return secondaryPanel;
	}

	/**
	 * Initialization method called from the constructor
	 */
	public void localInitialization() {

	}

	// </editor-fold>

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		frame.add(getSecondaryPanel(), null);
	}

	private void changeLabels() {
		frame.setTitle(rLabels.getString("title"));
	}

	public void writeLog(String log) {
		String text = logArea.getText();
		text = text + "\n" + log;
		logArea.setText(text);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		/*
		 * if(source == menuItemQuit){ System.exit(0); }
		 */
	}

	@Override
	public void update(Observable obs, Object obj) {
		// Affichage des log
		GameBean gb = (GameBean) obj;
		switch (gb.getState()) {
		/*
		 * case 19: writeLog(rLabels.getString("msg_new")); imgLabel.setIcon(new
		 * ImageIcon(getClass().getResource(
		 * "../ressources/pictures/bonus_spray.gif"))); break; case 2:
		 * writeLog(rLabels.getString("msg_player_fail")); break; case 3:
		 * writeLog(rLabels.getString("msg_player_touch")); break; case 4:
		 * writeLog(rLabels.getString("msg_player_kill")); break;
		 */
		case TYPEIAPLAYED:
			if (gb.isIaTouched()) {
				writeLog(rLabels.getString("msg_ia_touch") + " -> "
						+ gb.getIaCoordX() + " ; " + gb.getIaCoordY());
			} else {
				writeLog(rLabels.getString("msg_ia_fail"));
			}
			break;
		case TYPEPLAYERPLAYED:
			if (gb.isPlayerTouched()) {
				writeLog(rLabels.getString("msg_player_touch") + " -> "
						+ gb.getPlayerCoordX() + " ; " + gb.getPlayerCoordY());
			} else {
				writeLog(rLabels.getString("msg_player_fail"));
			}
			break;
		case TYPENORMAL:
			writeLog(rLabels.getString("msg_player_got_normal_shot"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/normal.gif")));
			break;
		case TYPEIANORMAL:
			writeLog(rLabels.getString("msg_ia_got_normal_shot"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/normal.gif")));
			break;
		case TYPETRIPLE:
			writeLog(rLabels.getString("msg_player_got_bonus_triple"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/bonus_triple_shot.gif")));
			break;
		case TYPESPRAY:
			writeLog(rLabels.getString("msg_player_got_bonus_spray"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/bonus_spray.gif")));
			break;
		case TYPESCAN:
			writeLog(rLabels.getString("msg_player_got_bonus_radar"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/bonus_debusquage.gif")));
			break;
		/*
		 * case 8: writeLog(rLabels.getString("msg_player_use_bonus_triple"));
		 * break; case 9:
		 * writeLog(rLabels.getString("msg_player_use_bonus_spray")); break;
		 * case 10: writeLog(rLabels.getString("msg_player_use_bonus_radar"));
		 * break; case 11:
		 * writeLog(rLabels.getString("msg_player_use_bonus_add")); break; case
		 * 12: writeLog(rLabels.getString("msg_ia_fail")); break; case 13:
		 * writeLog(rLabels.getString("msg_ia_touch")); break; case 14:
		 * writeLog(rLabels.getString("msg_ia_kill")); break;
		 */
		case TYPEIATRIPLE:
			writeLog(rLabels.getString("msg_ia_got_bonus_triple"));
			break;
		case TYPEIASPRAY:
			writeLog(rLabels.getString("msg_ia_got_bonus_spray"));
			break;
		case TYPEIASCAN:
			writeLog(rLabels.getString("msg_ia_got_bonus_radar"));
			break;
		case TYPEIAADDMORVE:
			writeLog(rLabels.getString("msg_ia_got_bonus_add"));
			break;
		/*
		 * case 19: writeLog(rLabels.getString("msg_ia_use_bonus_triple"));
		 * break; case 20:
		 * writeLog(rLabels.getString("msg_ia_use_bonus_spray")); break; case
		 * 21: writeLog(rLabels.getString("msg_ia_use_bonus_radar")); break;
		 * case 22: writeLog(rLabels.getString("msg_ia_use_bonus_add")); break;
		 */
		case TYPEPLAYERWIN:
			writeLog(rLabels.getString("msg_player_win"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/you_win.gif")));
			break;
		/*
		 * case 24: writeLog(rLabels.getString("msg_player_use_bonus_radar"));
		 * break;
		 */
		case TYPEIAWIN:
			writeLog(rLabels.getString("msg_player_loose"));
			imgLabel.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/you_lose.gif")));
			break;

		case TYPECHANGELANGAGE:
			if (gb.getLangage() == LANGAGEEN) {
				rLabels = ResourceBundle
						.getBundle("properties/vue_secondaire_en");
				changeLabels();
			} else if (gb.getLangage() == LANGAGEFR) {
				rLabels = ResourceBundle
						.getBundle("properties/vue_secondaire_fr");
				changeLabels();
			}
			break;
		}
		frame.getRootPane().revalidate();
	}
}
