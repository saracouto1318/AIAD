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
		elevator.getStopFloors().remove(this);
		System.out.println("Removing take request on floor " + floor + " - elevator cFloor " + elevator.getCFloor());
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((TakeRequest)obj).weight == weight;
	}
	
	@Override
	public int compareTo(Object arg0) {
		Request r = ((Request)arg0);
		return id != r.id ? floor + 1 - r.getFloor() : 0;
	}
}