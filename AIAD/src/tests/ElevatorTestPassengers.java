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
		stopFloors.add(new TakeRequest(1,50));
		stopFloors.add(new TakeRequest(14,50));
		stopFloors.add(new TakeRequest(5,50));
		stopFloors.add(new TakeRequest(10,50));
		stopFloors.add(new TakeRequest(11,50));
		stopFloors.add(new TakeRequest(19,50));
		stopFloors.add(new TakeRequest(1,50));
		stopFloors.add(new TakeRequest(12,49));
		stopFloors.add(new ReceiveRequest(4, ElevatorDirection.UP));
		stopFloors.add(new ReceiveRequest(2, ElevatorDirection.DOWN));
		stopFloors.add(new ReceiveRequest(6, ElevatorDirection.UP));
		stopFloors.add(new ReceiveRequest(10, ElevatorDirection.DOWN));
		cFloor = 2;
		direction = ElevatorDirection.UP;
		status = ElevatorStatus.MOVING;
	}
}
