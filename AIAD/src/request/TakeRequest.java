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
	public int compareTo(Object arg0) {
		Request r = ((Request)arg0);
		if(id != r.id)
			return (floor > r.floor || (floor == r.floor && id > r.id)) ? 1 : -1;
		return 0;
	}
}