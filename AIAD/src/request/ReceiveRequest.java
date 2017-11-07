package request;

import elevator.Elevator;

public class ReceiveRequest extends Request {
	
	public ReceiveRequest(int floor) {
		super(floor);
	}

	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		//removeRequest(elevator);
		//- Add several (random - the elevator doesn't know 
		//	how many passengers are waiting for it) 
		//	TakeRequests (random floor) to the elevator set
		//addTakeRequest(elevator, new TakeRequest(randomFloor, randomWeight);
	}

}
