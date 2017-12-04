package behaviour;

import building.Building;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class BuildingCommunicationBehaviour extends CyclicBehaviour {

	private Building building;

	public BuildingCommunicationBehaviour(Building building) {
		this.building = building;
	}

	@Override
	public void action() {
		ACLMessage message;
		do {
			message = this.building.receive();
		} while (message != null);
	}

}
