package request;

import java.util.Random;

import elevator.Elevator;
import elevator.ElevatorDirection;

/**
 * 
 * Creates the requests that a elevator will receive
 *
 */
public class ReceiveRequest extends Request {
	/**
	 * Possible maximum weight
	 */
	public static final int MAX_WEIGHT = 200;
	/**
	 * Possible minimum weight
	 */
	public static final int MIN_WEIGHT = 10;
	/**
	 * Enum value that represents, in this case, the direction the passenger wants go
	 */
	private ElevatorDirection direction;
	
	/**
	 * Minimum number of people
	 */
	private int minPeople;

	/**
	 * ReceiveRequest's constructor
	 * @param floor Request's floor
	 * @param direction Elevator's direction
	 */
	public ReceiveRequest(int floor, ElevatorDirection direction) {
		super(floor);
		this.direction = direction;
		this.minPeople = 1;
	}
	
	/**
	 * ReceiveRequest's constructor
	 * @param floor Request's floor
	 * @param direction Elevator's direction
	 * @param minPeople Minimum number of people of request
	 */
	public ReceiveRequest(int floor, ElevatorDirection direction, int minPeople) {
		super(floor);
		this.direction = direction;
		this.minPeople = minPeople <= 0 ? 1 : minPeople;
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
	 * that represents the floor to which the passenger want to go
	 * @param elevator Elevator that will receive the request
	 */
	@Override
	public void onFloor(Elevator elevator) {
		//- If if does not enter elevator -> end function
		if(!willEnterElevator(elevator))
			return;
		//- Add several (random - the elevator doesn't know 
		//	how many passengers are waiting for it) 
		//	TakeRequests (random floor) to the elevator set
		Random r = new Random();
		int nPeople = r.nextInt(8 - minPeople) + minPeople;
		while(nPeople-- > 0) {
			int weight = generateWeight();
			int floor = generateFloor(elevator);
			if(!elevator.addPassenger(floor, weight))
				break;
		}

		//- Remove this request from the elevator set
		elevator.getStopFloors().remove(this);
		
		// TODO: Warn the building about new request
		/*if(nPeople > 0)
			building.warn();
		*/			
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
	 * @return Whether or not it enter the elevator (true if it enters; false otherwise)
	 */
	public boolean willEnterElevator(Elevator elevator) {
		return elevator.getPassengersWeight() < elevator.elevatorWarningCapacity &&
				(direction == elevator.getDirection() || 
					elevator.getDirection() == ElevatorDirection.NO_DIRECTION ||
					(elevator.getDirection() != direction && 
						elevator.isLastDirection(floor)));
	}
	
	/**
	 * Generate a value that simulates the floor the passenger entering 
	 * the elevator wished to go.
	 * @param elevator Elevator that receives the request
	 * @return an integer value representing the floor
	 */
	private int generateFloor(Elevator elevator) {
		Random r = new Random();
		//get building min floor and max floor
		//currently working with hard-coded values
		int delta_floors;
		if(direction == ElevatorDirection.UP) {
			delta_floors = elevator.maxFloor - floor;
			// 29 - 0 = 29
			int d = r.nextInt(delta_floors) + 1;
			// [0, 28] + 1 = [1, 29]
			return floor + d;
		} else {
			delta_floors = floor - elevator.minFloor;
			int d = r.nextInt(delta_floors) + 1; 
			return floor - d;
		}
	}
	
	/**
	 * Generates the weight of the request
	 * @return The weight generated
	 */
	private int generateWeight() {
		return (new Random()).nextInt(MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT;
	}

	/**
	 * Compares two requests
	 * @param arg0 The request that will be used in the comparison
	 * @return 0 if the requests are equal; -1 if the elevator floor or id is smaller
	 */
	@Override
	public int compareTo(Object arg) {
		Request r = (Request)arg;
		if(r.getClass().isAssignableFrom(ReceiveRequest.class) 
				&& direction == ((ReceiveRequest)r).getDirection())
			return 0;
		return (floor > r.floor || 
				(floor == r.floor && id > r.id)) ? 
					1 : -1;
	}

}
