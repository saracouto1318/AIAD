package behaviour;

import elevator.Elevator;
import jade.core.behaviours.Behaviour;

/**
 * 
 * This class creates a behaviour that takes into account the negotiation between elevators
 *
 */
public class NegociateBehaviour extends TakeActionBehaviour {

	/**
	 * Behaviour used
	 */
	private Behaviour hello_behaviour;
	/**
	 * Behaviour's identifier
	 */
	private String id;
	
	/**
	 * NegociateBehaviour's constructor
	 * @param elevator Agent "Elevator"
	 * @param behaviour Behaviour used
	 * @param id Behaviour's identifier
	 */
	public NegociateBehaviour(Elevator elevator, Behaviour behaviour, String id) {
		super(elevator);
		this.hello_behaviour = behaviour;
		this.id = id;
	}

}
