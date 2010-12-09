package view;

public class GridThreadScanAndAddMorve extends Thread {
	GrillePanel grid;

	public GridThreadScanAndAddMorve(GrillePanel grid) {
		this.grid = grid;
	}

	public void run() {
		while (true) {
			grid.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
