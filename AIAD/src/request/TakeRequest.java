package request;

import elevator.Elevator;

/**
 * 
 * Creates the requests that a elevator will take
 *
 */
public class TakeRequest extends Request {
	/**
	 * Value that represents the weight of the passengers
	 */
	private int weight;
	
	/**
	 * TakeRequest's constructor
	 * @param floor Request's floor
	 * @param weight Passengers' weight
	 */
	public TakeRequest(int floor, int weight) {
		super(floor);
		this.weight = weight;
	}
	
	/**
	 * Gets the passenger' weight
	 * @return The passengers' weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * Called when the elevator gets to this floor.
	 * This function is an override method from the parent class Request
	 * and, in the case of TakeRequest, this function will remove this
	 * request from the set of floors.
	 * @param elevator Elevator that will take the request
	 */
	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		//	this also removes the weight of this passenger
		elevator.getStopFloors().remove(this);
	}
	
	/**
	 * Determines if the elevator should stop for this request
	 * @return Whether or not it should stop
	 */
	@Override
	public boolean stop(Elevator elevator) {
		return floor == elevator.getCFloor();
	}
	
	/**
	 * Compares two requests
	 * @param arg0 The request that will be used in the comparison
	 * @return 0 if the requests are equal; -1 if the elevator floor or id is smaller
	 */
	@Override
	public int compareTo(Object arg) {
		Request r = ((Request)arg);
		if(id != r.id)
			return (floor > r.floor || 
					(floor == r.floor && id > r.id)) ? 
						1 : -1;
		return 0;
	}
}