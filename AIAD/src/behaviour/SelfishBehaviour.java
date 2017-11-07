package behaviour;

import elevator.Elevator;

public class SelfishBehaviour extends TakeActionBehaviour {
	
	public SelfishBehaviour(Elevator elevator) {
		super(elevator);
	}
	
	@Override
	public void action() {}

	/**
	 * Returns false
	 */
	@Override
	public boolean done() {
		return false;
	}

}
