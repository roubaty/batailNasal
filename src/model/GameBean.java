package model;

import java.util.ArrayList;

import constants.IConstantsGlobal;

public class GameBean implements IConstantsGlobal{

	private int langage;
	private int sound;
	private int state;
	private ArrayList<MorveBean> playerTableMorve;
	private ArrayList<MorveBean> iaTableMorve;
	private boolean[][] visible = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
	private boolean[][] playerTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
	private boolean[][] iaTableShot = new boolean[NUMBER_CASE_X][NUMBER_CASE_Y];
	
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
	
	
	
}
