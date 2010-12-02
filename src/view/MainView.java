/*
 * DisplayViewPanel.java
 *
 * Created on January 22, 2007, 2:36 PM
 */

package view;

import controller.Controller;
import controller.IController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class MainView implements ActionListener, Observer, IView
{
    //  The components used by this view
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
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JLabel jLabel1 = null;
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
	private JPanel grid1Panel;
	private JPanel grid2Panel;
	private ResourceBundle rLabels;
    
    /**
     * Creates new form TextElementDisplayPanel
     * @param controller2 An object implenting the controller interface that
     *        this view can use to process GUI actions
     */
    public MainView(IController controller2) {
        
        this.controller = controller2;
        
        rLabels = ResourceBundle.getBundle("properties/vue_principale_fr");
        
        initFrame();
        initComponents();
        localInitialization();
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
		frame.setPreferredSize(new Dimension(800, 550));
		frame.pack();
		frame.setVisible(true);
    }

    /**
     * Initialization method called from the constructor
     */
    public void localInitialization() {
        
    }
    
    // </editor-fold>
     
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
    	frame.setJMenuBar(menuBar);
		frame.add(getBattlePanel(), null);
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
			infoPanel.setBounds(new Rectangle(5, 110, 760, 50));
			infoLabel = new JLabel();
			infoLabel.setBounds(new Rectangle(10, 120, 750, 40));
			infoLabel.setFont(new Font("Serif", Font.BOLD, 20));
			infoPanel.add(infoLabel, null);
			grid1Panel = new JPanel();
			grid1Panel.setBounds(new Rectangle(5, 165, 360, 310));
			grid1Panel.setBorder(BorderFactory.createRaisedBevelBorder());
			grid2Panel = new JPanel();
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
		}
		return battlePanel;
	}

	private void changeLabels(){
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
		
		if(source == menuItemQuit){
			System.exit(0);
		} else if (source == menuItemFR){
			rLabels = ResourceBundle.getBundle("properties/vue_principale_fr");
			changeLabels();
			System.out.println("FR");
		} else if (source == menuItemEN){
			rLabels = ResourceBundle.getBundle("properties/vue_principale_en");
			changeLabels();
			System.out.println("EN");
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
				
	}    
}