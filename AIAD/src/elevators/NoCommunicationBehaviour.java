package elevators;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 * 	Type of behavior for the elevator that doesn't support communication between elevators.
 * 	Different behaviors on elevators of the same building is possible, therefore, 
 * if this elevator receives messages from others it ignores them
 */
class NoCommunicationBehaviour extends SimpleBehaviour {

	public NoCommunicationBehaviour(Agent a) {
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
