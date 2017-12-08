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

public class Building extends Agent {
	private int bottomFloor;
	private int topFloor;
	private int numberOfFloors;
	private int requestFreq;
	private List<AID> elevators;
	
	private final static int DEFAULT_FREQ = 1;

	private final static Map<Integer, Integer> FLOOR_FREQ = new HashMap<Integer, Integer>();
	
	private Map<Integer, List<Message>> requestResponses = new HashMap<Integer, List<Message>>();

	static {
		FLOOR_FREQ.put(0, 10);
	}
	
	@Override
	protected void setup() {
		Object[] args = getArguments();
		try {
			bottomFloor = 0; //Integer.parseInt(args[0].toString());
			topFloor = Integer.parseInt(args[0].toString());
			requestFreq = Integer.parseInt(args[1].toString());
		} catch(ArrayIndexOutOfBoundsException exc) {
			throw(exc);
		}
		numberOfFloors = topFloor - bottomFloor + 1;
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
		//CommunicationBehaviour comBehaviour = new BuildingCommunicationBehaviour(this);
		//this.addBehaviour(comBehaviour);
		//sendMessage(new NewRequest(10, ElevatorDirection.DOWN));
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
		Integer res = FLOOR_FREQ.get(floor);
		return res == null ? DEFAULT_FREQ : res;
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
	public void sendMessage(Message message) {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		for(AID aid : elevators)
			msg.addReceiver(aid);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
		// We want to receive a reply in 100 milliseconds
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 100));
		try {
			msg.setContentObject(message);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.addBehaviour(new BuildingInitiator(this, msg, message, elevators.size()));
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
			requestResponses.put(requestId,list);
		}
		list.add(response);
	}
}
