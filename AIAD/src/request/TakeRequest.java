package request;

import java.io.IOException;
import java.util.Date;

import elevator.Elevator;
import stats.Statistics;

public class TakeRequest extends Request {
	/**
	 * Value that represents the weight of the passenger
	 */
	private int weight;
	
	public TakeRequest(int floor, int weight, Elevator elevator) {
		super(floor);
		
		this.id = TAKE_ID++;
		
		this.weight = weight;		
		
		this.startTime = new Date();
		this.startElevatorFloor = elevator.getCFloor();
		this.startElevatorDirection = elevator.getDirection();
		
		try {
			Statistics.instance.newRequest(this.id, 
					new Object[] {false, this.id, floor, this.startElevatorFloor, this.startElevatorDirection, this.startElevatorDirection, 
							startTime.getTime(), 0, 0});
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public int getWeight() {
		return weight;
	}

	/**
	 * Called when the elevator gets to this floor.
	 * This function is an override method from the parent class Request
	 * and, in the case of TakeRequest, this function will remove this
	 * request from the set of floors.
	 */
	@Override
	public void onFloor(Elevator elevator) {
		//- Remove this request from the elevator set
		//	this also removes the weight of this passenger
		elevator.getStopFloors().remove(this);
		
		try {
			Date finish = new Date();
			Statistics.instance.newRequest(this.id, 
					new Object[] {false, this.id, floor, this.startElevatorFloor, this.startElevatorDirection, this.startElevatorDirection, 
							startTime.getTime(), finish.getTime(), finish.getTime() - startTime.getTime()});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean stop(Elevator elevator) {
		return floor == elevator.getCFloor();
	}
	
	@Override
	public int compareTo(Object arg) {
		Request r = ((Request)arg);
		if(id != r.id)
			return (floor > r.floor || 
					(floor == r.floor && id > r.id)) ? 
						1 : -1;
		return 0;
	}
}