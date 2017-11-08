package request;

import java.util.Random;

import elevator.Elevator;
import elevator.ElevatorDirection;

public class ReceiveRequest extends Request {
	/**
	 * Enum value that represents, in this case, the direction the passenger wants go
	 */
	private ElevatorDirection direction;
	

	public ReceiveRequest(int floor, ElevatorDirection direction) {
		super(floor);
		//Return exception if Direction is NO_DIRECTION
		this.direction = direction;
	}
	
	/**
	 * Getter for the value direction
	 * @return direction
	 */
	public ElevatorDirection getDirection() {
		return direction;
	}

	/**
	 * Called when the elevator gets to this floor.
	 * This function is an override method from the parent class Request
	 * and, in the case of ReceiveRequest, this function will remove this
	 * request from the set of floors and it'll add a new TakeRequest
	 * that represents the floor to which the passenger want to go.
	 */
	@Override
	public void onFloor(Elevator elevator) {
		//- If if does not enter elevator -> end function
		if(!willEnterElevator(elevator))
			return;
		//- Remove this request from the elevator set
		elevator.getStopFloors().remove(this);
		//- Add several (random - the elevator doesn't know 
		//	how many passengers are waiting for it) 
		//	TakeRequests (random floor) to the elevator set
		Random r = new Random();
		int nPeople = r.nextInt(4) + 1;
		while(nPeople-- > 0)
			elevator.getStopFloors().add(
					new TakeRequest(generateFloor(),10));
	}
	
	/**
	 * Determines if the elevator should stop for this request
	 * @return Whether or not it should stop
	 */
	@Override
	public boolean stop(Elevator elevator) {
		return floor == elevator.getCFloor() && 
				willEnterElevator(elevator);
	}
	
	/**
	 * Determines if, considering that the elevator is in this floor,
	 * the passenger will enter the elevator
	 * @param elevator Elevator the is used to determine
	 * @return whether or not it enter the elevator
	 */
	public boolean willEnterElevator(Elevator elevator) {
		return direction == elevator.getDirection() || 
				elevator.getDirection() == ElevatorDirection.NO_DIRECTION ||
				(elevator.getDirection() != direction && 
					elevator.isLastDirection(floor));
	}
	
	/**
	 * Generate a value that simulates the floor the passenger entering 
	 * the elevator wished to go.
	 * @return an integer value representing the floor
	 */
	private int generateFloor() {
		Random r = new Random();
		//get building min floor and max floor
		//currently working with hard-coded values
		int delta_floors;
		if(direction == ElevatorDirection.UP) {
			delta_floors = Elevator.MAX_FLOOR - floor - 1;
			return r.nextInt(delta_floors) + floor + 1;
		} else {
			delta_floors = floor - Elevator.MIN_FLOOR - 1;
			return r.nextInt(delta_floors) - floor;
		}
	}

	@Override
	public int compareTo(Object arg) {
		Request r = (Request)arg;
		if((r.getClass().isAssignableFrom(ReceiveRequest.class) 
				&& direction == ((ReceiveRequest)r).getDirection()) 
				|| floor != r.floor)
			return floor > r.floor ? 1 : -1;

		if(id != r.id)
			return (floor > r.floor || 
					(floor == r.floor && id > r.id)) ? 
						1 : -1;
		return 0;
	}

}
