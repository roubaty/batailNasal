package model;

public class MorveBean {
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
		this.size=-start_case_x+start_case_y-end_case_x-end_case_y;
		System.out.println(size);
		this.direction=(start_case_x==end_case_x) ? 1 :0 ;
		this.end_case_x=end_case_x;
		this.end_case_y=end_case_y;
	}
	public MorveBean(int start_case_x, int start_case_y, int size, boolean direction){
		this.start_case_x=start_case_x;
		this.start_case_y=start_case_y;
		this.size=size;
		this.direction=(direction)? 1 : 0;
		if(direction){
			this.end_case_x=start_case_x+size;
			this.end_case_y=start_case_y;
		}else{
			this.end_case_x=start_case_x;
			this.end_case_y=start_case_y+size;
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
}