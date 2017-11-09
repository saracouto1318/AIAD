package request;

import elevator.Elevator;

public class TakeRequest extends Request {
	/**
	 * Value that represents the weight of the passenger
	 */
	private int weight;
	
	public TakeRequest(int floor, int weight) {
		super(floor);
		this.weight = weight;
	}
	
	public int getWeight() {
		return weight;
	}

	/**
	 * Called when the elevator gets to this floor.
	 * This function is an override method from the parent class Request
	 * and, in the case of TakeRequest, this function will remove this
	 * request from the set of floors.
	 */
	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		//	this also removes the weight of this passenger
		elevator.getStopFloors().remove(this);
		System.out.println("Removing take request on floor " + floor 
				+ " - elevator cFloor " + elevator.getCFloor());
	}
	
	@Override
	public boolean stop(Elevator elevator) {
		return floor == elevator.getCFloor();
	}
	
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