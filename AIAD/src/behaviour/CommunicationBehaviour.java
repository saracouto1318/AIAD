package behaviour;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * 
 * Creates the communication that will be used by the building or by the elevators
 *
 */
public abstract class CommunicationBehaviour extends CyclicBehaviour {
	
	/**
	 * CommunicationBehaviour's constructor
	 * @param agent Agent that will have the communication associated
	 */
	public CommunicationBehaviour(Agent agent){
		super(agent);
	}
	
	/**
	 * Reads all the messages received and handles them
	 */
	@Override
	public void action() {
		ACLMessage message = this.myAgent.receive();
		if (message != null) {
			handler(message);
			//message = this.myAgent.receive();
		}
	}
	
	/**
	 * Handles the message the agent received
	 * @param message
	 */
	abstract protected void handler(ACLMessage message);
}
