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

public class FastInitiate {
	private static Profile p;
	private static ContainerController container;
	private String[] agentsNames;
	
	
	public static void main(String[] args) {
		try {
			int[] capacities = {500};
			new FastInitiate(30, 1, capacities);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FastInitiate(int nFloors, int nElevators, int[] elevatorCapacities) throws Exception {
		agentsNames = new String[nElevators + 1];
		
		if(elevatorCapacities.length != nElevators)
			throw new Exception("Invalid length");
		if(!initAgents(nFloors, nElevators, elevatorCapacities))
			throw new Exception("Error initializing agents");
		if(!startAgents())
			throw new Exception("Error starting agents");
	}
		
	private boolean initAgents(int nFloors, int nElevators, int[] elevatorCapacities) {
		p = new ProfileImpl(true);
		container = jade.core.Runtime.instance().createMainContainer(p);
		try {
			String name;
			for(int i = 0; i < nElevators; i++) {
				name = "elev" + i;
				container.createNewAgent(name, Elevator.class.getName(), new Object[] {nFloors - 1, elevatorCapacities[i], false});
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
