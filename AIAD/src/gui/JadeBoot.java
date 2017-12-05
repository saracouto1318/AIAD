package gui;

import building.Building;
import elevator.Elevator;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import tests.ElevatorTestPassengers;

public class JadeBoot {
	private static Profile p;
	private static ContainerController container;
	private String[] agentsNames;
	
	private Elevator[] elevatorAgents;

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
	
	public void addAgent(Elevator elevator, int i) {
		this.elevatorAgents[i] = elevator;
	}
	
	public Elevator[] getElevatorAgents() {
		return elevatorAgents;
	}
	
	public boolean hasAllInstancesOfElevator() {
		for(int i = 0; i < elevatorAgents.length; i++)
			if(elevatorAgents[i] == null)
				return false;
		return true;
	}
	
	private boolean initAgents(int nFloors, int nElevators, Integer[] elevatorCapacities) {
		p = new ProfileImpl(true);
		container = jade.core.Runtime.instance().createMainContainer(p);
		try {
			String name;
			for(int i = 0; i < nElevators; i++) {
				name = "elev" + i;
				container.createNewAgent(name, ElevatorTestPassengers.class.getName(), new Object[] {nFloors, elevatorCapacities[i], true, this, i});
				agentsNames[i] = name;
			}
			name = "building"; 
			container.createNewAgent(name, Building.class.getName(), new Object[] {nFloors, 2000000});
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
