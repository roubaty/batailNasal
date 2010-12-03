package model;

public interface IWorker {

	void changeLangage(int langage);

	void newGame();

	void place(int startX, int startY, int endX, int endY);

	void shot(int type, int posX, int posY);

	void changesound();
	
}
