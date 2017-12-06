package contract;

import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
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
	
	protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
		System.out.println("Agent "+ this.getAgent().getLocalName()+": CFP received from "+cfp.getSender().getName()+". Action is "+cfp.getContent());
		
		((Elevator)this.getAgent()).initiateNewResponder();
		
		double proposal = Math.random();
		// We provide a proposal
		System.out.println("Agent "+ this.getAgent().getLocalName()+": Proposing "+proposal);
		ACLMessage propose = cfp.createReply();
		propose.setPerformative(ACLMessage.PROPOSE);
		propose.setContent(String.valueOf(proposal));
		return propose;
	}
	
	protected ACLMessage prepareResultNotification(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
		System.out.println("Agent "+this.getAgent().getLocalName()+": Action successfully performed");
		ACLMessage inform = accept.createReply();
		inform.setPerformative(ACLMessage.INFORM);
		return inform;
	}
	
	protected void handleRejectProposal(ACLMessage reject) {
		System.out.println("Agent "+ this.getAgent().getLocalName()+": Proposal rejected");
	}
}
