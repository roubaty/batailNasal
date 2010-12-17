/*
 * DisplayViewPanel.java
 *
 * Created on January 22, 2007, 2:36 PM
 */

package view;

import action.*;
import constants.IConstantsGlobal;
import controller.IController;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.GameBean;
import model.MorveBean;

public class MainView implements ActionListener, Observer, IView,
		IConstantsGlobal {
	// The components used by this view
	private IController controller;
	private JFrame frame;
	private JMenuBar menuBar;
	private JMenu menuNasalBattle;
	private JMenu menuLanguages;
	private JMenu menuHelp;
	private JMenuItem menuItemNew;
	private JMenuItem menuItemQuit;
	private JMenuItem menuItemFR;
	private JMenuItem menuItemEN;
	private JMenuItem menuItemAbout;
	private JMenuItem menuItemDonate;
	private JPanel battlePanel = null;
	private JLabel p1NameLabel = null;
	private JLabel p1InfoLabel = null;
	private JLabel p1BigMorveLabel = null;
	private JLabel p1MidMorveLabel = null;
	private JLabel p1LittleMorveLabel = null;
	private JLabel p1NoBigMorves = null;
	private JLabel p1NoMidMorves = null;
	private JLabel p1NoLittleMorves = null;
	private JLabel p2NameLabel = null;
	private JLabel p2InfoLabel = null;
	private JLabel p2BigMorveLabel = null;
	private JLabel p2MidMorveLabel = null;
	private JLabel p2LittleMorveLabel = null;
	private JLabel p2NoBigMorves = null;
	private JLabel p2NoMidMorves = null;
	private JLabel p2NoLittleMorves = null;
	private JPanel p1Panel = null;
	private JPanel p2Panel = null;
	private JLabel infoLabel;
	private JPanel infoPanel;
	private GridPanel grid1Panel;
	private GridPanel grid2Panel;
	private ResourceBundle rLabels;
	private JButton soundButton;
	private static int lang;

	/**
	 * Creates new form TextElementDisplayPanel
	 * 
	 * @param controller2
	 *            An object implenting the controller interface that this view
	 *            can use to process GUI actions
	 */
	public MainView(IController controller2) {

		this.controller = controller2;

		rLabels = ResourceBundle.getBundle("properties/vue_principale_fr");
		lang = LANGAGEFR;
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

		frame.setPreferredSize(new Dimension(780, 540));

		frame.pack();
		frame.setVisible(true);
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
		menuBar = new JMenuBar();
		menuNasalBattle = new JMenu(rLabels.getString("menu1"));
		menuBar.add(menuNasalBattle);
		menuItemNew = new JMenuItem(rLabels.getString("new"));
		menuItemNew.addActionListener(this);
		menuNasalBattle.add(menuItemNew);
		menuItemQuit = new JMenuItem(rLabels.getString("exit"));
		menuItemQuit.addActionListener(this);
		menuNasalBattle.add(menuItemQuit);
		menuLanguages = new JMenu(rLabels.getString("menu2"));
		menuBar.add(menuLanguages);
		menuItemFR = new JMenuItem(rLabels.getString("fr"));
		menuItemFR.addActionListener(this);
		menuLanguages.add(menuItemFR);
		menuItemEN = new JMenuItem(rLabels.getString("en"));
		menuItemEN.addActionListener(this);
		menuLanguages.add(menuItemEN);
		menuHelp = new JMenu(rLabels.getString("menu3"));
		menuBar.add(menuHelp);
		menuItemAbout = new JMenuItem(rLabels.getString("about"));
		menuItemAbout.addActionListener(this);
		menuHelp.add(menuItemAbout);
		menuItemDonate = new JMenuItem(rLabels.getString("donate"));
		menuItemDonate.addActionListener(this);
		menuHelp.add(menuItemDonate);
		menuHelp.addActionListener(this);
		frame.setJMenuBar(menuBar);
		frame.add(getBattlePanel(), null);
		frame.repaint();
	}

	private JPanel getBattlePanel() {
		if (battlePanel == null) {
			p2NoLittleMorves = new JLabel();
			p2NoLittleMorves.setBounds(new Rectangle(690, 75, 40, 20));
			p2NoLittleMorves.setText("Z");
			p2NoMidMorves = new JLabel();
			p2NoMidMorves.setBounds(new Rectangle(690, 55, 40, 20));
			p2NoMidMorves.setText("Y");
			p2NoBigMorves = new JLabel();
			p2NoBigMorves.setBounds(new Rectangle(690, 35, 40, 20));
			p2NoBigMorves.setText("X");
			p2LittleMorveLabel = new JLabel();
			p2LittleMorveLabel.setBounds(new Rectangle(570, 75, 110, 20));
			p2LittleMorveLabel.setText(rLabels.getString("text_little"));
			p2MidMorveLabel = new JLabel();
			p2MidMorveLabel.setBounds(new Rectangle(570, 55, 120, 20));
			p2MidMorveLabel.setText(rLabels.getString("text_middle"));
			p2BigMorveLabel = new JLabel();
			p2BigMorveLabel.setBounds(new Rectangle(570, 35, 120, 20));
			p2BigMorveLabel.setText(rLabels.getString("text_big"));
			p2InfoLabel = new JLabel();
			p2InfoLabel.setBounds(new Rectangle(570, 10, 150, 20));
			p2InfoLabel.setText(rLabels.getString("text_morve"));
			p2NameLabel = new JLabel();
			p2NameLabel.setBounds(new Rectangle(420, 30, 120, 40));
			p2NameLabel.setText("Player 2");
			p2NameLabel.setFont(new Font("Serif", Font.BOLD, 24));
			p1NoBigMorves = new JLabel();
			p1NoBigMorves.setBounds(new Rectangle(290, 35, 40, 20));
			p1NoBigMorves.setText("X");
			p1NoMidMorves = new JLabel();
			p1NoMidMorves.setBounds(new Rectangle(290, 55, 40, 20));
			p1NoMidMorves.setText("Y");
			p1NoLittleMorves = new JLabel();
			p1NoLittleMorves.setBounds(new Rectangle(290, 75, 40, 20));
			p1NoLittleMorves.setText("Z");
			p1LittleMorveLabel = new JLabel();
			p1LittleMorveLabel.setBounds(new Rectangle(170, 75, 110, 20));
			p1LittleMorveLabel.setText(rLabels.getString("text_little"));
			p1MidMorveLabel = new JLabel();
			p1MidMorveLabel.setBounds(new Rectangle(170, 55, 120, 20));
			p1MidMorveLabel.setText(rLabels.getString("text_middle"));
			p1BigMorveLabel = new JLabel();
			p1BigMorveLabel.setBounds(new Rectangle(170, 35, 120, 20));
			p1BigMorveLabel.setText(rLabels.getString("text_big"));
			p1InfoLabel = new JLabel();
			p1InfoLabel.setBounds(new Rectangle(170, 10, 150, 20));
			p1InfoLabel.setText(rLabels.getString("text_morve"));
			p1NameLabel = new JLabel();
			p1NameLabel.setBounds(new Rectangle(20, 30, 120, 40));
			p1NameLabel.setText("Player 1");
			p1NameLabel.setFont(new Font("Serif", Font.BOLD, 24));
			p1Panel = new JPanel();
			p1Panel.setLayout(new GridBagLayout());
			p1Panel.setBounds(new Rectangle(5, 5, 360, 100));
			Border raisedbevel = BorderFactory.createRaisedBevelBorder();
			p1Panel.setBorder(raisedbevel);
			p2Panel = new JPanel();
			p2Panel.setLayout(new GridBagLayout());
			p2Panel.setBounds(new Rectangle(405, 5, 360, 100));
			p2Panel.setBorder(raisedbevel);
			infoPanel = new JPanel();
			infoPanel.setBorder(raisedbevel);
			infoPanel.setBounds(new Rectangle(65, 110, 700, 50));
			infoLabel = new JLabel();
			infoLabel.setBounds(new Rectangle(70, 120, 690, 40));
			infoLabel.setFont(new Font("Serif", Font.BOLD, 20));
			infoPanel.add(infoLabel, null);
			soundButton = new JButton();
			soundButton.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/soundOn.gif")));
			soundButton.setBounds(new Rectangle(5, 110, 50, 50));
			soundButton.addActionListener(this);
			KeyListenerAddMorveAndSpray listener = new KeyListenerAddMorveAndSpray();
			frame.addKeyListener(listener);
			grid1Panel = new GridPanel(this, 0, listener);
			grid1Panel.setBounds(new Rectangle(5, 165, 360, 310));
			grid1Panel.setBorder(BorderFactory.createRaisedBevelBorder());
			grid2Panel = new GridPanel(this, 1, listener);
			grid2Panel.setBounds(new Rectangle(405, 165, 360, 310));
			grid2Panel.setBorder(BorderFactory.createRaisedBevelBorder());
			battlePanel = new JPanel();
			battlePanel.setLayout(null);
			battlePanel.setSize(new Dimension(800, 550));
			battlePanel.add(p1NameLabel, null);
			battlePanel.add(p1InfoLabel, null);
			battlePanel.add(p1BigMorveLabel, null);
			battlePanel.add(p1MidMorveLabel, null);
			battlePanel.add(p1LittleMorveLabel, null);
			battlePanel.add(p1NoBigMorves, null);
			battlePanel.add(p1NoMidMorves, null);
			battlePanel.add(p1NoLittleMorves, null);
			battlePanel.add(p2NameLabel, null);
			battlePanel.add(p2InfoLabel, null);
			battlePanel.add(p2BigMorveLabel, null);
			battlePanel.add(p2MidMorveLabel, null);
			battlePanel.add(p2LittleMorveLabel, null);
			battlePanel.add(p2NoBigMorves, null);
			battlePanel.add(p2NoMidMorves, null);
			battlePanel.add(p2NoLittleMorves, null);
			battlePanel.add(p1Panel, null);
			battlePanel.add(p2Panel, null);
			battlePanel.add(infoPanel, null);
			battlePanel.add(grid1Panel, null);
			battlePanel.add(grid2Panel, null);
			battlePanel.add(soundButton, null);
		}
		return battlePanel;
	}

	private void changeLabels() {
		frame.setTitle(rLabels.getString("title"));
		menuNasalBattle.setText(rLabels.getString("menu1"));
		menuItemNew.setText(rLabels.getString("new"));
		menuItemQuit.setText(rLabels.getString("exit"));
		menuLanguages.setText(rLabels.getString("menu2"));
		menuItemFR.setText(rLabels.getString("fr"));
		menuItemEN.setText(rLabels.getString("en"));
		menuHelp.setText(rLabels.getString("menu3"));
		menuItemAbout.setText(rLabels.getString("about"));
		menuItemDonate.setText(rLabels.getString("donate"));
		p2LittleMorveLabel.setText(rLabels.getString("text_little"));
		p2MidMorveLabel.setText(rLabels.getString("text_middle"));
		p2BigMorveLabel.setText(rLabels.getString("text_big"));
		p2InfoLabel.setText(rLabels.getString("text_morve"));
		p1LittleMorveLabel.setText(rLabels.getString("text_little"));
		p1MidMorveLabel.setText(rLabels.getString("text_middle"));
		p1BigMorveLabel.setText(rLabels.getString("text_big"));
		p1InfoLabel.setText(rLabels.getString("text_morve"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == menuItemQuit) {
			System.exit(0);
		} else if (source == menuItemFR) {
			lang = LANGAGEFR;
			ActionLangage actionLangage = new ActionLangage(LANGAGEFR);
			controller.actionOccured(actionLangage);
		} else if (source == menuItemEN) {
			lang = LANGAGEEN;
			ActionLangage actionLangage = new ActionLangage(LANGAGEEN);
			controller.actionOccured(actionLangage);
		} else if (source == soundButton) {
			ActionSound actionSound = new ActionSound();
			controller.actionOccured(actionSound);
		} else if (source == menuItemAbout) {
			GenericView aboutView = new GenericView("ABOUT", lang);
		} else if (source == menuItemDonate) {
			GenericView donateView = new GenericView("DONATE", lang);
		}else if(source == menuItemNew){
			ActionNewGame actionNewGame = new ActionNewGame();
			controller.actionOccured(actionNewGame);
		}
	}

	public void update(Observable obs, Object bean) {
		GameBean obj = (GameBean) bean;
		if (obj.getLangage() == LANGAGEEN) {
			rLabels = ResourceBundle.getBundle("properties/vue_principale_en");
			changeLabels();
		} else if (obj.getLangage() == LANGAGEFR) {
			rLabels = ResourceBundle.getBundle("properties/vue_principale_fr");
			changeLabels();
		}
		if (obj.getSound() == SOUNDSON) {
			soundButton.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/soundOn.gif")));

		} else if (obj.getSound() == SOUNDSOFF) {
			soundButton.setIcon(new ImageIcon(getClass().getResource(
					"../ressources/pictures/soundOff.gif")));
		}
		switch (obj.getMsg()) {

		case MSGPLAYERTURN:
			infoLabel.setText(rLabels.getString("msg_player_turn"));
			break;
		case MSGIATURN:
			infoLabel.setText(rLabels.getString("msg_ia_turn"));
			break;
		case MSGPLAYERGOOD:
			infoLabel.setText(rLabels.getString("msg_player_good"));
			break;
		case MSGPLAYERBAD:
			infoLabel.setText(rLabels.getString("msg_player_bad"));
			break;
		case MSGPLAYERBONUSTRIPLE:
			infoLabel.setText(rLabels.getString("msg_player_bonus_triple"));
			break;
		case MSGPLAYERBONUSSPRAY:
			infoLabel.setText(rLabels.getString("msg_player_bonus_spray"));
			break;
		case MSGPLAYERBONUSADD:
			infoLabel.setText(rLabels.getString("msg_player_bonus_add"));
			break;
		case MSGPLAYERBONUSRADAR:
			infoLabel.setText(rLabels.getString("msg_player_bonus_radar"));
			break;
		case MSGPLAYERWIN:
			infoLabel.setText(rLabels.getString("msg_player_win"));
			break;
		case MSGPLAYERLOOSE:
			infoLabel.setText(rLabels.getString("msg_player_loose"));
			break;
		case MSGIABONUSTRIPLE:
			infoLabel.setText(rLabels.getString("msg_ia_bonus_triple"));
			break;
		case MSGIABONUSSPRAY:
			infoLabel.setText(rLabels.getString("msg_ia_bonus_spray"));
			break;
		case MSGIABONUSADD:
			infoLabel.setText(rLabels.getString("msg_ia_bonus_add"));
			break;
		case MSGIABONUSRADAR:
			infoLabel.setText(rLabels.getString("msg_ia_bonus_radar"));
			break;
		}
		frame.getRootPane().revalidate();
		grid1Panel.setState(obj.getState(), obj.getPlayerTableMorve(),grid1Panel.my_table_visibility, obj.getPlayerTableShot(),obj.getSizeMorve());
		grid2Panel.setState(obj.getState(), obj.getIaTableMorve(), obj.getVisible(), obj.getIaTableShot(),obj.getSizeMorve());
	}

	public void actionFromGridPanel(Point point) {
		int[] pos = grid1Panel.getCaseFromPosition(point);
		switch (grid1Panel.getState()) {
		case TYPEADDMORVE_START:
		case TYPEADDMORVE:
			if (grid1Panel.isMorveAddValid()) {
				MorveBean morve = grid1Panel.getMorve();
				System.out.println(morve);
				ActionPlace action = new ActionPlace(morve.getStart_case_x(),
						morve.getStart_case_y(), morve.getEnd_case_x(),
						morve.getEnd_case_y());
				controller.actionOccured(action);
			}
			break;
		case TYPESCAN:
			ActionShot actionScan = new ActionShot(TYPESCAN, pos[0], pos[1]);
			controller.actionOccured(actionScan);
			break;
		case TYPESPRAY:
			ActionShot actionSpray = new ActionShot(TYPESPRAY, pos[0], pos[1]);
			controller.actionOccured(actionSpray);
			break;
		case TYPENORMAL:
			ActionShot actionShot = new ActionShot(TYPENORMAL, pos[0], pos[1]);
			controller.actionOccured(actionShot);
			break;
		case TYPETRIPLE:
			ActionShot actionTripleShot = new ActionShot(TYPETRIPLE, pos[0],
					pos[1]);
			controller.actionOccured(actionTripleShot);
			break;
		default:
			break;
		}
	}
}
