package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import constants.IConstantsGlobal;

public class GenericView implements ActionListener, IConstantsGlobal {

	private JFrame frame;
	private ResourceBundle rLabels;
	private JPanel genericPanel;
	private JPanel contentPanel;
	private JPanel buttonPanel;
	private JTextPane content;
	private JButton button;

	public GenericView(String type, int lang) {
		initFrame(type, lang);
		initComponents();
		frame.getRootPane().revalidate();
	}

	public void initFrame(String type, int lang) {
		if (lang == LANGAGEFR) {
			if (type.equals("ABOUT")) {
				rLabels = ResourceBundle.getBundle("properties/vue_about_fr");
			} else if (type.equals("DONATE")) {
				rLabels = ResourceBundle.getBundle("properties/vue_donate_fr");
			}
		} else if (lang == LANGAGEEN) {
			if (type.equals("ABOUT")) {
				rLabels = ResourceBundle.getBundle("properties/vue_about_en");
			} else if (type.equals("DONATE")) {
				rLabels = ResourceBundle.getBundle("properties/vue_donate_en");
			}
		}
		frame = new JFrame(rLabels.getString("title"));
		frame.setPreferredSize(new Dimension(350, 200));
		frame.pack();
		frame.setLocation(300, 300);
		frame.setVisible(true);
	}

	private JPanel getPanel() {
		if (genericPanel == null) {
			genericPanel = new JPanel();
			contentPanel = new JPanel();
			content = new JTextPane();
			content.setOpaque(false);
			content.setText(rLabels.getString("content"));
			content.setEditable(false);
			contentPanel.add(content, null);
			buttonPanel = new JPanel();
			button = new JButton(rLabels.getString("button"));
			button.addActionListener(this);
			buttonPanel.add(button, null);
			genericPanel.add(contentPanel, null);
			genericPanel.add(buttonPanel, null);
		}
		return genericPanel;
	}

	private void initComponents() {
		frame.add(getPanel(), null);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == button) {
			frame.dispose();
		}
	}

}
