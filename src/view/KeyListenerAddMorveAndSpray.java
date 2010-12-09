package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerAddMorveAndSpray implements KeyListener{
	private boolean crltPress = false;
	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_CONTROL){
			crltPress=!crltPress;
			
		}
		System.out.println("press");
	}

	public boolean isCrltPress() {
		return crltPress;
	}

}
