package gui;

import building.Building;
import elevator.Elevator;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class JadeBoot {
	private static Profile p;
	private static ContainerController container;

	public JadeBoot(int nFloors, int nElevators, Integer[] elevatorCapacities) throws Exception {
		if(elevatorCapacities.length != nElevators)
			throw new Exception("Invalid length");
		initAgents(nFloors, nElevators, elevatorCapacities);
	}
	
	private boolean initAgents(int nFloors, int nElevators, Integer[] elevatorCapacities) {
		p = new ProfileImpl(true);
		container = jade.core.Runtime.instance().createMainContainer(p);
		
		try {
			for(int i = 0; i < nElevators; i++)
				container.createNewAgent("elev" + i, Elevator.class.getName(), new Object[] {nFloors, elevatorCapacities[i]});			
			container.createNewAgent("building", Building.class.getName(), new Object[] {nFloors, 2000000});
		} catch(StaleProxyException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
