package behaviour;

import java.util.Set;
import java.util.TreeSet;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import request.Request;

/**
 * 
 * This class creates a behaviour that allows the elevators to take an action
 *
 */
public class TakeActionBehaviour extends TickerBehaviour {
	/**
	 * Action time of the behaviour
	 */
	private static final int ACTION_TIME = 500;
	/**
	 * Elevator that will be used 
	 */
	private Elevator elevator;
	
	/**
	 * TakeActionBehaviour's constructor
	 * @param elevator Elevator that will be used
	 */
	public TakeActionBehaviour(Elevator elevator) {
		super(elevator, ACTION_TIME);
		this.elevator = elevator;
	}
	

	/**
	 * According to the status this method will lead the elevator in a certain way
	 */
	@Override
	protected void onTick() {
		/*System.out.println("Agent " + elevator.getCFloor() + " dir " + elevator.getDirection() + " status " + elevator.getStatus());
		for(Request r : elevator.getStopFloors())
			System.out.print(r.getClass() + " - " + r.getFloor() + " -- ");
		System.out.println();*/
		int usage = 1;
		if(this.elevator.getStatus() == ElevatorStatus.STOPPED && this.elevator.getDirection() == ElevatorDirection.NO_DIRECTION)
			usage = 0;
		elevator.getStatistics().updateValues(elevator.getPassengersWeight() / (double) elevator.elevatorCapacity, usage);
		
		//Stop if this cFloor is a stopFloor
		if(shouldStop()) {
			this.elevator.setStatus(ElevatorStatus.STOPPED);
			onFloor();
			return;
		}

		//Go from stopped to moving
		if(this.elevator.getStatus() == ElevatorStatus.STOPPED)
			this.elevator.setStatus(ElevatorStatus.MOVING);
		
		nextDirection();
		moveElevator();
	}
	
	/**
	 * Calls the onFloor for all requests of a given floor
	 */
	private void onFloor() {
		Set<Request> requests = new TreeSet<Request>(this.elevator.getStopFloors());
		for(Request r : requests) {
			if(r.getFloor() == this.elevator.getCFloor()) {
				r.onFloor(this.elevator);
			}
			//Since it's ordered
			else if(r.getFloor() > this.elevator.getCFloor())
				return;
		}
	}
	
	/**
	 * Iterates through all requests determining if the elevator should, or not,
	 * stop at the current floor
	 * @return true if it should stop at the current floor; false otherwise
	 */
	private boolean shouldStop() {
		for(Request r : this.elevator.getStopFloors())
			if(r.stop(elevator))
				return true;
		return false;
	}
	
	/**
	 * Moves the elevator in a given direction
	 */
	private void moveElevator() {
		if(this.elevator.getStatus() != ElevatorStatus.MOVING)
			return;
		this.elevator.move();
	}
	
	/**
	 * Calculates the next move for the elevator
	 */
	private void nextDirection() {
		this.elevator.changeDirection();
		if(this.elevator.getDirection() == ElevatorDirection.NO_DIRECTION)
			this.elevator.setStatus(ElevatorStatus.STOPPED);
	}
}
