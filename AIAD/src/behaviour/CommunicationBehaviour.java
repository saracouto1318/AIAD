package behaviour;

import java.io.IOException;

import com.sun.org.apache.bcel.internal.generic.NEW;

import elevator.Elevator;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.*;

public class CommunicationBehaviour extends CyclicBehaviour {
	/*
		Behaviour 		- Reads and sends messages
		Negotiation		- Every message sent by the building will be read by this behaviour. If that message is a new request 
						then the elevator will calculate it's availability and send it to the building. 
						The building will then broadcast a message to all elevators.
		Renegotiation	- Elevator allocation might be dynamic. Some elevators might have this functionality, 
						where if it thinks it has a more efficient path to the floor than the previously allocated elevator, 
						then it sends a negotiation message to the building saying it might be better to change the request 
						to a different elevator. This will then lead the building to either respond that the request was 
						already answered or it will broadcast a message to both elevators (this one and the one that has the request) 
						and both will once again negotiate.
		Answered		- When an elevator answers a request than it send a message to the building.
						The building broadcasts a message to all elevators letting them know the request was already
						answered.
	 */
	private Elevator elevator;
	
	public CommunicationBehaviour(Elevator elevator) {
		this.elevator = elevator;
	}

	/**
	 * Reads all the messages received and handles them
	 */
	@Override
	public void action() {
		ACLMessage message = this.elevator.receive();
		while(message != null)
			handler(message);
	}
	
	/**
	 * 
	 * @param message
	 */
	private void handler(ACLMessage message) {
		Message request;
		MessageType type;

		//Get message content
		try {
			request = (Message) message.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
			return;
		}
		
		//Handle request depending on it's type
		type = MessageType.getMessageType(request);
		switch(type) {
			case NEW:
				handleNew(message.createReply(), (NewRequest)request);
				break;
			case ACK:
				handleAck((AckRequest)request);
				break;
			case REJ:
				handleRej((RejRequest)request);
				break;
			default:
				return;
		}
	}
	
	private void handleNew(ACLMessage reply, NewRequest request) {
		//Create a reply message
		AnswerRequest answer = new AnswerRequest(/*request.getId(), diff(request.getFloor()), capacity(), numRequests()*/);
		try {
			reply.setContentObject(answer);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//Send message
		elevator.send(reply);
	}
	
	private void handleAck(AckRequest request) {
		//TODO: Set new ReceiveRequest
		//elevator.getStopFloors().add(new ReceiveRequest());
	}
	
	private void handleRej(RejRequest request) {
		//TODO: add floor to the rejected floors
	}
	
	/**
	 * Calculates how available this elevator is to answer a request.
	 * The elevator with the availability closer to zero gets the request.
	 * Scale: 0 - (2 * #floors - 1)
	 * @param request The requested floor
	 * @return int value representing the availability of this elevator. 
	 * The Integer is 0 if the elevator can answer the request right away and increases the further it is from being able to answer.
	 */
	public int diff(int request) {
		return Math.abs(elevator.getCFloor() - request);
	}
	
	public int capacity() {
		return this.elevator.ELEVATOR_CAPACITY - this.elevator.getPassengersWeight();
	}
	
	public int numRequests() {
		return this.elevator.getStopFloors().size();
	}

}
