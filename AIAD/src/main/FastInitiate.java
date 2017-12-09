package main;

import javax.swing.SwingUtilities;

import building.Building;
import elevator.Elevator;
import gui.MainPage;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

/**
 * 
 * Class that allows to initiate the JADE profile, the container and the different agents
 *
 */
public class FastInitiate {
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
	 * Main function that allows to initiate the profile, container and its agents
	 * @param args Arguments
	 */
	public static void main(String[] args) {
		try {
			int[] capacities = {500};
			new FastInitiate(30, 1, capacities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initiates the profile, container and its agents
	 * @param nFloors Building's number of floors
	 * @param nElevators Building's number of elevators
	 * @param elevatorCapacities Capacity of each elevator
	 * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch
	 */
	public FastInitiate(int nFloors, int nElevators, int[] elevatorCapacities) throws Exception {
		agentsNames = new String[nElevators + 1];
		
		if(elevatorCapacities.length != nElevators)
			throw new Exception("Invalid length");
		if(!initAgents(nFloors, nElevators, elevatorCapacities))
			throw new Exception("Error initializing agents");
		if(!startAgents())
			throw new Exception("Error starting agents");
	}
	
	/**
	 * Initiates the different agents
	 * @param nFloors Building's number of floors
	 * @param nElevators Building's number of elevators
	 * @param elevatorCapacities Capacity of each elevator
	 * @return true if it was possible to initiate the different agents; false otherwise
	 */
	private boolean initAgents(int nFloors, int nElevators, int[] elevatorCapacities) {
		p = new ProfileImpl(true);
		container = jade.core.Runtime.instance().createMainContainer(p);
		try {
			String name;
			for(int i = 0; i < nElevators; i++) {
				name = "elev" + i;
				//Creates the "Elevator" agent
				container.createNewAgent(name, Elevator.class.getName(), new Object[] {nFloors - 1, elevatorCapacities[i], false});
				agentsNames[i] = name;
			}
			name = "building"; 
			//Create the "Building" agent
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

}
