package behaviour;

import elevator.Elevator;

/**
 * This class creates a selfish behaviour which doesn't take into account the negotiation between elevators
 *
 */
public class SelfishBehaviour extends TakeActionBehaviour {
	
	/**
	 * SelfishBehaviour's constructor
	 * @param elevator Agent "Elevator"
	 */
	public SelfishBehaviour(Elevator elevator) {
		super(elevator);
	}
}
