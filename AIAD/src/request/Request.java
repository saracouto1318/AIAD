package request;

import elevator.Elevator;

public abstract class Request implements Comparable {
	private static int ID = 0;
	
	protected int id = ID++;
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
		return id == ((Request)obj).id;
	}

	@Override
	public int hashCode() {
		return floor;
	}
	
	@Override
	public abstract int compareTo(Object arg0);
}
