package contract;

import java.util.Vector;

import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class BuildingInitiator extends ContractNetInitiator {

	public BuildingInitiator(Agent a, ACLMessage cfp) {
		super(a, cfp);
	}
	
	protected void handlePropose(ACLMessage propose, Vector v) {
		System.out.println("Building Handle propose");
		//TODO: Define if this should do something
	}
	
	protected void handleRefuse(ACLMessage refuse) {
		System.out.println("Building Handle reject");
		//TODO: This will never happen - send exception
	}
	
	protected void handleFailure(ACLMessage failure) {
		System.out.println("Building Handle failure");
		//TODO: Failure to execute request
	}
	
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		System.out.println("Building Handle responses");
		//TODO: Algorithm that determines the best proposal
	}
	
	protected void handleInform(ACLMessage inform) {
		System.out.println("Building Handle inform");
		//TODO: Define if this should receive response when the elevator accepts or when it satisfies the request
	}

}
