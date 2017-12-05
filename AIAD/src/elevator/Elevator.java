package elevator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import behaviour.*;
import contract.ElevatorResponder;
import gui.JadeBoot;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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
	public int maxFloor = 30;
	/**
	 * Minimum possible floor
	 */
	public int minFloor = 0;
	/**
	 * Maximum capacity for the elevator
	 */
	public int ELEVATOR_CAPACITY;
	/**
	 * Elevator capacity which does not allow any more passengers Meaning it'll
	 * not attend receive requests
	 */
	public int ELEVATOR_WARNING_CAPACITY;
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
	 * Sets up the elevator with its behaviour, registering it
	 */
	@Override
	protected void setup() {
		Object[] args = getArguments();
		try {
			minFloor = 0; //Integer.parseInt(args[0].toString());
			maxFloor = Integer.parseInt(args[0].toString());
			ELEVATOR_CAPACITY = Integer.parseInt(args[1].toString());
			ELEVATOR_WARNING_CAPACITY = ELEVATOR_CAPACITY - 50;
			
			//Add instance to jade boot if aplicable
			boolean hasInterface = Boolean.parseBoolean(args[2].toString());
			
			if(hasInterface) {
				JadeBoot boot = (JadeBoot)args[3];
				int elevatorIndex = Integer.parseInt(args[4].toString());
				boot.addAgent(this, elevatorIndex);
			}
		} catch(ArrayIndexOutOfBoundsException exc) {
			throw(exc);
		}
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

		// Create behaviours
		CommunicationBehaviour cb = new ElevatorCommunicationBehaviour(this);
		this.addBehaviour(cb);
		TakeActionBehaviour nb = new TakeActionBehaviour(this);
		this.addBehaviour(nb);
		//Contract net behaviour
		MessageTemplate template = MessageTemplate.and(
		  		MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
		  		MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		ElevatorResponder er = new ElevatorResponder(this, template);
		this.addBehaviour(er);
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
		int floor = this.cFloor;
		if(isLastDirection(this.cFloor))
			return floor;
		boolean isDown = this.direction == ElevatorDirection.DOWN;
		Iterator<Request> iter;
		if(isDown)
			iter = stopFloors.descendingIterator();
		else
			iter = stopFloors.iterator();
		
		while(iter.hasNext()) {
			Request r = iter.next();
			if(r instanceof ReceiveRequest && ((ReceiveRequest)r).getDirection() == this.getDirection())
				return isDown ? minFloor : maxFloor;
			else if(r instanceof TakeRequest) {
				floor = r.getFloor();
				if(isLastDirection(floor))
					return floor;
			}
		}

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
		System.out.println("LAST FLOOR " + floor + " -> " + (this.stopFloors.last().getFloor() <= floor));
		return this.stopFloors.last().getFloor() <= floor;
	}
	
	/**
	 * Verifies if if the set of floors is below of the elevator's current floor
	 * @param floor Elevator's current floor
	 * @return true if the set os floors is below of the elevator's current floor; false otherwise
	 */
	public boolean isBelow(int floor) {
		if(this.stopFloors.isEmpty())
			return false;
		System.out.println("FIRST FLOOR " + floor + " -> " + (this.stopFloors.first().getFloor() <= floor));
		return this.stopFloors.first().getFloor() >= floor;
	}
	
	/**
	 * Verifies if if elevator's direction is the last direction possible
	 * @param floor Elevator's current floor
	 * @return true if elevator's direction is the last direction possible; false otherwise
	 */
	public boolean isLastDirection(int floor) {
		return this.stopFloors.isEmpty() ||
				(this.direction == ElevatorDirection.UP && 
					isAbove(floor)) ||
				(this.direction == ElevatorDirection.DOWN && 
					isBelow(floor));
	}
}
