package model;

import java.util.Observable;

import controller.IController;

public class Worker extends Observable implements IWorker {

	private int langage;
	private int sound;
	
	public Worker(IController controller) {
		initGame();
	}

	private void initGame(){
		
	}
	
}
