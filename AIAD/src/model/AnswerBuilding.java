package model;

import java.io.Serializable;

public class AnswerBuilding  implements Serializable {
	private int id;
	private int diff_floors;
	private int capacity;
	private int num_requests;
	
	public AnswerBuilding() {}
	
	public AnswerBuilding(int id, int diff_floors, int capacity, int num_requests) {
		this.id = id;
		this.diff_floors = diff_floors;
		this.capacity = capacity;
		this.num_requests = num_requests;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDiff_floors() {
		return diff_floors;
	}

	public void setDiff_floors(int diff_floors) {
		this.diff_floors = diff_floors;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getNum_requests() {
		return num_requests;
	}

	public void setNum_requests(int num_requests) {
		this.num_requests = num_requests;
	}

	
}
