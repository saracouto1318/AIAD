package request;

import elevator.Elevator;

public abstract class Request implements Comparable {
	private static long ID = 0;
	
	protected long id = ID++;
	protected int floor;
	
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
