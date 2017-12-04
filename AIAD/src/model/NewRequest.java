package model;

import elevator.ElevatorDirection;

public class NewRequest extends Message {
	private int floor;
	private ElevatorDirection direction;
	private static int NEW_REQUEST_ID = 0;
	
	public NewRequest() {
		super();
	}
	
	public NewRequest(int floor, ElevatorDirection direction) {
		super(NEW_REQUEST_ID++);
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