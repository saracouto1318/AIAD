package request;

import elevator.Elevator;

public abstract class Request {
	protected int floor;
	
	public Request(int floor) {
		this.floor = floor;
	}
	
	public abstract void onFloor(Elevator elevator);
}
