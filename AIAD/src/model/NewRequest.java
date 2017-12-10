package model;

import elevator.ElevatorDirection;

/**
 * 
 * Class that allows to create a new request
 *
 */
public class NewRequest extends Message implements Comparable{
	/**
	 * Request's floor
	 */
	private int floor;
	/**
	 * Elevator's direction
	 */
	private ElevatorDirection direction;
	/**
	 * Request's identifier
	 */
	private static int NEW_REQUEST_ID = 0;
	
	/**
	 * NewRequest's constructor
	 */
	public NewRequest() {
		super();
	}
	
	/**
	 * NewRequest's constructor
	 * @param floor Request's floor
	 * @param direction Elevator's direction
	 */
	public NewRequest(int floor, ElevatorDirection direction) {
		super(NEW_REQUEST_ID++);
		this.floor = floor;
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
	
	@Override
	public int compareTo(Object arg0) {
		NewRequest a1 = ((NewRequest)arg0);
		if (this.floor == a1.floor && this.direction == a1.direction){
			return 0;
		}
		return this.floor < a1.floor ? -1 : 1;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}