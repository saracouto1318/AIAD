package elevators;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 * 	Type of behaviour for elevators that knows the number of people currently riding the elevator, 
 * but also, nows how many people are waiting for it on each floor.
 * 	This behaviour might be better when there are, frequently, floors where the number of people waiting 
 * for an elevator is bigger than the elevator's capacity.
 */
public class NumberOfPeopleBehaviour extends SimpleBehaviour {

	public NumberOfPeopleBehaviour(Agent a) {
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
