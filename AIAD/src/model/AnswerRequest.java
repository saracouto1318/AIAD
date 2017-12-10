package model;

import elevator.ElevatorDirection;

/**
 * 
 * Class that allows to answer a request
 *
 */
public class AnswerRequest extends Message {
	/**
	 * Elevator's direction
	 */
	private ElevatorDirection direction;
	/**
	 * Request's floor
	 */
	private int floor;
	/**
	 * Last floor in the elevator's direction
	 */
	private int lastFloorInDirection;
	/**
	 * Passenger's weight
	 */
	private int passengersWeight;
	/**
	 * Distance from the stop floor
	 */
	private int stopFloorsLength;
	/**
	 * Elevator's total capacity
	 */
	private int elevatorCapacity;
	
	/**
	 * AnswerRequest's constructor
	 */
	public AnswerRequest() {
		super();
	}
	
	/**
	 * AnswerRequest's constructor
	 * @param id Message's identifier
	 * @param direction Elevator's direction
	 * @param floor Request's floor
	 * @param lastFloorInDirection Last floor in the elevator's direction
	 * @param passengersWeight Passenger's weight
	 * @param stopFloorsLength Distance from the stop floor
	 */
	public AnswerRequest(int id, ElevatorDirection direction, int floor, int lastFloorInDirection, int passengersWeight, int stopFloorsLength, int elevatorCapacity) {
		super(id);
		this.direction = direction;
		this.floor = floor;
		this.lastFloorInDirection = lastFloorInDirection;
		this.passengersWeight = passengersWeight;
		this.stopFloorsLength = stopFloorsLength;
		this.elevatorCapacity = elevatorCapacity;
	}

	/**
	 * Gets the elevator's direction
	 * @return The elevator's direction
	 */
	public ElevatorDirection getDirection() {
		return direction;
	}

	/**
	 * Sets the elevator's direction
	 * @param direction The elevator's new direction
	 */
	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

	/**
	 * Gets the request's floor
	 * @return The request's floor
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * Sets the request's floor
	 * @param floor The new request's floor
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * Gets the last floor in the elevator's direction
	 * @return The last floor in the elevator's direction
	 */
	public int getLastFloorInDirection() {
		return lastFloorInDirection;
	}

	/**
	 * Sets the last floor in the elevator's direction
	 * @param lastFloorInDirection The new last floor in the elevator's direction
	 */
	public void setLastFloorInDirection(int lastFloorInDirection) {
		this.lastFloorInDirection = lastFloorInDirection;
	}

	/**
	 * Gets the passengers' weight
	 * @return The passengers' weight
	 */
	public int getPassengersWeight() {
		return passengersWeight;
	}

	/**
	 * Sets the passengers' weight
	 * @param passengersWeight The passengers' new weight
	 */
	public void setPassengersWeight(int passengersWeight) {
		this.passengersWeight = passengersWeight;
	}

	/**
	 * Gets the distance from the stop floor
	 * @return The distance from the stop floor
	 */
	public int getStopFloorsLength() {
		return stopFloorsLength;
	}

	/**
	 * Sets the distance from the stop floor
	 * @param stopFloorsLength The new distance from the stop floor
	 */
	public void setStopFloorsLength(int stopFloorsLength) {
		this.stopFloorsLength = stopFloorsLength;
	}
}
