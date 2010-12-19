package model;

import java.util.ArrayList;

import constants.IConstantsGlobal;

public class GameBean implements IConstantsGlobal {

	private int langage;
	private int sound;
	private int state;
	private ArrayList<MorveBean> playerTableMorve = new ArrayList<MorveBean>();
	private ArrayList<MorveBean> iaTableMorve = new ArrayList<MorveBean>();
	private boolean[][] visible;
	private boolean[][] playerTableShot;
	private boolean[][] iaTableShot;
	private int sizeMorve;
	private boolean playerTouched;
	private boolean iaTouched;	
	private int playerCoordX;
	private int playerCoordY;
	private int iaCoordX;
	private int iaCoordY;
	
	public boolean isPlayerTouched() {
		return playerTouched;
	}

	public void setPlayerTouched(boolean playerTouched) {
		this.playerTouched = playerTouched;
	}

	public boolean isIaTouched() {
		return iaTouched;
	}

	public void setIaTouched(boolean iaTouched) {
		this.iaTouched = iaTouched;
	}

	public int getPlayerCoordX() {
		return playerCoordX;
	}

	public void setPlayerCoordX(int playerCoordX) {
		this.playerCoordX = playerCoordX;
	}

	public int getPlayerCoordY() {
		return playerCoordY;
	}

	public void setPlayerCoordY(int playerCoordY) {
		this.playerCoordY = playerCoordY;
	}

	public int getIaCoordX() {
		return iaCoordX;
	}

	public void setIaCoordX(int iaCoordX) {
		this.iaCoordX = iaCoordX;
	}

	public int getIaCoordY() {
		return iaCoordY;
	}

	public void setIaCoordY(int iaCoordY) {
		this.iaCoordY = iaCoordY;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public ArrayList<MorveBean> getPlayerTableMorve() {
		return playerTableMorve;
	}

	public void setPlayerTableMorve(ArrayList<MorveBean> playerTableMorve) {
		this.playerTableMorve = playerTableMorve;
	}

	public ArrayList<MorveBean> getIaTableMorve() {
		return iaTableMorve;
	}

	public void setIaTableMorve(ArrayList<MorveBean> iaTableMorve) {
		this.iaTableMorve = iaTableMorve;
	}

	public boolean[][] getVisible() {
		return visible;
	}

	public void setVisible(boolean[][] visible) {
		this.visible = visible;
	}

	public boolean[][] getPlayerTableShot() {
		return playerTableShot;
	}

	public void setPlayerTableShot(boolean[][] playerTableShot) {
		this.playerTableShot = playerTableShot;
	}

	public boolean[][] getIaTableShot() {
		return iaTableShot;
	}

	public void setIaTableShot(boolean[][] iaTableShot) {
		this.iaTableShot = iaTableShot;
	}

	public int getLangage() {
		return langage;
	}

	public void setLangage(int langage) {
		this.langage = langage;
	}

	public int getSound() {
		return sound;
	}

	public void setSound(int sound) {
		this.sound = sound;
	}

	public void setSizeMorve(int sizeMorve) {
		this.sizeMorve = sizeMorve;
	}

	public int getSizeMorve() {
		return sizeMorve;
	}

}
