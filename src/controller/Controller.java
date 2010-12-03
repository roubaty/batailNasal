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
		mainView = new MainView(this);
		secondaryView = new SecondaryView(this);
		worker = new Worker(this);
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
	}

	@Override
	public void actionOccured(Action action) {
		if (action instanceof ActionLangage) {

		}
		if (action instanceof ActionNewGame) {

		}
		if (action instanceof ActionPlace) {

		}
		if (action instanceof ActionShot) {

		}
	}

}