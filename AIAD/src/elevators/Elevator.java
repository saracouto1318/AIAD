package elevators;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

import behaviour.*;

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
	
	private ElevatorStatus status;
	
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
	/**
	 * 
	 */
	private ElevatorDirection direction;
		
	public Elevator() {
		ELEVATOR_CAPACITY = 200;
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
	
	public ElevatorStatus getStatus() {
		return status;
	}
	
	public void setStatus(ElevatorStatus status) {
		this.status = status;
	}
	
	public ElevatorDirection getDirection() {
		return direction;
	}
	
	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}
	
	
	/**
	 * Getter for the next floor on the set
	 * @return the int representing the floor
	 */
	public Integer getNextFloor() {
		Iterator<Integer> itr;
		if(this.direction == ElevatorDirection.DOWN) {
			LinkedList<Integer> list = new LinkedList<>(this.stopFloors);
			itr = list.descendingIterator();
		} else
			itr = this.stopFloors.iterator();	
		return itr.hasNext() ? itr.next() : null;
	}
	
	/**
	 * Removes a given floor from the set of stop floors
	 * @param floor int representing the floor
	 * @return The next floor on the set
	 */
	public Integer removeStopFloor(int floor) {
		this.stopFloors.remove(floor);
		return getNextFloor();
	}
	
	/**
	 * 	
	 */
	public void changeDirection() {
		if(this.stopFloors.isEmpty()) {
			this.stopFloors.addAll(this.toAttend);
			this.toAttend.clear();
		} else {
			System.out.println("YA DUMB BITCH! GO WHERE YOU NEED TO GOOOOOOOO");
		}
		
		if(this.stopFloors.isEmpty())
			this.direction = ElevatorDirection.NO_DIRECTION;
		else
			this.direction = 
				direction == ElevatorDirection.DOWN ? 
					ElevatorDirection.UP : 
					ElevatorDirection.DOWN;
	}
	
	/**
	 * 
	 */
	public void move() {
		if(direction == ElevatorDirection.DOWN)
			cFloor--;
		else if(direction == ElevatorDirection.UP)
			cFloor++;
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
		CommunicationBehaviour cb = new CommunicationBehaviour();
		this.addBehaviour(cb);
		NegociateBehaviour nb = new NegociateBehaviour(this, cb, this.getName());
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
}
