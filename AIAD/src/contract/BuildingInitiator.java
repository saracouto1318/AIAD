package contract;

import java.util.Enumeration;
import java.util.Vector;

import building.Building;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;
import model.AnswerRequest;
import model.Message;

public class BuildingInitiator extends ContractNetInitiator {
	private int nResponders = 1;
	private Message content;
	
	public BuildingInitiator(Agent a, ACLMessage cfp, Message content, int nResponders) {
		super(a, cfp);
		this.nResponders = nResponders;
		this.content = content;
	}
	/*
	protected void handlePropose(ACLMessage propose, Vector v) {}
	
	protected void handleRefuse(ACLMessage refuse) {}
	*/
	protected void handleFailure(ACLMessage failure) {
		nResponders--;
	}
	
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		if (responses.size() < nResponders) {
			// Some responder didn't reply within the specified timeout
			System.out.println("Timeout expired: missing "+(nResponders - responses.size())+" responses");
			if(responses.size() == 0) {
				((Building)this.getAgent()).sendMessage(content);
				return;
			}
		}
		// Evaluate proposals.
		AnswerRequest bestProposal = null;
		AID bestProposer = null;
		ACLMessage accept = null;
		Enumeration e = responses.elements();
		while (e.hasMoreElements()) {
			ACLMessage msg = (ACLMessage) e.nextElement();
			//Check if message is wanted
			if (msg.getPerformative() == ACLMessage.PROPOSE) {
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
				acceptances.addElement(reply);
				
				//Get proposal object
				AnswerRequest proposal;
				try {
					proposal = (AnswerRequest)msg.getContentObject();
				} catch (UnreadableException e1) {
					//If err continue to next iteration
					System.out.println("ERR READING CONTENT");
					continue;
				}
				
				//If it's better change
				if(isBetterProposal(proposal, bestProposal)) {
					bestProposal = proposal;
					bestProposer = msg.getSender();
					accept = reply;
				}
			}
		}
		
		// Accept the proposal of the best proposer
		if (accept != null) {
			accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
		} else {
			System.out.println("ACCEPT NULL");
			((Building)this.getAgent()).sendMessage(content);
		}
	}
	/*
	protected void handleInform(ACLMessage inform) {}
	*/
	private boolean isBetterProposal(AnswerRequest proposal, AnswerRequest bestProposal) {
		return bestProposal == null || proposal.getStopFloorsLength() < bestProposal.getStopFloorsLength();
	}
}
