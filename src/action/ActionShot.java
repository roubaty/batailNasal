package action;

public class ActionShot extends Action {
	
	private final int type;
	private final int posX;
	private final int posY;

	public ActionShot(int type, int posX, int posY){
		this.type = type;
		this.posX = posX;
		this.posY = posY;
	}

	public int getType() {
		return type;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
}
