package behaviour;

import java.io.IOException;
import java.util.Random;

import elevator.Elevator;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import model.AnswerBuilding;
import model.BuildingRequest;

public class CommunicationBehaviour extends CyclicBehaviour {
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
		if(message != null)
			handler(message);
	}
	
	/**
	 * 
	 * @param message
	 */
	private void handler(ACLMessage message) {
		BuildingRequest request;
		ACLMessage reply;
		AnswerBuilding answer;

		//Get message content
		try {
			request = (BuildingRequest) message.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
			return;
		}
		
		//Create a reply message
		reply = message.createReply();
		answer = new AnswerBuilding(request.getId(), diff(request.getFloor()), capacity(), numRequests());
		try {
			reply.setContentObject(answer);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//Send message
		elevator.send(reply);
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
