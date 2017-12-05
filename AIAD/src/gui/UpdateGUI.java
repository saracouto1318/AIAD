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
		Elevator[] elevators = null;
		while(!isInterrupted()) {
			if(hasAllElevators) {
				updateElevators(elevators);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println("I can't fall asleep for some strange and sexual reason");
				}
				
			} else if(boot.hasAllInstancesOfElevator()) {
				hasAllElevators = true;
				elevators = boot.getElevatorAgents();
				
				//No need for this
				this.boot = null;
			}
		}
	}
	
	private void updateElevators(Elevator[] elevators) {
		for(int i = 0; i < elevators.length; i++) {
			gui.eraseFloor(elevators[i].getCFloor(), i);
			gui.paintFloor(elevators[i].getCFloor(), i);
		}
	}

}
