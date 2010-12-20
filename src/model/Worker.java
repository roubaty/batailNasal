package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import view.GridPanel;

import constants.IConstantWorker;
import constants.IConstantsGlobal;
import controller.IController;

public class Worker extends Observable implements IWorker, IConstantsGlobal,
		IConstantWorker {

	private GameBean gb;
	private short countMorve;
	private short countNormalShot;
	private short countTripleShot;
	private short countMaxHitPlayer;
	private short countMaxHitIA;
	private short countHitPlayer;
	private short countHitIA;
	private boolean gameFinish;
	private boolean gotYou;
	private boolean last;
	private Random r = new Random();

	/**
	 * Constructor of Worker, initialize the game with the default values
	 * 
	 * @param controller
	 */
	public Worker(IController controller) {
		initGame();
		newGame();
	}

	/**
	 * initialise default values and build the WorkerSound
	 */
	private void initGame() {
		gb = new GameBean();
		gb.setLangage(LANGAGEFR);
		gb.setSound(SOUNDON);
		gameFinish = false;
	}

	private void updateViews() {
		setChanged();
		this.notifyObservers(gb);
	}

	@Override
	public void changeLangage(int langage) {
		gb.setLangage(langage);
		gb.setState(TYPECHANGELANGAGE);
		updateViews();
	}

	@Override
	public void newGame() {
		// initial values
		boolean[][] iaTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		boolean[][] playerTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		boolean[][] visible = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
		// for (int i = 0; i < visible.length; i++) {
		// for (int j = 0; j < visible.length; j++) {
		// visible[i][j] = true;
		// }
		// }
		ArrayList<MorveBean> playerTableMorve = new ArrayList<MorveBean>();
		ArrayList<MorveBean> iaTableMorve = new ArrayList<MorveBean>();
		countMorve = 0;
		countNormalShot = 0;
		countMaxHitPlayer = MAXHITDEFAULT;
		countMaxHitIA = MAXHITDEFAULT;
		countHitPlayer = 0;
		countHitIA = 0;
		countTripleShot = 0;
		gameFinish = false;
		last = true;
		gotYou = false;

		// prepare game
		gb.setState(TYPEADDMORVE_START);
		gb.setSizeMorve(MORVESIZEBIG);
		gb.setIaTableMorve(iaTableMorve);
		gb.setIaTableShot(iaTableShot);
		gb.setPlayerTableMorve(playerTableMorve);
		gb.setPlayerTableShot(playerTableShot);
		gb.setVisible(visible);
		gb.setIaTouched(false);
		gb.setPlayerTouched(false);

		updateViews();
	}

	// return a random bonus
	private int getRandomBonus(boolean isPlayerTurn) {
		countNormalShot = 0;
		int nr = r.nextInt(100);
		if (nr < 40) {
			// triple shot
			// sounds get bonus triple
			if (gb.getSound() == SOUNDON && isPlayerTurn) {
				new WorkerSound("triple_shots.wav", gb.getLangage()).start();
			}
			return TYPETRIPLE;
		} else if (nr < 70) {
			// spray
			// sounds
			if (gb.getSound() == SOUNDON && isPlayerTurn) {
				new WorkerSound("bonus_spray.wav", gb.getLangage()).start();
			}
			return TYPESPRAY;
		} else if (nr < 90) {
			// add morve
			// sounds
			if (gb.getSound() == SOUNDON && isPlayerTurn) {
				new WorkerSound("add_morve.wav", gb.getLangage()).start();
			}
			return TYPEADDMORVE;
		} else {
			// scan
			// sounds
			if (gb.getSound() == SOUNDON && isPlayerTurn) {
				new WorkerSound("debusquage.wav", gb.getLangage()).start();
			}
			return TYPESCAN;
		}
	}

	/**
	 * method for ia playing its turn
	 * 
	 * @param BonusTurn
	 *            true if it is a bonus turn
	 */
	private void iaTurn(boolean bonusTurn) {
		// ia turn, with and without bonus
		int shotX = r.nextInt(NUMBER_CASE_X);
		int shotY = r.nextInt(NUMBER_CASE_Y);
		if (bonusTurn) {
			int crtState = getRandomBonus(false);
			switch (crtState) {
			case TYPEADDMORVE:
				int j = 0;
				while (j < 5) {
					int direction = r.nextInt(10);
					if (direction < 5) {
						direction = HORIZONTAL;
					} else {
						direction = VERTICAL;
					}
					int startX_morveIA = r.nextInt(NUMBER_CASE_X);
					int startY_morveIA = r.nextInt(NUMBER_CASE_Y);
					int endX_morveIA;
					int endY_morveIA;
					if (direction == HORIZONTAL) {
						if (startX_morveIA >= MORVESIZELITTLE) {
							endX_morveIA = startX_morveIA;
							startX_morveIA -= MORVESIZELITTLE;
						} else {
							endX_morveIA = startX_morveIA + MORVESIZELITTLE;
						}
						endX_morveIA--;
						endY_morveIA = startY_morveIA;
					} else {
						if (startY_morveIA >= MORVESIZELITTLE) {
							endY_morveIA = startY_morveIA;
							startY_morveIA -= MORVESIZELITTLE;
						} else {
							endY_morveIA = startY_morveIA + MORVESIZELITTLE;
						}
						endY_morveIA--;
						endX_morveIA = startX_morveIA;
					}
					MorveBean m = new MorveBean(startX_morveIA, startY_morveIA,
							endX_morveIA, endY_morveIA);
					if (GridPanel.isValideAdd(m, gb.getIaTableMorve())) {
						gb.getIaTableMorve().add(m);
						gb.getIaTableShot()[startX_morveIA][startY_morveIA] = false;
						gb.getIaTableShot()[endX_morveIA][endY_morveIA] = false;
						if (direction == HORIZONTAL) {
							gb.getIaTableShot()[startX_morveIA + 1][startY_morveIA] = false;
						} else {
							gb.getIaTableShot()[startX_morveIA][startY_morveIA + 1] = false;
						}
						countMaxHitIA += 3;
						break;
					}
					j++;
				}

				gb.setState(TYPEIAADDMORVE);
				updateViews();
				break;
			case TYPESPRAY:
				last = false;
				setShot(shotX, shotY, false);
				setShot(shotX - 1, shotY, false);
				setShot(shotX + 1, shotY, false);
				setShot(shotX, shotY - 1, false);
				last = true;
				setShot(shotX, shotY + 1, false);
				gb.setState(TYPEIASPRAY);
				updateViews();
				break;
			case TYPETRIPLE:
				int i = 0;
				while (i < 3) {
					setShot(shotX, shotY, false);
					gb.setIaCoordX(shotX);
					gb.setIaCoordY(shotY);
					gb.setState(TYPEIATRIPLE);
					updateViews();
					shotX = r.nextInt(NUMBER_CASE_X);
					shotY = r.nextInt(NUMBER_CASE_Y);
					i++;
				}
				break;
			default:
				gb.setIaCoordX(shotX);
				gb.setIaCoordY(shotY);
				gb.setState(TYPEIASCAN);
				updateViews();
			}
		} else {
			// shot classique
			// boolean validShot = false;
			// while(!validShot){
			gb.setState(TYPEIANORMAL);
			updateViews();
			// try {
			// Thread.sleep(TIMEIASLEEP);
			// } catch (InterruptedException e) {
			// // si on change le son, la langue ou nouvelle partie, ça bug ici
			// System.out.println("Thread principal interrompu, c'est la fin...");
			// e.printStackTrace();
			// }
			gb.setIaCoordX(shotX);
			gb.setIaCoordY(shotY);
			setShot(shotX, shotY, false);
			// }
		}
	}

	private void addIAMorve() {
		MorveBean m = new MorveBean(1, 1, 3, true);
		int[] morveCount = {NUMBERBIGMORVE,NUMBERMIDDLEMORVE,NUMBERITTLEMORVE};
		int[] morveSize = {MORVESIZEBIG, MORVESIZEMIDDLE, MORVESIZELITTLE};
		for (int i = 0; i < morveCount.length; i++) {
			for (int j = 0; j < morveCount[i]; j++) {
				do{
					boolean direction = r.nextBoolean();
					int start_x;
					int start_y;
					if(direction){
						start_x = r.nextInt(NUMBER_CASE_X);
						start_y = r.nextInt(NUMBER_CASE_Y-morveSize[i]+1);
					}else{
						start_x = r.nextInt(NUMBER_CASE_X-morveSize[i]+1);
						start_y = r.nextInt(NUMBER_CASE_Y);
					}
					m = new MorveBean(start_x, start_y, morveSize[i], direction);
				}while(!GridPanel.isValideAdd(m, gb.getIaTableMorve()));
				gb.getIaTableMorve().add(m);
			}
		}
		/*
		short i = 0;
		while (i < 6) {
			int direction = r.nextInt(10);
			if (direction < 5) {
				direction = HORIZONTAL;
			} else {
				direction = VERTICAL;
			}
			int startX_morveIA = r.nextInt(NUMBER_CASE_X);
			int startY_morveIA = r.nextInt(NUMBER_CASE_Y);
			int endX_morveIA;
			int endY_morveIA;
			switch (i) {
			case 0:
				// add big morve
				if (direction == HORIZONTAL) {
					if (startX_morveIA >= MORVESIZEBIG) {
						endX_morveIA = startX_morveIA;
						startX_morveIA -= MORVESIZEBIG;
					} else {
						endX_morveIA = startX_morveIA + MORVESIZEBIG;
					}
					endX_morveIA--;
					endY_morveIA = startY_morveIA;
				} else {
					if (startY_morveIA >= MORVESIZEBIG) {
						endY_morveIA = startY_morveIA;
						startY_morveIA -= MORVESIZEBIG;
					} else {
						endY_morveIA = startY_morveIA + MORVESIZEBIG;
					}
					endY_morveIA--;
					endX_morveIA = startX_morveIA;
				}
				m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA,
						endY_morveIA);
				if (GridPanel.isValideAdd(m, gb.getIaTableMorve())) {
					gb.getIaTableMorve().add(m);
					i++;
				}
				break;
			case 1:
			case 2:
				// add middle morve
				if (direction == HORIZONTAL) {
					if (startX_morveIA >= MORVESIZEMIDDLE) {
						endX_morveIA = startX_morveIA;
						startX_morveIA -= MORVESIZEMIDDLE;
					} else {
						endX_morveIA = startX_morveIA + MORVESIZEMIDDLE;
					}
					endX_morveIA--;
					endY_morveIA = startY_morveIA;
				} else {
					if (startY_morveIA >= MORVESIZEMIDDLE) {
						endY_morveIA = startY_morveIA;
						startY_morveIA -= MORVESIZEMIDDLE;
					} else {
						endY_morveIA = startY_morveIA + MORVESIZEMIDDLE;
					}
					endY_morveIA--;
					endX_morveIA = startX_morveIA;
				}
				m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA,
						endY_morveIA);
				if (GridPanel.isValideAdd(m, gb.getIaTableMorve())) {
					gb.getIaTableMorve().add(m);
					i++;
				}
				break;
			default:
				// add little morve
				if (direction == HORIZONTAL) {
					if (startX_morveIA >= MORVESIZELITTLE) {
						endX_morveIA = startX_morveIA;
						startX_morveIA -= MORVESIZELITTLE;
					} else {
						endX_morveIA = startX_morveIA + MORVESIZELITTLE;
					}
					endX_morveIA--;
					endY_morveIA = startY_morveIA;
				} else {
					if (startY_morveIA >= MORVESIZELITTLE) {
						endY_morveIA = startY_morveIA;
						startY_morveIA -= MORVESIZELITTLE;
					} else {
						endY_morveIA = startY_morveIA + MORVESIZELITTLE;
					}
					endY_morveIA--;
					endX_morveIA = startX_morveIA;
				}
				m = new MorveBean(startX_morveIA, startY_morveIA, endX_morveIA,
						endY_morveIA);
				if (GridPanel.isValideAdd(m, gb.getIaTableMorve())) {
					gb.getIaTableMorve().add(m);
					i++;
				}
				break;
			}
		}*/
	}

	/**
	 * PRE: receive valid positions to place morve
	 */
	public void place(int startX, int startY, int endX, int endY) {

		// add morve to game
		MorveBean m = new MorveBean(startX, startY, endX, endY);
		System.out.println(m);
		gb.getPlayerTableMorve().add(m);
		countMorve++;

		if (gb.getState() == TYPEADDMORVE_START) {
			if (countMorve < 6) {
				// add more morve for new game
				if (countMorve <= 2) {
					// middle morve
					gb.setSizeMorve(MORVESIZEMIDDLE);
				} else {
					// little morve
					gb.setSizeMorve(MORVESIZELITTLE);
				}
				gb.setState(TYPEADDMORVE_START);
			} else {
				// add morve for new game completed
				gb.setState(TYPENORMAL);
				// sounds
				if (gb.getSound() == SOUNDON) {
					new WorkerSound("start.wav", gb.getLangage()).start();
				}
				// ia completion of it game
				this.addIAMorve();
			}
		} else {
			// add morve during game
			countMaxHitPlayer += 3;
			gb.getPlayerTableShot()[startX][startY] = false;
			gb.getPlayerTableShot()[endX][endY] = false;
			if (m.getDirection() == HORIZONTAL) {
				gb.getPlayerTableShot()[startX + 1][startY] = false;
			} else {
				gb.getPlayerTableShot()[startX][startY + 1] = false;
			}
			// IA play its BONUS (! if ia win)
			iaTurn(true);
			// next step for player
			gb.setState(TYPENORMAL);
		}
		gb.setPlayerCoordX(startX);
		gb.setPlayerCoordY(startY);

		updateViews();
	}

	@Override
	public void shot(int type, int posX, int posY) {
		// selon le type de tir
		switch (type) {
		case TYPENORMAL:
			countNormalShot++;
			setShot(posX, posY, true);
			setCaseVisibleTrue(posX, posY);
			gb.setPlayerCoordX(posX);
			gb.setPlayerCoordY(posY);
			// ia turn
			iaTurn(false);
			// continuation
			if (countNormalShot >= 4) {
				gb.setState(getRandomBonus(true));
			} else {
				gb.setState(TYPENORMAL);
			}
			break;
		case TYPESCAN:
			gb.setPlayerCoordX(posX);
			gb.setPlayerCoordY(posY);
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3 - i; j++) {
					setCaseVisibleTrue(posX - i, posY - j);
					setCaseVisibleTrue(posX - i, posY + j);
					setCaseVisibleTrue(posX + i, posY - j);
					setCaseVisibleTrue(posX + i, posY + j);
				}
			}
			iaTurn(true);
			// continuation
			gb.setState(TYPENORMAL);
			break;
		case TYPESPRAY:
			last = false;
			setShot(posX, posY, true);
			setCaseVisibleTrue(posX, posY);

			setShot(posX - 1, posY, true);
			setCaseVisibleTrue(posX - 1, posY);

			setShot(posX + 1, posY, true);
			setCaseVisibleTrue(posX + 1, posY);

			setShot(posX, posY - 1, true);
			setCaseVisibleTrue(posX, posY - 1);

			last = true;
			setShot(posX, posY + 1, true);
			setCaseVisibleTrue(posX, posY + 1);

			iaTurn(true);
			// continuation
			gb.setState(TYPENORMAL);
			break;
		case TYPETRIPLE:
			countTripleShot++;
			setShot(posX, posY, true);
			gb.setPlayerCoordX(posX);
			gb.setPlayerCoordY(posY);
			setCaseVisibleTrue(posX, posY);
			if (countTripleShot >= 3) {
				// triple shot ok
				countTripleShot = 0;
				// bonus turn of ia
				iaTurn(true);
				// next step for player
				gb.setState(TYPENORMAL);
			} else {
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

	private void setCaseVisibleTrue(int posX, int posY) {
		try {
			gb.getVisible()[posX][posY] = true;
		} catch (ArrayIndexOutOfBoundsException e) {
			// on evite de propager cette exeption, car lorsqu'on se trouve
			// dans les bords, c'est sûr que ca pose probleme
		}
	}

	private void setShot(int posX, int posY, boolean isPlayerShot) {
		try {
			if (isPlayerShot) {
				if (gb.getIaTableShot()[posX][posY])
					return;
				gb.getIaTableShot()[posX][posY] = true;
			} else {
				if (gb.getPlayerTableShot()[posX][posY])
					return;
				gb.getPlayerTableShot()[posX][posY] = true;
			}
			updateHitCount(posX, posY, isPlayerShot);
		} catch (ArrayIndexOutOfBoundsException e) {
			// idem setCaseVisibleTrue
		}
	}

	private void updateHitCount(int posX, int posY, boolean isPlayerShot) {
		ArrayList<MorveBean> ml;
		if (isPlayerShot) {
			ml = gb.getIaTableMorve();
		} else {
			ml = gb.getPlayerTableMorve();
		}
		for (MorveBean m : ml) {
			if (m.isOnAMorve(posX, posY)) {
				gotYou = true;
				if (isPlayerShot) {
					countHitPlayer++;
					gb.setPlayerCoordX(posX);
					gb.setPlayerCoordY(posY);
					// controle si le joueur gagne
					System.out.println("player shot : "+ countHitPlayer + "  : "+countMaxHitIA);
					if (countHitPlayer >= countMaxHitIA) {
						gb.setState(TYPEPLAYERWIN);
						gameFinish = true;
						if (gb.getSound() == SOUNDON)
							new WorkerSound("win.wav", gb.getLangage()).start();
						updateViews();
					}
				} else {
					countHitIA++;
					// controle si le joueur perd
					gb.setIaCoordX(posX);
					gb.setIaCoordY(posY);
					if (countHitIA >= countMaxHitPlayer) {
						gb.setState(TYPEIAWIN);
						gameFinish = true;
						if (gb.getSound() == SOUNDON)
							new WorkerSound("loose.wav", gb.getLangage())
									.start();
						updateViews();
					}
				}
			}
		}
		if (!gotYou && !gameFinish && last) {
			if (isPlayerShot) {
				if (gb.getSound() == SOUNDON && countNormalShot < 4)
					new WorkerSound("miss.wav", gb.getLangage()).start();
				gb.setIaTouched(false);
			} else {
				gb.setPlayerTouched(false);
			}
		}
		if (gotYou && !gameFinish && last) {
			if (isPlayerShot) {
				if (gb.getSound() == SOUNDON && countNormalShot < 4)
					new WorkerSound("hit.wav", gb.getLangage()).start();
				gb.setIaTouched(true);
				
			}
			else {
				gb.setPlayerTouched(true);
			}
			gotYou = false;
		}
	}

	public boolean isGameFinish() {
		return gameFinish;
	}

	@Override
	public void changesound() {
		if (gb.getSound() == SOUNDON)
			gb.setSound(SOUNDOOFF);
		else
			gb.setSound(SOUNDON);
		gb.setState(TYPECHANGESOUNDS);
		updateViews();
	}

}
