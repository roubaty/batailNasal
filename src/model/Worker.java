package model;

import java.util.Observable;

import constants.IConstantWorker;
import constants.IConstantsGlobal;
import controller.IController;

public class Worker extends Observable implements IWorker,IConstantsGlobal,IConstantWorker {

	private int langage;
	private int sound;
	private WorkerSound ws;
	
	/**
	 * Constructor of Worker, initialize the game with the default values
	 * @param controller
	 */
	public Worker(IController controller) {
		initGame();
	}

	/**
	 * initialise default values and build the WorkerSound
	 */
	private void initGame(){
		langage = LANGAGEFR;
		sound = SOUNDON;
		this.ws = new WorkerSound();
	}
	
	private void updateViews(){
		
	}

	@Override
	public void changeLangage(int langage) {
		this.langage = langage;
		updateViews();
	}

	@Override
	public void newGame() {
		// TODO Auto-generated method stub
		updateViews();
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
		if(sound == SOUNDON)sound = SOUNDOOFF;
		else sound = SOUNDON;
		updateViews();
	}
	
}
