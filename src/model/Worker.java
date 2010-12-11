package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import view.GridPanel;

import constants.IConstantWorker;
import constants.IConstantsGlobal;
import controller.IController;

public class Worker extends Observable implements IWorker,IConstantsGlobal,IConstantWorker {
	
	private GameBean gb;
	private short countMorve;
	private short countNormalShot;
	private int countMaxHitPlayer;
	private int countMaxHitIA;
	private int countHitPlayer;
	private int countHitIA;
	private Random r = new Random();
	
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
		gb = new GameBean();
		gb.setLangage(LANGAGEFR);
		gb.setSound(SOUNDON);
	}
	
	private void updateViews(){
		this.notifyObservers(gb);
	}

	@Override
	public void changeLangage(int langage) {
		gb.setLangage(langage);
		gb.setState(STATECHANGELANGAGE);
		updateViews();
	}

	@Override
	public void newGame() {
		// initial values
		boolean[][] iaTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		boolean[][] playerTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		boolean[][] visible = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		ArrayList<MorveBean> playerTableMorve = new ArrayList<MorveBean>();
		ArrayList<MorveBean> iaTableMorve = new ArrayList<MorveBean>();
		countMorve = 0;
		countNormalShot = 0;
		countMaxHitPlayer = MAXHITDEFAULT;
		countMaxHitIA = MAXHITDEFAULT;
		countHitPlayer = 0;
		countHitIA = 0;
		
		// prepare game
		gb.setState(TYPEADDMORVE_START);
		gb.setSizeMorve(MORVESIZEBIG);
		gb.setIaTableMorve(iaTableMorve);
		gb.setIaTableShot(iaTableShot);
		gb.setPlayerTableMorve(playerTableMorve);
		gb.setPlayerTableShot(playerTableShot);
		gb.setVisible(visible);
		
		updateViews();
	}
	
	// return a random bonus
	private int getRandomBonus(){
		int nr = r.nextInt(100);
		if(nr < 40){
			// triple shot
			return TYPETRIPLE;
		}
		else if (nr < 70){
			// spray
			return TYPESPRAY;
		}
		else if (nr < 90){
			// add morve
			return TYPEADDMORVE;
		}
		else {
			// scan
			return TYPESCAN;
		}
	}
	
	private void addIAMorve(){
		MorveBean m;
		short i = 1;
		while(i < 6){
			int direction = r.nextInt(10);
			if(direction < 5){
				direction = HORIZONTAL;
			}
			else {
				direction = VERTICAL;
			}
			int startX_morveIA = r.nextInt(NUMBER_CASE_X) + 1;
			int startY_morveIA = r.nextInt(NUMBER_CASE_Y) + 1;
			switch(i){
				case 0:
					// add big morve
					int endX_morveIA;
					int endY_morveIA;
					if(direction == HORIZONTAL){
						if(startX_morveIA > 5){
							endX_morveIA = startX_morveIA-MORVESIZEBIG;
						} else{
							endX_morveIA = startX_morveIA+MORVESIZEBIG;
						}
						endY_morveIA = startY_morveIA;
					}
					else{
						if(startY_morveIA > 5){
							endY_morveIA = startY_morveIA-MORVESIZEBIG;
						} else{
							endY_morveIA = startY_morveIA+MORVESIZEBIG;
						}
						endX_morveIA = startX_morveIA;
					}
					m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA, endY_morveIA);
					if(GridPanel.isValideAdd(m, gb.getIaTableMorve())){
						gb.getIaTableMorve().add(m);
						i++;
					}
					break;
				case 1:
				case 2:
					// add middle morve
					
					break;
				default:
					// add little morve
					
					break;
			}
		}
	}

	/**
	 * PRE: receive valid positions to place morve
	 */
	public void place(int startX, int startY, int endX, int endY) {
		// sounds ?
		// add morve to game
		MorveBean m = new MorveBean(startX, startY, endX, endY);
		gb.getPlayerTableMorve().add(m);
		countMorve++;
		
		if(gb.getState() == TYPEADDMORVE_START){
			if(countMorve < 6){
				// add more morve for new game
				if(countMorve <= 1){
					// middle morve
					gb.setSizeMorve(MORVESIZEMIDDLE);
				}
				else {
					// little morve
					gb.setSizeMorve(MORVESIZELITTLE);
				}
				gb.setState(TYPEADDMORVE_START);
			}
			else{
				// add morve for new game completed
				gb.setState(TYPENORMAL);
				// sounds
				if(gb.getSound() == SOUNDON){
					if(gb.getLangage() == LANGAGEFR){
						new WorkerSound("src/ressources/sounds/fr_start.wav").start();
					} else {
						new WorkerSound("src/ressources/sounds/en_start.wav").start();
					}
				}
				// ia completion of it game
				this.addIAMorve();
			}
		}
		else {
			// TODO
			countNormalShot = 0;
		}
		
		updateViews();
	}

	@Override
	public void shot(int type, int posX, int posY) {
		countNormalShot++;
		// TODO Auto-generated method stub
		updateViews();
	}

	@Override
	public void changesound() {
		if(gb.getSound() == SOUNDON)gb.setSound(SOUNDOOFF);
		else gb.setSound(SOUNDON);
		gb.setState(STATECHANGESOUNDS);
		updateViews();
	}
	
}
