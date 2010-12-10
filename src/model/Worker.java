package model;

import java.util.Observable;

import constants.IConstantWorker;
import constants.IConstantsGlobal;
import controller.IController;

public class Worker extends Observable implements IWorker,IConstantsGlobal,IConstantWorker {
	
	GameBean gb;
	
	/**
	 * Constructor of Worker, initialize the game with the default values
	 * @param controller
	 */
	public Worker(IController controller) {
		initGame();
		newGame();
	}

	/**
	 * initialise default values and build the WorkerSound
	 */
	private void initGame(){
		gb = new GameBean();
		gb.setLangage(LANGAGEFR);
		gb.setSound(SOUNDON);
	}
	
	private void updateViews(){
		this.notifyObservers(/*arg bean*/);
	}

	@Override
	public void changeLangage(int langage) {
		gb.setLangage(langage);
		updateViews();
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		updateViews();
		if(gb.getSound() == SOUNDON){
			if(gb.getLangage() == LANGAGEFR){
				new WorkerSound("src/ressources/sounds/fr_start.wav").start();
			} else {
				new WorkerSound("src/ressources/sounds/en_start.wav").start();
			}
		}
	}

	@Override
	public void place(int startX, int startY, int endX, int endY) {
		// TODO Auto-generated method stub
		updateViews();
	}

	@Override
	public void shot(int type, int posX, int posY) {
		// TODO Auto-generated method stub
		updateViews();
	}

	@Override
	public void changesound() {
		if(gb.getSound() == SOUNDON)gb.setSound(SOUNDOOFF);
		else gb.setSound(SOUNDON);
		updateViews();
	}
	
}
