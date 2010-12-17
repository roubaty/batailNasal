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
	private short countTripleShot;
	private short countMaxHitPlayer;
	private short countMaxHitIA;
	private short countHitPlayer;
	private short countHitIA;
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
		setChanged();
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
		countTripleShot = 0;
		
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
		countNormalShot = 0;
		int nr = r.nextInt(100);
		if(nr < 40){
			// triple shot
			// sounds get bonus triple
			if(gb.getSound() == SOUNDON){
				new WorkerSound("triple_shots.wav",gb.getLangage()).start();
			}
			return TYPETRIPLE;
		}
		else if (nr < 70){
			// spray
			// sounds
			if(gb.getSound() == SOUNDON){
				new WorkerSound("bonus_spray.wav",gb.getLangage()).start();
			}
			return TYPESPRAY;
		}
		else if (nr < 90){
			// add morve
			// sounds
			if(gb.getSound() == SOUNDON){
				new WorkerSound("add_morve.wav",gb.getLangage()).start();
			}
			return TYPEADDMORVE;
		}
		else {
			// scan
			// sounds
			if(gb.getSound() == SOUNDON){
				new WorkerSound("debusquage.wav",gb.getLangage()).start();
			}
			return TYPESCAN;
		}
	}
	
	/**
	 * method for ia playing its turn
	 * @param BonusTurn true if it is a bonus turn
	 */
	private void iaTurn(boolean BonusTurn){
		// TODO ia turn, with and without bonus
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
			int endX_morveIA;
			int endY_morveIA;
			switch(i){
				case 0:
					// add big morve
					if(direction == HORIZONTAL){
						if(startX_morveIA > MORVESIZEBIG){
							endX_morveIA = startX_morveIA-MORVESIZEBIG;
						} else{
							endX_morveIA = startX_morveIA+MORVESIZEBIG;
						}
						endY_morveIA = startY_morveIA;
					}
					else{
						if(startY_morveIA > MORVESIZEBIG){
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
					if(direction == HORIZONTAL){
						if(startX_morveIA > MORVESIZEMIDDLE){
							endX_morveIA = startX_morveIA-MORVESIZEMIDDLE;
						} else{
							endX_morveIA = startX_morveIA+MORVESIZEMIDDLE;
						}
						endY_morveIA = startY_morveIA;
					}
					else{
						if(startY_morveIA > MORVESIZEMIDDLE){
							endY_morveIA = startY_morveIA-MORVESIZEMIDDLE;
						} else{
							endY_morveIA = startY_morveIA+MORVESIZEMIDDLE;
						}
						endX_morveIA = startX_morveIA;
					}
					m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA, endY_morveIA);
					if(GridPanel.isValideAdd(m, gb.getIaTableMorve())){
						gb.getIaTableMorve().add(m);
						i++;
					}
					break;
				default:
					// add little morve
					if(direction == HORIZONTAL){
						if(startX_morveIA > MORVESIZELITTLE){
							endX_morveIA = startX_morveIA-MORVESIZELITTLE;
						} else{
							endX_morveIA = startX_morveIA+MORVESIZELITTLE;
						}
						endY_morveIA = startY_morveIA;
					}
					else{
						if(startY_morveIA > MORVESIZELITTLE){
							endY_morveIA = startY_morveIA-MORVESIZELITTLE;
						} else{
							endY_morveIA = startY_morveIA+MORVESIZELITTLE;
						}
						endX_morveIA = startX_morveIA;
					}
					m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA, endY_morveIA);
					if(GridPanel.isValideAdd(m, gb.getIaTableMorve())){
						gb.getIaTableMorve().add(m);
						i++;
					}
					break;
			}
		}
	}

	/**
	 * PRE: receive valid positions to place morve
	 */
	public void place(int startX, int startY, int endX, int endY) {
		
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
					new WorkerSound("start.wav",gb.getLangage()).start();
				}
				// ia completion of it game
				this.addIAMorve();
			}
		}
		else {
			// TODO add morve during game
			countMaxHitPlayer += 3;
			// sounds for placing morve ?
			// IA play its BONUS (! if ia win)
			iaTurn(true);
			// next step for player
			gb.setState(TYPENORMAL);
		}
		
		updateViews();
	}

	@Override
	public void shot(int type, int posX, int posY) {
		// TODO Auto-generated method stub
		// selon le type de tir
		switch(type){
		case TYPENORMAL:
			countNormalShot++;
			// TODO do shot
			// ia turn
			iaTurn(false);
			// continuation
			if(countNormalShot >= 4){
				gb.setState(getRandomBonus());
			}
			else {
				gb.setState(TYPENORMAL);
			}
			break;
		case TYPESCAN :
			// TODO do scan
			// ia turn
			iaTurn(true);
			// continuation
			gb.setState(TYPENORMAL);
			break;
		case TYPESPRAY :
			// TODO do spray
			// ia turn
			iaTurn(true);
			// continuation
			gb.setState(TYPENORMAL);
			break;
		case TYPETRIPLE :
			countTripleShot++;
			// TODO do shot, check victory 
			if(countTripleShot >= 3){
				// triple shot ok
				countTripleShot = 0;
				// bonus turn of ia
				iaTurn(true);
				// next step for player
				gb.setState(TYPENORMAL);
			}
			else {
				// continue le triple shot				
				gb.setState(TYPETRIPLE);
			}
			break;
		default:
			// unknow state
			System.out.println("Error in Worker, unknow shot state.");
			break;
		}
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
