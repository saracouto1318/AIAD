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
import model.NewRequest;

/**
 * 
 * This class initiates the building as a ContractNetInitiator
 *
 */
public class BuildingInitiator extends ContractNetInitiator {
	/**
	 * Number of responders
	 */
	private int nResponders = 1;
	/**
	 * Mesage's content
	 */
	private final NewRequest content;
	/**
	 * Defines which heuristic to use
	 */
	private final boolean heuristic;

	/**
	 * BuildingInitiator's constructor
	 * 
	 * @param a
	 *            Agent that will be the initiator
	 * @param cfp
	 *            ACLMessage
	 * @param content
	 *            Message's content
	 * @param nResponders
	 *            Number of responders
	 */
	public BuildingInitiator(Agent a, ACLMessage cfp, NewRequest content, int nResponders, boolean heuristic) {
		super(a, cfp);
		this.nResponders = nResponders;
		this.content = content;
		this.heuristic = heuristic;
	}
	/*
	 * protected void handlePropose(ACLMessage propose, Vector v) {}
	 * 
	 * protected void handleRefuse(ACLMessage refuse) {}
	 */

	/**
	 * This function handles the failure of ACLMessage
	 * 
	 * @param failure
	 *            ACLMessage
	 */
	protected void handleFailure(ACLMessage failure) {
		nResponders--;
	}

	/**
	 * This function handles with all the responses obtained
	 * 
	 * @param responses
	 *            Vector with all the responses obtained
	 * @param acceptances
	 *            Vector with all the acceptances obtained
	 */
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		if (responses.size() < nResponders) {
			// Some responder didn't reply within the specified timeout
			System.out.println("Timeout expired: missing " + (nResponders - responses.size()) + " responses");
			if (responses.size() == 0) {
				((Building) this.getAgent()).sendMessage(content);
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
			// Check if message is wanted
			if (msg.getPerformative() == ACLMessage.PROPOSE) {
				ACLMessage reply = msg.createReply();
				reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
				acceptances.addElement(reply);

				// Get proposal object
				AnswerRequest proposal;
				try {
					proposal = (AnswerRequest) msg.getContentObject();
				} catch (UnreadableException e1) {
					// If err continue to next iteration
					System.out.println("ERR READING CONTENT");
					continue;
				}

				// If it's better change
				if (isBetterProposal(proposal, bestProposal)) {
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
			((Building) this.getAgent()).sendMessage(content);
		}
	}
	/*
	 * protected void handleInform(ACLMessage inform) {}
	 */
	
	/**
	 * Returns the weight of the given AnswerRequest to take care of the request  
	 * @param proposal AnswerRequest given by the elevator
	 * @return weight
	 */
	private double getProposalWeight(AnswerRequest proposal) {
		if (proposal.getAlreadyExists()) {
			return Integer.MIN_VALUE;
		}
		if(proposal.getId() != content.getId())
			System.out.println("Its different " + proposal.getId() + " vs " + content.getId());
		
		double weight = 0;
		double percentUsedCapcacity = (proposal.getPassengersWeight() / proposal.getElevatorCapacity()) * 100;
		double dist;			
		
		if (content.getDirection() != proposal.getDirection()) {
			dist = Math.abs(proposal.getFloor() - proposal.getLastFloorInDirection())
					+ Math.abs(proposal.getLastFloorInDirection() - content.getFloor());
		} else {
			dist = Math.abs(content.getFloor() - proposal.getFloor());
		}
		
		if(this.heuristic) {
			weight += dist + percentUsedCapcacity/(double)10;
		} else {
			weight += dist + percentUsedCapcacity/10 + proposal.getNumStopFloors();
		}
		
		return weight;
	}

	/**
	 * Verifies if a proposal is better than the best proposal obtained until
	 * now
	 * 
	 * @param proposal
	 *            Proposal to be compared
	 * @param bestProposal
	 *            Bes proposal until now
	 * @return true if the new proposal is better; false otherwise
	 */
	private boolean isBetterProposal(AnswerRequest proposal, AnswerRequest bestProposal) {
		return bestProposal == null || getProposalWeight(proposal) < getProposalWeight(bestProposal);
	}
}
