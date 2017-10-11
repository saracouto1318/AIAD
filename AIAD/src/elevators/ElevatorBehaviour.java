package elevators;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;


/**
 * 	Type of behavior for the elevators that uses communication between elevators to accomplish 
 * a better solution for the problem that is elevator allocation.
 * 	This behavior improves on the NoCommunication behavior in the way that elevators might negotiate
 * which one has a better chance to serve a request.
 */
public class ElevatorBehaviour extends SimpleBehaviour {

	public ElevatorBehaviour(Agent a) {
		super(a);
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
