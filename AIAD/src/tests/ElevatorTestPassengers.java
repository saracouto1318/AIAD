package tests;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;

public class ElevatorTestPassengers extends Elevator {
	public ElevatorTestPassengers() {
		super();
		testFloors();
	}
	
	private void testFloors() {
		stopFloors.add(1);
		stopFloors.add(3);
		stopFloors.add(5);
		stopFloors.add(10);
		stopFloors.add(4);
		stopFloors.add(19);
		stopFloors.add(2);
		stopFloors.add(-1);
		stopFloors.add(-5);
		cFloor = 2;
		direction = ElevatorDirection.UP;
		status = ElevatorStatus.MOVING;
	}
}
