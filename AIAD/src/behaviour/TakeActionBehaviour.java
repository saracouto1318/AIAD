package behaviour;

import elevators.Elevator;
import elevators.ElevatorDirection;
import elevators.ElevatorStatus;
import jade.core.behaviours.Behaviour;

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
			this.elevator.getStopFloors().remove(this.elevator.getCFloor());
			this.elevator.setStatus(ElevatorStatus.MOVING);
			
			//Check next move's direction
			nextDirection();			
		}
		//Stop if this cFloor is a stopFloor
		else if(this.elevator.getStopFloors().contains(this.elevator.getCFloor())) {
			System.out.println("Elevator stopped to leave some passengers");
			this.elevator.setStatus(ElevatorStatus.STOPPED);
		}

		if(this.elevator.getDirection() == ElevatorDirection.NO_DIRECTION)
			System.out.println("Ending program now");
		
		moveElevator();
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
		else if((this.elevator.getDirection() == ElevatorDirection.UP && 
				this.elevator.isCFloorAbove()) ||
			(this.elevator.getDirection() == ElevatorDirection.DOWN && 
				this.elevator.isCFloorBelow())) {
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
		return this.elevator.getDirection() == ElevatorDirection.NO_DIRECTION;
	}

}
