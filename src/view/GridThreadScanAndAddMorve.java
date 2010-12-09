package view;

public class GridThreadScanAndAddMorve extends Thread {
	GridPanel grid;

	public GridThreadScanAndAddMorve(GridPanel grid) {
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
