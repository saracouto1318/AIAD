package contract;

import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;
import model.AnswerRequest;
import model.Message;
import model.MessageType;
import model.NewRequest;
import model.SatisfiedRequest;
import model.StatusRequest;
import request.ReceiveRequest;

import java.io.IOException;

import elevator.Elevator;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

/**
 * 
 * This class initiates the elevator agent as a ContractNetResponder
 *
 */
public class ElevatorResponder extends ContractNetResponder {
	/**
	 * ElevatorResponder's constructor
	 * @param a Agent "Elevator"
	 * @param mt Message template
	 */
	public ElevatorResponder(Elevator a, MessageTemplate mt) {
		super(a, mt);
	}
	
	/**
	 * This function prepares a response to the message sent by the Building
	 * @param cfp Message sent by the Building
	 * @return The response to the message sent by the Building
	 * @throws RefuseException
	 */
	protected ACLMessage prepareResponse(ACLMessage cfp) throws RefuseException {
		((Elevator)this.getAgent()).initiateNewResponder();
		
		// We provide a proposal
		ACLMessage propose = cfp.createReply();
		propose.setPerformative(ACLMessage.PROPOSE);
		try {
			propose.setContentObject(getReply(cfp));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RefuseException("content-failure");
		}
		
		return propose;
	}
	
	/**
	 * Prepares the notification of the result of the negotiation
	 * @param cfp The accept message
	 * @param propose The propose message
	 * @param accept The accept message used to get the reply
	 * @return  The ACLMessage that informs about the state of the negotiation
	 * @throws FailureException This class represents a generic FailureException
	 */
	protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
		handleAccept(cfp);
		ACLMessage inform = accept.createReply();
		inform.setPerformative(ACLMessage.INFORM);
		return inform;
	}
	/*
	protected void handleRejectProposal(ACLMessage reject) {}
	*/
	/**
	 * Handles the accept message sent by the initiator
	 * @param cfp Accpet message sent
	 */
	private void handleAccept(ACLMessage cfp) 
	{
		NewRequest request;
		try {
			request = (NewRequest) cfp.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
			return;
		}
		System.out.println(this.getAgent().getAID().getLocalName() + " new request " + request.getFloor());
		((Elevator)this.getAgent()).getStopFloors().add(new ReceiveRequest(request.getFloor(), request.getDirection()));
	}
	
	/**
	 * Gets the reply to the proposal
	 * @param message Message replied
	 * @return The message answered to the request
	 * @throws RefuseException This class represents a generic RefuseException
	 */
	private Message getReply(ACLMessage message) throws RefuseException {		
		Message request;
		MessageType type;

		// Get message content
		try {
			request = (Message) message.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
			return null;
		}

		// Handle request depending on it's type
		type = MessageType.getMessageType(request);
		switch (type) {
		case NEW:
			return replyToNew(message.createReply(), (NewRequest) request);
		case STATUS:
			//replyToStatus((StatusRequest) request);
			break;
		case SATISFIED:
			//replyToSatisfied((SatisfiedRequest) request);
			break;
		default:
			return null;
		}
		return null;
	}
	
	/**
	 * Replies to new requests
	 * @param reply Reply message
	 * @param request New request
	 * @return The message answered to the request
	 * @throws RefuseException This class represents a generic RefuseException
	 */
	private Message replyToNew(ACLMessage reply, NewRequest request) throws RefuseException {
		// Create a reply message
		Elevator elevator = (Elevator) this.myAgent;
		return new AnswerRequest(request.getId(), elevator.getDirection(),
				elevator.getCFloor(), elevator.getLastFloorInDirection(), elevator.getPassengersWeight(),
				elevator.getStopFloors().size());
	}
}
