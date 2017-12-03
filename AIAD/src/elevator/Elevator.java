package elevator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import behaviour.*;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import request.Request;
import request.TakeRequest;

public class Elevator extends Agent {
	public static final int MAX_FLOOR = 30;
	public static final int MIN_FLOOR = -5;

	/**
	 * Maximum capacity for the elevator
	 */
	public final int ELEVATOR_CAPACITY;
	/**
	 * Elevator capacity which does not allow any more passengers Meaning it'll
	 * not attend receive requests
	 */
	public final int ELEVATOR_WARNING_CAPACITY;
	/**
	 * Number of the floors to where the elevator's passengers want to go
	 */
	protected TreeSet<Request> stopFloors;
	/**
	 * Elevator's current floor
	 */
	protected int cFloor;
	/**
	 * 
	 */
	protected ElevatorDirection direction;

	protected ElevatorStatus status;

	public static final AID[] getAllElevators(Agent a) {
		AMSAgentDescription[] agents = null;
		List<AID> agentsAID = new ArrayList<AID>();
		try {
			SearchConstraints c = new SearchConstraints();
			c.setMaxResults(new Long(-1));
			agents = AMSService.search(a, new AMSAgentDescription(), c);
			for (int i = 0; i < agents.length; i++) {
				agentsAID.add(agents[i].getName());
			}
		} catch (Exception e) {

		}
		return (AID[]) agentsAID.toArray();
	}

	public Elevator() {
		ELEVATOR_CAPACITY = 500;
		ELEVATOR_WARNING_CAPACITY = 400;
		stopFloors = new TreeSet<>();
	}

	public int getPassengersWeight() {
		int passengersWeight = 0;
		for (Request r : stopFloors)
			if (r.getClass().isAssignableFrom(TakeRequest.class))
				passengersWeight += ((TakeRequest) r).getWeight();
		return passengersWeight;
	}

	public TreeSet<Request> getStopFloors() {
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
	 * 
	 */
	public boolean addPassenger(int floor, int weight) {
		if (getPassengersWeight() + weight >= ELEVATOR_CAPACITY)
			return false;
		stopFloors.add(new TakeRequest(floor, weight));
		return true;
	}

	/**
	 * 	
	 */
	public void changeDirection() {
		if (this.stopFloors.isEmpty())
			this.direction = ElevatorDirection.NO_DIRECTION;
		else
			this.direction = ElevatorDirection.changeDirection(this.direction);
	}

	/**
	 * 
	 */
	public void move() {
		if (direction == ElevatorDirection.DOWN) {
			cFloor--;
			System.out.println("Move down");
		} else if (direction == ElevatorDirection.UP) {
			cFloor++;
			System.out.println("Move up");
		}
	}

	public boolean isAbove(int floor) {
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getLast().getFloor() <= floor;
	}

	public boolean isBelow(int floor) {
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getFirst().getFloor() >= floor;
	}

	public boolean isLastDirection(int floor) {
		return (direction == ElevatorDirection.UP && isAbove(cFloor))
				|| (direction == ElevatorDirection.DOWN && isBelow(cFloor));
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
		} catch (FIPAException e) {
			e.printStackTrace();
		}

		// Create behaviour
		CommunicationBehaviour cb = new CommunicationBehaviour(this);
		this.addBehaviour(cb);
		TakeActionBehaviour nb = new TakeActionBehaviour(this);
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
}
