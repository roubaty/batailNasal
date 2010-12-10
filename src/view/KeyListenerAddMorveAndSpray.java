package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListenerAddMorveAndSpray implements KeyListener{
	private boolean crltPress = false;
	@Override
	public void keyPressed(KeyEvent e) {
		crltPress=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		crltPress=false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public boolean isCrltPress() {
		return crltPress;
	}

}
