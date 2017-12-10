package tests;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;
import request.ReceiveRequest;
import request.TakeRequest;

/**
 * 
 * This class tests the elevator behaviour
 *
 */
public class ElevatorTestPassengers extends Elevator {
	/**
	 * Constructor of the class ElevatorTestPassengers
	 */
	public ElevatorTestPassengers() {
		super();
		testFloors();
	}
	
	/**
	 * This functions tests the requests of the elevator
	 */
	private void testFloors() {
		cFloor = 2;
		direction = ElevatorDirection.UP;
		status = ElevatorStatus.MOVING;
		
		stopFloors.add(new TakeRequest(1,50, this));
		stopFloors.add(new TakeRequest(14,50, this));
		stopFloors.add(new TakeRequest(5,50, this));
		stopFloors.add(new TakeRequest(10,50, this));
		stopFloors.add(new TakeRequest(11,50, this));
		stopFloors.add(new TakeRequest(19,50, this));
		stopFloors.add(new TakeRequest(1,50, this));
		stopFloors.add(new TakeRequest(12,49, this));
		stopFloors.add(new ReceiveRequest(4, ElevatorDirection.UP, this));
		stopFloors.add(new ReceiveRequest(2, ElevatorDirection.DOWN, this));
		stopFloors.add(new ReceiveRequest(6, ElevatorDirection.UP, this));
		stopFloors.add(new ReceiveRequest(10, ElevatorDirection.DOWN, this));
	}
}
