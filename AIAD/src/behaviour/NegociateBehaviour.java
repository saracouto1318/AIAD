package behaviour;

import elevator.Elevator;
import jade.core.behaviours.Behaviour;

public class NegociateBehaviour extends TakeActionBehaviour {

	private Behaviour hello_behaviour;
	private String id;
	
	public NegociateBehaviour(Elevator elevator, Behaviour behaviour, String id) {
		super(elevator);
		this.hello_behaviour = behaviour;
		this.id = id;
	}

}
