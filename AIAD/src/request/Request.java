package request;

import java.util.Date;

import elevator.Elevator;
import elevator.ElevatorDirection;

public abstract class Request implements Comparable {
	protected static int RECEIVE_ID = 0;
	protected static int TAKE_ID = 0;
	
	protected int id;
	protected int floor;	
	
	protected Date startTime;
	protected int startElevatorFloor;
	protected ElevatorDirection startElevatorDirection;
	
	public Request(int floor) {
		this.floor = floor;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public abstract void onFloor(Elevator elevator);
	
	public abstract boolean stop(Elevator elevator);
	
	@Override
	public abstract int compareTo(Object arg0);
}
