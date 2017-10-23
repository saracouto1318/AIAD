package elevators;

import java.util.LinkedHashSet;
import java.util.Set;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Elevator extends Agent {
	/**
	 * Number of people the elevator can take at a time
	 */
	private final int ELEVATOR_CAPACITY;
	
	/**
	 * Number of the floors that are waiting for an elevator to be available
	 */
	private Set<Integer> toAttend;
	/**
	 * Number of the floors to where the elevator's passengers want to go
	 */
	private Set<Integer> stopFloors;
	/**
	 * Elevator's current floor
	 */
	private int cFloor;
		
	public Elevator(int capacity) {
		ELEVATOR_CAPACITY = capacity;
		toAttend = new LinkedHashSet<>();
		stopFloors = new LinkedHashSet<>();
	}
	
	public int getElevatorCapacity() {
		return ELEVATOR_CAPACITY;
	}
	
	public Set<Integer> getToAttend() {
		return toAttend;
	}
	
	public Set<Integer> getStopFloors() {
		return stopFloors;
	}
	
	public int getCFloor() {
		return cFloor;
	}
	
	public void setCFloor(int floor) {
		cFloor = floor;
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
		
		//Create behaviour
			
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
