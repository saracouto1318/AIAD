package behaviour;

import building.Building;
import model.Message;
import model.MessageType;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

/**
 * 
 * This class creates the communication in the building
 *
 */
public class BuildingCommunicationBehaviour extends CommunicationBehaviour {

	/**
	 * BuildingCommunicationBehaviour's constructor
	 * @param building Building where the communication will be created
	 */
	public BuildingCommunicationBehaviour(Building building) {
		super(building);
	}

	/**
	 * Handles the message received
	 * @param message Message received
	 */
	@Override
	protected void handler(ACLMessage message) {
		Message content;
		MessageType type;
		try {
			content = (Message) message.getContentObject();
		} catch (UnreadableException e) {
			return;
		}
		type = MessageType.getMessageType(content);
		switch (type) {
		case ANSWER:
			((Building) this.myAgent).receiveRequestResponse(content.getId(), content);
			break;
		case RENEGOTIATE:
			break;
		case SATISFIED:
			break;
		default:
			return;
		}
	}
}
