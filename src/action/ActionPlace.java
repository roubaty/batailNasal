package action;

public class ActionPlace extends Action {

	private final int startX;
	private final int startY;
	private final int endX;
	private final int endY;
	
	public ActionPlace(int startX, int startY, int endX, int endY){
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	public int getX() {
		return startX;
	}

	public int getY() {
		return startY;
	}
	
	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
}
