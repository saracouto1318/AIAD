package behaviour;

import java.util.Random;

import building.Building;
import elevator.ElevatorDirection;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import model.NewRequest;

/**
 * 
 * This class creates a behaviour that generates requests
 *
 */
public class GenerateRequestsBehaviour extends TickerBehaviour {
	/**
	 * Action time of the behaviour
	 */
	private static final int ACTION_TIME = 500;

	/**
	 * Building that will be used
	 */
	private Building building;

	/**
	 * GenerateRequestsBehaviour's constructor
	 * 
	 * @param building
	 *            Building that will be used
	 */
	public GenerateRequestsBehaviour(Building building) {
		super(building, ACTION_TIME);
		this.building = building;
	}

	/**
	 * This method is invoked periodically with the period defined in the
	 * constructor Subclasses are expected to define this method specifying the
	 * action that must be performed at every tick
	 */
	@Override
	public void onTick() {
		// System.out.println("ACTION GENERATE");
		generateRandomRequests();
	}

	/**
	 * This function generates random requests
	 * 
	 * @return The number of random requests
	 */
	public int generateRandomRequests() {
		Random randomGenerator = new Random();
		int bf = building.getBottomFloor();
		int tf = building.getTopFloor();
		int n = 0;
		for (int i = bf; i <= tf; i++) {
			if (randomGenerator.nextInt(building.getRequestFreqOfFloor(i)) == 0) {
				int floor = i;
				int d;
				ElevatorDirection direction;
				if (i == building.getBottomFloor()) {
					direction = ElevatorDirection.UP;
				} else if (i == building.getTopFloor()) {
					direction = ElevatorDirection.DOWN;
				} else {
					d = randomGenerator.nextInt(1);
					direction = ElevatorDirection.values()[d];
				}

				if (this.building.newRequest(floor, direction)){
					n++;
				}
			}
		}
		return n;
	}
}
