package gui;

import elevator.Elevator;

/**
 * 
 * This class allows to update the GUI
 *
 */
public class UpdateGUI extends Thread {
	/**
	 * Class that initiates the JADE
	 */
	private JadeBoot boot;
	/**
	 * GUI that initiates the elevators
	 */
	private StartElevators gui;

	/**
	 * UpdateGUI's constructor
	 * @param gui GUI that initiate the elevators
	 * @param boot Class that initiates the JADE
	 */
	public UpdateGUI(StartElevators gui, JadeBoot boot) {
		this.gui = gui;
		this.boot = boot;
	}

	/**
	 * If this thread was constructed using a separate Runnable run object, then that Runnable object's run method is called; otherwise, this method does nothing and returns. 
Subclasses of Thread should override this method
	 */
	@Override
	public void run() {
		boolean hasAllElevators = false;
		Elevator[] elevators = null;
		while(!isInterrupted()) {
			if(hasAllElevators) {
				updateElevators(elevators);

				try {
					Thread.sleep(100);
				} catch (InterruptedException ignore) {}
				
			} else if(boot.hasAllInstancesOfElevator()) {
				hasAllElevators = true;
				elevators = boot.getElevatorAgents();
				
				//No need for this
				this.boot = null;
			}
		}
	}
	
	/**
	 * Updates the elevators with the different requests
	 * @param elevators Building's elevators
	 */
	private void updateElevators(Elevator[] elevators) {
		for(int i = 0; i < elevators.length; i++) {
			gui.eraseFloor(elevators[i].getCFloor(), i);
			gui.paintFloor(elevators[i].getCFloor(), i);
		}
	}

}
