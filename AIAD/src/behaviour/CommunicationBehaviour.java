package behaviour;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import elevator.Elevator;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.AnswerRequest;
import model.Message;
import model.MessageType;
import model.NewRequest;
import model.SatisfiedRequest;
import model.StatusRequest;
import request.ReceiveRequest;

public abstract class CommunicationBehaviour extends CyclicBehaviour {
	
	public CommunicationBehaviour(Agent agent){
		super(agent);
	}
	
	/**
	 * Reads all the messages received and handles them
	 */
	@Override
	public void action() {
		ACLMessage message = this.myAgent.receive();
		while (message != null) {
			handler(message);
			message = this.myAgent.receive();
		}
	}
	
	/**
	 * Handles the message the agent received
	 * @param message
	 */
	abstract protected void handler(ACLMessage message);
}
