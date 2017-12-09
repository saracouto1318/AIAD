package behaviour;

import java.util.Set;
import java.util.TreeSet;

import elevator.Elevator;
import elevator.ElevatorDirection;
import elevator.ElevatorStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import request.Request;

public class TakeActionBehaviour extends TickerBehaviour {
	private static final int ACTION_TIME = 500;
	private Elevator elevator;
	
	public TakeActionBehaviour(Elevator elevator) {
		super(elevator, ACTION_TIME);
		this.elevator = elevator;
	}
	

	/**
	 * According to the status this method will lead the elevator in a certain way
	 */
	@Override
	protected void onTick() {
		if(this.elevator.getStatus() == ElevatorStatus.STOPPED) {
			//Go from stopped to moving
			this.elevator.setStatus(ElevatorStatus.MOVING);
			onFloor();
			nextDirection();			
		}
		//Stop if this cFloor is a stopFloor
		else if(shouldStop()) {
			this.elevator.setStatus(ElevatorStatus.STOPPED);
		}
		//Might be time to change direction
		else {
			nextDirection();
		}
		
		moveElevator();		
		
		/*StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append(this.elevator.getAID()).append(" ").append(this.elevator.getCFloor()).append(" ");
		for(Request r : this.elevator.getStopFloors())
			strBuilder.append(" ").append(r.getClass()).append(":").append(r.getFloor());
		System.out.println(strBuilder.toString());*/
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
		/*//If stop floors is empty then there are no more requests
		if(this.elevator.getStopFloors().isEmpty())
			this.elevator.setDirection(ElevatorDirection.NO_DIRECTION);
		
		//Check if moving in the current elevator direction will lead them to a stop
		else if(this.elevator.isLastDirection(this.elevator.getCFloor())) {
			//If it doesn't -> Change direction*/
		this.elevator.changeDirection();
		//}
	}
}
