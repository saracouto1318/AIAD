package contract;

import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

public class ElevatorResponder extends ContractNetResponder {

	public ElevatorResponder(Agent a, MessageTemplate mt) {
		super(a, mt);
	}

	protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
		//TODO: Send a meaningful message instead of 3
		/*
		System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
		int proposal = evaluateAction();
		if (proposal > 2) {
			// We provide a proposal
			System.out.println("Agent "+getLocalName()+": Proposing "+proposal);
		*/
		ACLMessage propose = cfp.createReply();
		propose.setPerformative(ACLMessage.PROPOSE);
		propose.setContent(String.valueOf(3));
		return propose;
		/*
		}
		else {
			// We refuse to provide a proposal
			System.out.println("Agent "+getLocalName()+": Refuse");
			throw new RefuseException("evaluation-failed");
		}
		*/
	}
	
	protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
		//TODO: Sends inform message - to be defined
		/*
		System.out.println("Agent "+getLocalName()+": Proposal accepted");
		if (performAction()) {
			System.out.println("Agent "+getLocalName()+": Action successfully performed");
		*/
		ACLMessage inform = accept.createReply();
		inform.setPerformative(ACLMessage.INFORM);
		return inform;
		/*
		}
		else {
			System.out.println("Agent "+getLocalName()+": Action execution failed");
			throw new FailureException("unexpected-error");
		}
		*/
	}
	
	protected void handleRejectProposal(ACLMessage reject) {
		//TODO: Define what the rejection means to the elevator
	}
}
