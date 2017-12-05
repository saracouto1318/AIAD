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
		//TODO: Define if this should do something
	}
	
	protected void handleRefuse(ACLMessage refuse) {
		//TODO: This will never happen - send exception
	}
	
	protected void handleFailure(ACLMessage failure) {
		//TODO: Failure to execute request
	}
	
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		//TODO: Algorithm that determines the best proposal
	}
	
	protected void handleInform(ACLMessage inform) {
		//TODO: Define if this should receive response when the elevator accepts or when it satisfies the request
	}

}
