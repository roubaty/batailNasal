package view;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.JFrame;

public class GenericView {
	
    private JFrame frame;
	private ResourceBundle rLabels;
	
	public GenericView(){
        rLabels = ResourceBundle.getBundle("properties/vue_help_fr");
		frame = new JFrame(rLabels.getString("title"));
		// Add a window listner for close button
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setPreferredSize(new Dimension(300, 300));
		frame.pack();
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}

}
