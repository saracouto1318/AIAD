package request;

import elevator.Elevator;

public class TakeRequest extends Request {

	private int weight;
	
	public TakeRequest(int floor, int weight) {
		super(floor);
		this.weight = weight;
	}

	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		//	this also removes the weight of this passenger
		//removeRequest(elevator)
	}

}
