package building;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import behaviour.*;
import elevator.Elevator;
import model.Message;

public class Building extends Agent {
	private int bottomFloor;
	private int topFloor;
	private int numberOfFloors;
	private int requestFreq;

	private final static int DEFAULT_FREQ = 20;

	private final static Map<Integer, Integer> FLOOR_FREQ = new HashMap<Integer, Integer>();

	static {
		FLOOR_FREQ.put(0, 10);
	}

	@Override
	protected void setup() {
		Object[] args = getArguments();
		bottomFloor = (int) args[0];
		topFloor = (int) args[1];
		requestFreq = (int) args[2];
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

		// Create behaviour
		GenerateRequestsBehaviour nb = new GenerateRequestsBehaviour(this);
		this.addBehaviour(nb);
	}

	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	public int getBottomFloor() {
		return bottomFloor;
	}

	public int getTopFloor() {
		return topFloor;
	}

	public int getNumberOfFloors() {
		return numberOfFloors;
	}

	public int getFreqOfFloor(int floor) {
		return FLOOR_FREQ.get(floor);
	}

	public int sendMessage(Message message) {
		int n = 0;
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		try {
			msg.setContentObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AID[] elevators = Elevator.getAllElevators(this);
		for (int i = 0; i < elevators.length; i++) {
			msg.addReceiver(elevators[i]);
		}
		send(msg);
		return n;
	}
}
