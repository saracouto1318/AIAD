package tests;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;
import request.ReceiveRequest;
import request.TakeRequest;

public class ElevatorTestPassengers extends Elevator {
	public ElevatorTestPassengers() {
		super();
		testFloors();
	}
	
	private void testFloors() {
		stopFloors.add(new TakeRequest(1, 1));
		stopFloors.add(new TakeRequest(3,1));
		stopFloors.add(new TakeRequest(5,1));
		stopFloors.add(new TakeRequest(10,1));
		stopFloors.add(new TakeRequest(4,1));
		stopFloors.add(new TakeRequest(19,2));
		stopFloors.add(new TakeRequest(1, 40));
		stopFloors.add(new TakeRequest(2,10));
		cFloor = 2;
		direction = ElevatorDirection.UP;
		status = ElevatorStatus.MOVING;
	}
}
