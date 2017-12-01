package elevator;

import java.util.LinkedList;
import java.util.TreeSet;

import behaviour.*;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import request.ReceiveRequest;
import request.Request;
import request.TakeRequest;

public class Elevator extends Agent {
	/*
	 * Questões:
	 * 	É suposto termos uma forma automática de criar todos os elevadores e edifico dado um argumento?
	 * 	Como é para fazer a interface? Swing?
	 * 	Os elevadores podem e devem comunicar entre si certo?
	 * 	O edíficio servirá apenas para decidir qual o melhor?
	 * 	Tendo a inteface é suposto os elevadores moverem-se a um passo mais lento?
	 */
	public static final int MAX_FLOOR = 30;
	public static final int MIN_FLOOR = -5;
	
	/**
	 * Maximum capacity for the elevator
	 */
	public final int ELEVATOR_CAPACITY;
	/**
	 * Elevator capacity which does not allow any more passengers
	 * Meaning it'll not attend receive requests
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
		
	public Elevator() {
		ELEVATOR_CAPACITY = 500;
		ELEVATOR_WARNING_CAPACITY = 400;
		stopFloors = new TreeSet<>();
	}	
	
	public int getPassengersWeight() {
		int passengersWeight = 0;
		for(Request r : stopFloors)
			if(r.getClass().isAssignableFrom(TakeRequest.class))
				passengersWeight += ((TakeRequest)r).getWeight();
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
	 * 
	 */
	public boolean addPassenger(int floor, int weight) {
		if(getPassengersWeight() + weight >= ELEVATOR_CAPACITY)
			return false;
		stopFloors.add(new TakeRequest(floor,weight));
		return true;
	}
	
	/**
	 * 	
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
	 * 
	 * @param floor
	 * @return
	 */
	private int move(int floor) {
		if(direction == ElevatorDirection.DOWN) {
			floor--;
			System.out.println("Move down");
		} else if(direction == ElevatorDirection.UP) {
			floor++;
			System.out.println("Move up");
		}
		return floor;
	}
	
	/**
	 * 
	 */
	public void move() {
		cFloor = move(cFloor);
	}
	
	
	public boolean isAbove(int floor) {
		if(this.stopFloors.isEmpty())
			return false;
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getLast().getFloor() <= floor;
	}
	
	public boolean isBelow(int floor) {
		if(this.stopFloors.isEmpty())
			return false;
		LinkedList<Request> list = new LinkedList<>(this.stopFloors);
		return list.getFirst().getFloor() >= floor;
	}
	
	public boolean isLastDirection(int floor) {
		return this.stopFloors.isEmpty() ||
				(this.direction == ElevatorDirection.UP && 
					isAbove(cFloor)) ||
				(this.direction == ElevatorDirection.DOWN && 
					isBelow(cFloor));
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
		CommunicationBehaviour cb = new CommunicationBehaviour(this);
		this.addBehaviour(cb);
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
