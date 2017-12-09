package model;

import elevator.ElevatorDirection;

/**
 * 
 * Creates the request's status
 *
 */
public class StatusRequest extends Message {
	/**
	 * ACK message
	 */
	private boolean ack;
	/**
	 * Request's floor
	 */
	private int floor;
	/**
	 * Elevator's direction
	 */
	private ElevatorDirection direction;

	/**
	 * StatusRequest's constructor
	 */
	public StatusRequest() {
		super();
	}
	
	/**
	 * NewRequest's constructor
	 * @param id Request's identifier
	 * @param ack ACK message
	 * @param floor Request's floor
	 * @param direction Elevator's direction
	 */
	public StatusRequest(int id, boolean ack, int floor, ElevatorDirection direction) {
		super(id);
		this.ack = ack;
		this.floor = floor;
		this.direction = direction;
	}

	/**
	 * Verifies if the message received is the ACK
	 * @return true if it's ACK; false otherwise
	 */
	public boolean isAck() {
		return ack;
	}

	/**
	 * Sets the ack parameter
	 * @param ack New boolean value to the ack parameter
	 */
	public void setAck(boolean ack) {
		this.ack = ack;
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
}
