package gui;

import building.Building;
import elevator.Elevator;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import tests.ElevatorTestPassengers;

/**
 * 
 * Class that allows to initiate the JADE profile, the container and the different agents
 *
 */
public class JadeBoot {
	/**
	 * JADE profile
	 */
	private static Profile p;
	/**
	 * Agents' container
	 */
	private static ContainerController container;
	/**
	 * Array with the agents' name
	 */
	private String[] agentsNames;
	/**
	 * Array with the different "Elevator" Agents
	 */
	private Elevator[] elevatorAgents;
	/**
	 * Initiates the profile, container and its agents
	 * @param nFloors Building's number of floors
	 * @param nElevators Building's number of elevators
	 * @param elevatorCapacities Capacity of each elevator
	 * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch
	 */
	public JadeBoot(int nFloors, int nElevators, Integer[] elevatorCapacities) throws Exception {
		agentsNames = new String[nElevators + 1];
		elevatorAgents = new Elevator[nElevators];
		
		if(elevatorCapacities.length != nElevators)
			throw new Exception("Invalid length");
		if(!initAgents(nFloors, nElevators, elevatorCapacities))
			throw new Exception("Error initializing agents");
		if(!startAgents())
			throw new Exception("Error starting agents");
	}
	
	/**
	 * Adds an "Elevator" agent to the respective array, in a certain index
	 * @param elevator Elevator that will be added to the array
	 * @param i Index where the Elevator will be placed
	 */
	public void addAgent(Elevator elevator, int i) {
		this.elevatorAgents[i] = elevator;
	}
	
	/**
	 * Gets all the "Elevator" Agents
	 * @return The "Elevator" agents
	 */
	public Elevator[] getElevatorAgents() {
		return elevatorAgents;
	}
	
	/**
	 * Verifies if all "Elevator" agents are an instance of Elevator
	 * @return
	 */
	public boolean hasAllInstancesOfElevator() {
		for(int i = 0; i < elevatorAgents.length; i++)
			if(elevatorAgents[i] == null)
				return false;
		return true;
	}
	
	/**
	 * Initiates the different agents
	 * @param nFloors Building's number of floors
	 * @param nElevators Building's number of elevators
	 * @param elevatorCapacities Capacity of each elevator
	 * @return true if it was possible to initiate the different agents; false otherwise
	 */
	private boolean initAgents(int nFloors, int nElevators, Integer[] elevatorCapacities) {
		p = new ProfileImpl(true);
		container = jade.core.Runtime.instance().createMainContainer(p);
		try {
			String name;
			for(int i = 0; i < nElevators; i++) {
				name = "elev" + i;
				container.createNewAgent(name, Elevator.class.getName(), new Object[] {nFloors - 1, elevatorCapacities[i], true, this, i});
				agentsNames[i] = name;
			}
			name = "building"; 
			container.createNewAgent(name, Building.class.getName(), new Object[] {0, nFloors - 1, 40});
			agentsNames[nElevators] = name;
		} catch(StaleProxyException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * This function starts the agents
	 * @return true if it was possible to initiate the agents; false otherwise
	 */
	private boolean startAgents() {
		System.out.println("Start agents");
		try {
			for(int i = 0; i < agentsNames.length; i++) 
				container.getAgent(agentsNames[i]).start();
		} catch(ControllerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This function kills the container stopping all agents currently running
	 */
	public void end() {
		try {
			container.kill();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		
		for(Elevator e : elevatorAgents)
			if(e != null)
				e.finish();
			else
				System.out.println("NULL ELEVATOR");
	}
}
