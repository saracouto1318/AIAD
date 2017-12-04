package behaviour;

import building.Building;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class BuildingCommunicationBehaviour extends CommunicationBehaviour {

	public BuildingCommunicationBehaviour(Building building) {
		super(building);
	}

	@Override
	protected void handler(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}
}
