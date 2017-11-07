package request;

import elevator.Elevator;

public abstract class Request implements Comparable {
	protected int floor;
	
	public Request(int floor) {
		this.floor = floor;
	}
	
	public int getFloor() {
		return floor;
	}
	
	public abstract void onFloor(Elevator elevator);

	@Override
	public boolean equals(Object obj) {
		return floor == ((Request)obj).getFloor();
	}

	@Override
	public int hashCode() {
		return floor;
	}
	
	@Override
	public abstract int compareTo(Object arg0);
}
