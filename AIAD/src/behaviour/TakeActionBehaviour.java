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
			Integer nFloor = this.elevator.removeStopFloor(this.elevator.getCFloor());
			this.elevator.setStatus(ElevatorStatus.MOVING);
			
			//Change direction if it has no next floor
			if(nFloor == null)
				this.elevator.changeDirection();
			
		}
		//Stop if this cFloor is a stopFloor
		else if(this.elevator.getStopFloors().contains(this.elevator.getCFloor())) {
			this.elevator.setStatus(ElevatorStatus.STOPPED);
			return ;
		}

		this.elevator.move();
	}

	/**
	 * Returns false
	 */
	@Override
	public boolean done() {
		return false;
	}

}
