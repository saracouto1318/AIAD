package elevators;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Elevator extends Agent {
	private final int ELEVATOR_CAPACITY;
		
	public Elevator(int capacity) {
		ELEVATOR_CAPACITY = capacity;
	}
	
	public int getElevatorCapacity() {
		return ELEVATOR_CAPACITY;
	}
	
	@Override
	protected void setup() {
		String type = "No Communication";
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(getName());
		sd.setType("Agent " + type);
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
		
		//Create behavior
			
	}

	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch(FIPAException e) {
			e.printStackTrace();
		}
	}
}
