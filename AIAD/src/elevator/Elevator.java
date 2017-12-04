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
import request.ReceiveRequest;
import request.Request;
import request.TakeRequest;

/**
 * This class creates an elevator which is a JADE agent
 */
public class Elevator extends Agent {
	/*
	 * Questões:
	 * 	É suposto termos uma forma automática de criar todos os elevadores e edifico dado um argumento?
	 * 	Como é para fazer a interface? Swing?
	 * 	Os elevadores podem e devem comunicar entre si certo?
	 * 	O edíficio servirá apenas para decidir qual o melhor?
	 * 	Tendo a inteface é suposto os elevadores moverem-se a um passo mais lento?
	 */
	
	/**
	 * Maximum possible floor
	 */
	public static final int MAX_FLOOR = 30;
	/**
	 * Minimum possible floor
	 */
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
	 * Enumerates the elevator's direction
	 */
	protected ElevatorDirection direction;

	/**
	 * Enumerates the elevator's different status
	 */
	protected ElevatorStatus status;

	public static final List<AID> getAllElevators(Agent a) {
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
		return agentsAID;
	}
	
	/**
	 * Creates an elevator with a max capacity, a warning capacity and a set of floors where the elevator must stop
	 */
	public Elevator() {
		ELEVATOR_CAPACITY = 500;
		ELEVATOR_WARNING_CAPACITY = 400;
		stopFloors = new TreeSet<>();
	}
	
	/**
	 * Calculates the total weight of the passengers
	 * @return The total weight of the passengers
	 */
	public int getPassengersWeight() {
		int passengersWeight = 0;
		for (Request r : stopFloors)
			if (r.getClass().isAssignableFrom(TakeRequest.class))
				passengersWeight += ((TakeRequest) r).getWeight();
		return passengersWeight;
	}

	/**
	 * Gets the set of floors where the elevator must stop
	 * @return The set of floors where the elevator must stop
	 */
	public TreeSet<Request> getStopFloors() {
		return stopFloors;
	}
	
	/**
	 * Gets the elevator's current floor
	 * @return The elevator's current floor
	 */
	public int getCFloor() {
		return cFloor;
	}
	
	/**
	 * Sets the elevator's current floor
	 * @param floor New current floor
	 */
	public void setCFloor(int floor) {
		cFloor = floor;
	}
	
	/**
	 * Gets the current status of the elevator
	 * @return The current status of the elevator
	 */
	public ElevatorStatus getStatus() {
		return status;
	}
	
	/**
	 * Sets the current status of the elevator
	 * @param status New current status of the elevator
	 */
	public void setStatus(ElevatorStatus status) {
		this.status = status;
	}
	
	/**
	 * Gets the current direction of the elevator
	 * @return The current direction of the elevator
	 */
	public ElevatorDirection getDirection() {
		return direction;
	}
	
	/**
	 * Sets the current direction of the elevator
	 * @return The current direction of the elevator
	 */
	public void setDirection(ElevatorDirection direction) {
		this.direction = direction;
	}

	/**
	 * Gets the last floor possible in a certain direction
	 * @return The last floor possible in a certain direction
	 */
	public int getLastFloorInDirection() {
		//TODO: Melhorar este algoritmo #naoconsigopensar
		int floor = this.cFloor;
		if(isLastDirection(this.cFloor))
			return floor;
		do {
			for(Request r : this.stopFloors) {
				if(r.getFloor() == floor && ReceiveRequest.class.isInstance(r.getClass())) 
					if(this.direction == ElevatorDirection.DOWN)
						return floor = MIN_FLOOR;
					else
						return floor = MAX_FLOOR;
				//Since it's ordered
				else if(r.getFloor() > floor)
					break;
			}
			floor = move(floor);
		} while(!isLastDirection(floor));
		return floor;
	}
	
	/**
	 * This function adds a passenger to the elevator
	 * @param floor Passenger's floor
	 * @param weight Passenger's weight
	 * @return true if it's possible to add the new passenger to the elevator; false otherwise
	 */
	public boolean addPassenger(int floor, int weight) {
		if (getPassengersWeight() + weight >= ELEVATOR_CAPACITY)
			return false;
		stopFloors.add(new TakeRequest(floor, weight));
		return true;
	}

	/**
	 * This function changes the elevator's direction
	 * @return true if it's possible to change the elevator's direction; false otherwise
	 */
	public boolean changeDirection() {
		if(this.stopFloors.isEmpty())
			this.direction = ElevatorDirection.NO_DIRECTION;
		else if(!isLastDirection(cFloor))
			return false;
		else
			this.direction = ElevatorDirection.changeDirection(this.direction);
		return true;
	}

	/**
	 * This function creates the elevator's movements
	 * @param floor Elevator's current floor
	 * @return The elevator's current floor
	 */
	private int move(int floor) {
		if(direction == ElevatorDirection.DOWN) {
			floor--;
		} else if(direction == ElevatorDirection.UP) {
			floor++;
		}
		return floor;
	}
	
	/**
	 * Moves the elevator
	 */
	public void move() {
		cFloor = move(cFloor);
	}
	
	/**
	 * Verifies if if the set of floors is above of the elevator's current floor
	 * @param floor Elevator's current floor
	 * @return true if the set os floors is above of the elevator's current floor; false otherwise
	 */
	public boolean isAbove(int floor) {
		if(this.stopFloors.isEmpty())
			return false;
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getLast().getFloor() <= floor;
	}
	
	/**
	 * Verifies if if the set of floors is below of the elevator's current floor
	 * @param floor Elevator's current floor
	 * @return true if the set os floors is below of the elevator's current floor; false otherwise
	 */
	public boolean isBelow(int floor) {
		if(this.stopFloors.isEmpty())
			return false;
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getFirst().getFloor() >= floor;
	}
	
	/**
	 * Verifies if if elevator's direction is the last direction possible
	 * @param floor Elevator's current floor
	 * @return true if elevator's direction is the last direction possible; false otherwise
	 */
	public boolean isLastDirection(int floor) {
		return this.stopFloors.isEmpty() ||
				(this.direction == ElevatorDirection.UP && 
					isAbove(cFloor)) ||
				(this.direction == ElevatorDirection.DOWN && 
					isBelow(cFloor));
	}
	
	
	/**
	 * Sets up the elevator with its behaviour, registering it
	 */
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
		CommunicationBehaviour cb = new ElevatorCommunicationBehaviour(this);
		this.addBehaviour(cb);
		TakeActionBehaviour nb = new TakeActionBehaviour(this);
		this.addBehaviour(nb);
	}

	/**
	 * Removes the register of DF
	 */
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
}
