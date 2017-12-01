package model;

import elevator.ElevatorDirection;

public class NewRequest extends Message {
	private int floor;
	private ElevatorDirection direction;
	
	public NewRequest() {
		super();
	}
	
	public NewRequest(int id, int floor, ElevatorDirection direction) {
		super(id);
		this.floor = floor;
		this.direction = direction;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}
}