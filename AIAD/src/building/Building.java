package building;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import behaviour.*;
import contract.BuildingInitiator;
import elevator.Elevator;
import elevator.ElevatorDirection;
import model.Message;
import model.NewRequest;

/**
 * 
 * This class creates the agent "Building"
 *
 */
public class Building extends Agent {
	/**
	 * Building's bottom floor
	 */
	private int bottomFloor;
	/**
	 * Building's top floor
	 */
	private int topFloor;
	/**
	 * Building's number of floors
	 */
	private int numberOfFloors;
	/**
	 * Frequency of requests
	 */
	private int requestFreq;
	/**
	 * List of AID of the elevators
	 */
	private List<AID> elevators;
	
	/**
	 * Default frequency
	 */
	private final static double DEFAULT_FREQ = 1.0;

	/**
	 * Map with the floors' frequency
	 */
	private final static Map<Integer, Double> FLOOR_FREQ = new HashMap<Integer, Double>();

	/**
	 * Map with the responses of a request
	 */
	private Map<Integer, List<Message>> requestResponses;

	/**
	 * 	List of request's messages
	 */
	private List<Message> requests;

	static {
		FLOOR_FREQ.put(0, 0.5);
	}

	/**
	 * Sets up the "Building" agent
	 */
	@Override
	protected void setup() {
		Object[] args = getArguments();
		try {
			bottomFloor = Integer.parseInt(args[0].toString());
			topFloor = Integer.parseInt(args[1].toString());
			requestFreq = Integer.parseInt(args[2].toString());
		} catch (ArrayIndexOutOfBoundsException exc) {
			throw (exc);
		}
		numberOfFloors = topFloor - bottomFloor + 1;
		requestResponses = new HashMap<Integer, List<Message>>();
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("Agent building");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		
		elevators = Elevator.getAllElevators(this);

		// Create behaviour
		GenerateRequestsBehaviour reqBehaviour = new GenerateRequestsBehaviour(this);
		this.addBehaviour(reqBehaviour);
		/*CommunicationBehaviour comBehaviour = new BuildingCommunicationBehaviour(this);
		this.addBehaviour(comBehaviour);*/
	}

	/**
	 * Takes down the "Building" agent
	 */
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the building's bottom floor
	 * @return The building's bottom floor
	 */
	public int getBottomFloor() {
		return bottomFloor;
	}

	/**
	 * Gets the building's top floor
	 * @return The building's top floor
	 */
	public int getTopFloor() {
		return topFloor;
	}

	/**
	 * Gets the number of floor of the building
	 * @return The number of floor of the building
	 */
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	/**
	 * Gets the frequency of a floor
	 * @param floor The floor that will be analyzed
	 * @return The frequency of the floor
	 */
	public double getFreqOfFloor(int floor) {
		Double res = FLOOR_FREQ.get(floor);
		return res == null ? DEFAULT_FREQ : res;
	}

	/**
	 * Gets the request's frequency
	 * @return The request's frequency
	 */
	public int getRequestFreq() {
		return requestFreq;
	}

	/**
	 * Gets the request's frequency of a certain floor
	 * @param floor Floor that will be analyzed
	 * @return The request's frequency of a certain floor
	 */
	public int getRequestFreqOfFloor(int floor) {
		return (int)(getFreqOfFloor(floor) * getRequestFreq());
	}

	/**
	 * Sends a message to the elevators
	 * @param message Message that will be sent
	 */
	public void sendMessage(Message message) {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		for(AID aid : elevators)
			msg.addReceiver(aid);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		// We want to receive a reply in 100 milliseconds
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 250));
		try {
			msg.setContentObject(message);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.addBehaviour(new BuildingInitiator(this, msg, message, elevators.size()));
	}

	/**
	 * Receives a response to a request
	 * @param requestId Request's identifier
	 * @param response Response message
	 */
	public void receiveRequestResponse(int requestId, Message response) {
		List<Message> list = requestResponses.get(requestId);
		if (list == null) {
			list = new ArrayList<Message>();
			requestResponses.put(requestId, list);
		}
		list.add(response);
	}
}
