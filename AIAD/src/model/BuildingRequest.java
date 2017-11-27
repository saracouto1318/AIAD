package model;

import java.io.Serializable;

public class BuildingRequest implements Serializable {
	private int id;
	private int floor;
	
	public BuildingRequest() {}
	
	public BuildingRequest(int id, int floor) {
		this.id = id;
		this.floor = floor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
}
