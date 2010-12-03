package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener  implements MouseListener{
	MainView view;
	public MyMouseListener(MainView view){
		this.view = view;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
