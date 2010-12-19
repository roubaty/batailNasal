/*
 * DefaultController.java
 *
 * Created on January 22, 2007, 8:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controller;

import action.*;
import model.IWorker;
import model.Worker;

import controller.Controller;
import view.IView;
import view.MainView;
import view.SecondaryView;

public class Controller implements IController {

	IView mainView;
	IView secondaryView;
	IWorker worker;

	/** Creates a new instance of Main */
	public Controller() {
		this.initComponent();
	}

	private void initComponent() {
		secondaryView = new SecondaryView(this);
		mainView = new MainView(this);
		worker = new Worker(this);
		worker.addObserver(mainView);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Controller controller = new Controller();
	}

	@Override
	public void actionOccured(Action action) {
		if (action instanceof ActionLangage) {
			worker.changeLangage(((ActionLangage) action).getLangage());
		}
		if (action instanceof ActionNewGame) {
			worker.newGame();
		}
		if (!worker.isGameFinish()) {
			if (action instanceof ActionPlace) {
				worker.place(((ActionPlace) action).getStartX(),
						((ActionPlace) action).getStartY(),
						((ActionPlace) action).getEndX(),
						((ActionPlace) action).getEndY());
			}
			if (action instanceof ActionShot) {
				worker.shot(((ActionShot) action).getType(),
						((ActionShot) action).getPosX(),
						((ActionShot) action).getPosY());
			}
		}
		if (action instanceof ActionSound) {
			worker.changesound();
		}
	}

}