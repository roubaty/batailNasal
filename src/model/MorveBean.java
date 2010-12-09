package model;

public class MorveBean {
	private final int start_case_x;
	private final int start_case_y;
	private final int size;
	private final int direction;	 		//%2==0 for east
											//%2==1 for south
	public MorveBean(int start_case_x, int start_case_y, int size, int direction){
		this.start_case_x=start_case_x;
		this.start_case_y=start_case_y;
		this.size=size;
		this.direction=direction%2;
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
}