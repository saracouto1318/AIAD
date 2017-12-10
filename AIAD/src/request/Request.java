package request;

import java.util.Date;

import elevator.Elevator;
import elevator.ElevatorDirection;

/**
 * 
 * Creates the requests that an elevator will receive in certain floor
 *
 */
public abstract class Request implements Comparable {	
	/**
	 * Request's identifier
	 */
	protected static int RECEIVE_ID = 0;
	/**
	 * Request's identifier
	 */
	protected static int TAKE_ID = 0;
	/**
	 * Request's identifier
	 */
	protected int id;
	/**
	 * Request's floor
	 */
	protected int floor;	
	
	protected Date startTime;
	protected int startElevatorFloor;
	protected ElevatorDirection startElevatorDirection;
	
	/**
	 * Request's constructor where the parameter floor is initiated
	 * @param floor Request's floor
	 */
	public Request(int floor) {
		this.floor = floor;
	}
	
	/**
	 * Gets the request's floor
	 * @return The request's floor
	 */
	public int getFloor() {
		return floor;
	}
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Function used in the requests received or taken
	 * @param elevator Elevator that receives or takes the request
	 */
	public abstract void onFloor(Elevator elevator);
	
	/**
	 * Determines if the elevator should stop for this request (used in the requests received or taken) 
	 * @return Whether or not it should stop (true or false)
	 */
	public abstract boolean stop(Elevator elevator);
	
	/**
	 * Compares two requests
	 * @param arg0 The request that will be used in the comparison
	 * @return 0 if the requests are equal; -1 if the elevator floor or id is smaller
	 */
	@Override
	public abstract int compareTo(Object arg0);
}
