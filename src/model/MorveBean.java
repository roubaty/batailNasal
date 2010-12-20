package model;

import constants.IConstantsGlobal;

public class MorveBean implements IConstantsGlobal {
	private final int start_case_x;
	private final int start_case_y;
	private final int size;
	private final int direction;	 		//%2==0 for east
											//%2==1 for south
	private final int end_case_x;
	private final int end_case_y;
	public MorveBean(int start_case_x, int start_case_y, int end_case_x, int end_case_y){
		this.start_case_x=start_case_x;
		this.start_case_y=start_case_y;
		this.size=(-start_case_x-start_case_y+end_case_x+end_case_y)+1;
		System.out.println(size);
		this.direction=(start_case_x==end_case_x) ? VERTICAL : HORIZONTAL ;
		this.end_case_x=end_case_x;
		this.end_case_y=end_case_y;
	}
	public MorveBean(int start_case_x, int start_case_y, int size, boolean direction){
		this.start_case_x=start_case_x;
		this.start_case_y=start_case_y;
		this.size=size;
		this.direction=(direction)? HORIZONTAL : VERTICAL; // incoherence !! may be the problem of bad ia morve positioning
		if(direction){
			this.end_case_x=start_case_x+(size-1);
			this.end_case_y=start_case_y;
		}else{
			this.end_case_x=start_case_x;
			this.end_case_y=start_case_y+(size-1);
		}
	}
	public int getStart_case_x() {
		return start_case_x;
	}
	public int getStart_case_y() {
		return start_case_y;
	}
	public int getSize() {
		return size;
	}
	public int getDirection() {
		return direction;
	}
	public int getEnd_case_x() {
		return end_case_x;
	}
	public int getEnd_case_y() {
		return end_case_y;
	}
	public String toString(){
		String s = "start_case_x : "+start_case_x + "\n";
		s += "start_case_y : "+start_case_y + "\n";
		s += "end_case_x : "+end_case_x + "\n";
		s += "end_case_y : "+end_case_y + "\n";
		s += "size : "+size + "\n";
		s += "direction : "+direction + "\n";
		return s;
	}
	public boolean isOnAMorve(int posX, int posY){
		if(direction==0){
			return (posY==start_case_y) && (posX>=start_case_x) && (posX<=start_case_x+size);
		}else{
			return (posX==start_case_x) && (posY>=start_case_y) && (posY<=start_case_y+size);
		}
	}
}