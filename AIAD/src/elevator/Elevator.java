package elevator;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

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
	/**
	 * Number of the floors to where the elevator's passengers want to go
	 */
	protected Set<Integer> stopFloors;
	/**
	 * Elevator's current floor
	 */
	protected int cFloor;
	/**
	 * 
	 */
	protected ElevatorDirection direction;

	protected ElevatorStatus status;
		
	public Elevator() {
		ELEVATOR_CAPACITY = 200;
		stopFloors = new TreeSet<>();
	}	
	
	public int getElevatorCapacity() {
		return ELEVATOR_CAPACITY;
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
	 * 	
	 */
	public void changeDirection() {
		if(this.stopFloors.isEmpty())
			this.direction = ElevatorDirection.NO_DIRECTION;
		else
			this.direction = ElevatorDirection.changeDirection(this.direction);
	}
	
	/**
	 * 
	 */
	public void move() {
		if(direction == ElevatorDirection.DOWN) {
			cFloor--;
			System.out.println("Move down");
		} else if(direction == ElevatorDirection.UP) {
			cFloor++;
			System.out.println("Move up");
		}
	}
	
	public boolean isCFloorAbove() {
		LinkedList<Integer> list = new LinkedList<>(this.stopFloors);
		return list.getLast() <= this.cFloor;
	}
	
	public boolean isCFloorBelow() {
		LinkedList<Integer> list = new LinkedList<>(this.stopFloors);
		return list.getFirst() >= this.cFloor;
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
		//CommunicationBehaviour cb = new CommunicationBehaviour();
		//this.addBehaviour(cb);
		TakeActionBehaviour nb = new TakeActionBehaviour (this);
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
