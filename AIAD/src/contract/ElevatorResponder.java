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

public class ElevatorResponder extends ContractNetResponder {
	public ElevatorResponder(Elevator a, MessageTemplate mt) {
		super(a, mt);
	}
	
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
	
	protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
		handleAccept(cfp);
		ACLMessage inform = accept.createReply();
		inform.setPerformative(ACLMessage.INFORM);
		return inform;
	}
	/*
	protected void handleRejectProposal(ACLMessage reject) {}
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
		System.out.println("New request accepted for elevator " + this.getAgent().getAID().getLocalName() + " on floor " + request.getFloor());
		((Elevator)this.getAgent()).getStopFloors().add(new ReceiveRequest(request.getFloor(), request.getDirection()));
	}
	
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
	
	private Message replyToNew(ACLMessage reply, NewRequest request) throws RefuseException {
		// Create a reply message
		Elevator elevator = (Elevator) this.myAgent;
		return new AnswerRequest(request.getId(), elevator.getDirection(),
				elevator.getCFloor(), elevator.getLastFloorInDirection(), elevator.getPassengersWeight(),
				elevator.getStopFloors().size());
	}
}
