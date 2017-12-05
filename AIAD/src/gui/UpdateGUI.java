package gui;

import elevator.Elevator;

public class UpdateGUI extends Thread {
	private JadeBoot boot;
	private StartElevators gui;

	public UpdateGUI(StartElevators gui, JadeBoot boot) {
		this.gui = gui;
		this.boot = boot;
	}

	@Override
	public void run() {
		boolean hasAllElevators = false;
		Elevator[] elevators;
		while(true) {
			if(hasAllElevators) {
				//TODO: update labels
			} else if(boot.hasAllInstancesOfElevator()) {
				hasAllElevators = true;
				elevators = boot.getElevatorAgents();
				
				//No need for this
				this.boot = null;
			}
		}
	}

}
