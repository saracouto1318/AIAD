package behaviour;

import java.util.Set;
import java.util.TreeSet;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;
import jade.core.behaviours.Behaviour;
import request.Request;

public class TakeActionBehaviour extends Behaviour {
	private Elevator elevator;
	
	public TakeActionBehaviour(Elevator elevator) {
		this.elevator = elevator;
	}
	
	/**
	 * According to the status this method will lead the elevator in a certain way
	 */
	@Override
	public void action() {
		if(this.elevator.getStatus() == ElevatorStatus.STOPPED) {
			//Go from stopped to moving
			this.elevator.setStatus(ElevatorStatus.MOVING);
			onFloor();
			nextDirection();			
		}
		//Stop if this cFloor is a stopFloor
		else if(shouldStop()) {
			System.out.println("Elevator stopped to leave some passengers");
			this.elevator.setStatus(ElevatorStatus.STOPPED);
		}
		
		moveElevator();
	}
	
	/**
	 * Calls the onFloor for all requests of a given floor
	 */
	private void onFloor() {
		Set<Request> requests = new TreeSet<Request>(this.elevator.getStopFloors());
		for(Request r : requests) {
			if(r.getFloor() == this.elevator.getCFloor())
				r.onFloor(this.elevator);
			//Since it's ordered
			else if(r.getFloor() > this.elevator.getCFloor())
				return;
		}
	}
	
	/**
	 * Iterates through all requests determining if the elevator should, or not,
	 * stop at the current floor
	 * @return
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
		//If stop floors is empty then there are no more requests
		if(this.elevator.getStopFloors().isEmpty())
			this.elevator.setDirection(ElevatorDirection.NO_DIRECTION);
		
		//Check if moving in the current elevator direction will lead them to a stop
		else if(this.elevator.isLastDirection(this.elevator.getCFloor())) {
			//If it doesn't -> Change direction
			this.elevator.changeDirection();
			System.out.println("Changing direction");
		}
	}

	/**
	 * Returns true when there are no more requests on the set
	 */
	@Override
	public boolean done() {
		return false;
	}
}
