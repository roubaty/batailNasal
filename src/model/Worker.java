package model;

import java.io.File;
import java.util.Observable;

import constants.IConstantWorker;
import constants.IConstantsGlobal;
import controller.IController;

public class Worker extends Observable implements IWorker,IConstantsGlobal,IConstantWorker {

	private int langage;
	private int sound;
	
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
		langage = LANGAGEFR;
		sound = SOUNDON;
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
		if(sound == SOUNDON){
			if(langage == LANGAGEFR){
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
		if(sound == SOUNDON)sound = SOUNDOOFF;
		else sound = SOUNDON;
		updateViews();
	}
	
}
