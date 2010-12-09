package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerAddMorveAndSpray implements KeyListener{
	private boolean crltPress = false;
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==17){
			crltPress=true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==17){
			crltPress=false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public boolean isCrltPress() {
		return crltPress;
	}

}
