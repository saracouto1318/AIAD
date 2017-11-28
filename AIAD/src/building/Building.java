package building;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.List;
import java.util.Map.Entry;

import behaviour.*;

public class Building extends Agent {
	private int bottomFloor;
	private int topFloor;
	private int requestFreq;
	
	private final static int DEFAULT_FREQ = 10;
	
	private final static List<Entry<Integer,Integer>> FLOOR_FREQ = new java.util.ArrayList<>();
	
	@Override
	protected void setup() {
		Object[] args = getArguments();
		bottomFloor = (int) args[0];
		topFloor = (int) args[1];
		requestFreq = (int) args[2];
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("Agent building");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		//Create behaviour
		GenerateRequestsBehaviour nb = new GenerateRequestsBehaviour(this);
		this.addBehaviour(nb);
	}

	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
	
	public int getBottomFloor() {
		return bottomFloor;
	}
	
	public int getTopFloor() {
		return topFloor;
	}
}
