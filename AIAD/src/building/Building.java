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
import model.Message;

public class Building extends Agent {
	private int bottomFloor;
	private int topFloor;
	private int numberOfFloors;
	private int requestFreq;

	private final static int DEFAULT_FREQ = 20;

	private final static Map<Integer, Integer> FLOOR_FREQ = new HashMap<Integer, Integer>();

	private Map<Integer, List<Message>> requestResponses;

	private List<Message> requests;

	static {
		FLOOR_FREQ.put(0, 10);
	}

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

		// Create behaviour
		GenerateRequestsBehaviour reqBehaviour = new GenerateRequestsBehaviour(this);
		CommunicationBehaviour comBehaviour = new BuildingCommunicationBehaviour(this);
		this.addBehaviour(reqBehaviour);
		this.addBehaviour(comBehaviour);
	}

	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getBottomFloor() {
		return bottomFloor;
	}

	/**
	 * 
	 * @return
	 */
	public int getTopFloor() {
		return topFloor;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	/**
	 * 
	 * @param floor
	 * @return
	 */
	public int getFreqOfFloor(int floor) {
		return FLOOR_FREQ.get(floor) == null ? DEFAULT_FREQ : FLOOR_FREQ.get(floor);
	}

	/**
	 * 
	 * @return
	 */
	public int getRequestFreq() {
		return requestFreq;
	}

	/**
	 * 
	 * @param floor
	 * @return
	 */
	public int getRequestFreqOfFloor(int floor) {
		return getFreqOfFloor(floor) * getRequestFreq();
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	public int sendMessage(Message message) {
		int n = 0;
		/*
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		try {
			msg.setContentObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<AID> elevators = Elevator.getAllElevators(this);
		for (AID elev : elevators)
			msg.addReceiver(elev);
		send(msg);
		return n;
		*/
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		List<AID> elevators = Elevator.getAllElevators(this);
		for (AID elev : elevators)
			msg.addReceiver(elev);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		// We want to receive a reply in 100 milliseconds
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 100));
		try {
			msg.setContentObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addBehaviour(new BuildingInitiator(this, msg));
		
		return n;
	}

	/**
	 * 
	 * @param requestId
	 * @param response
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
