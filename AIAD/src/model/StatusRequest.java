package model;

import elevator.ElevatorDirection;

public class StatusRequest extends Message {
	private boolean ack;
	private int floor;
	private ElevatorDirection direction;

	public StatusRequest() {
		super();
	}
	
	public StatusRequest(int id, boolean ack, int floor, ElevatorDirection direction) {
		super(id);
		this.ack = ack;
		this.floor = floor;
		this.direction = direction;
	}

	public boolean isAck() {
		return ack;
	}

	public void setAck(boolean ack) {
		this.ack = ack;
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
