package request;

import java.util.Random;

import elevator.Elevator;
import elevator.ElevatorDirection;

public class ReceiveRequest extends Request {
	
	private ElevatorDirection direction;
	
	public ReceiveRequest(int floor, ElevatorDirection direction) {
		super(floor);
		//Return exception if Direction is NO_DIRECTION
		this.direction = direction;
	}
	
	public ElevatorDirection getDirection() {
		return direction;
	}

	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		elevator.getStopFloors().remove(this);
		//- Add several (random - the elevator doesn't know 
		//	how many passengers are waiting for it) 
		//	TakeRequests (random floor) to the elevator set
		Random r = new Random();
		int nPeople = r.nextInt(4) + 1;
		while(nPeople > 0)
			//This souldn't be done this way
			elevator.getStopFloors().add(new TakeRequest(r.nextInt(30) - 10, 1));
	}

	@Override
	public int compareTo(Object arg0) {
		Request r = (Request)arg0;
		if(r.getClass().isAssignableFrom(ReceiveRequest.class) && direction == ((ReceiveRequest)r).getDirection())
			return floor - ((Request)arg0).getFloor();
		return floor + 1 - r.getFloor();
	}

}
