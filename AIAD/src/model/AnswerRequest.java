package model;

import elevator.ElevatorDirection;

public class AnswerRequest extends Message {
	private ElevatorDirection direction;
	private int floor;
	private int lastFloorInDirection;
	private int passengersWeight;
	private int stopFloorsLength;
	
	public AnswerRequest() {
		super();
	}
	
	public AnswerRequest(int id, ElevatorDirection direction, int floor, int lastFloorInDirection, int passengersWeight, int stopFloorsLength) {
		super(id);
		this.direction = direction;
		this.floor = floor;
		this.lastFloorInDirection = lastFloorInDirection;
		this.passengersWeight = passengersWeight;
		this.stopFloorsLength = stopFloorsLength;
	}

	public ElevatorDirection getDirection() {
		return direction;
	}

	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getLastFloorInDirection() {
		return lastFloorInDirection;
	}

	public void setLastFloorInDirection(int lastFloorInDirection) {
		this.lastFloorInDirection = lastFloorInDirection;
	}

	public int getPassengersWeight() {
		return passengersWeight;
	}

	public void setPassengersWeight(int passengersWeight) {
		this.passengersWeight = passengersWeight;
	}

	public int getStopFloorsLength() {
		return stopFloorsLength;
	}

	public void setStopFloorsLength(int stopFloorsLength) {
		this.stopFloorsLength = stopFloorsLength;
	}
}
