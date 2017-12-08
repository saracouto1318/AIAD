package behaviour;

import java.util.Random;

import building.Building;
import elevator.ElevatorDirection;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import model.NewRequest;

public class GenerateRequestsBehaviour extends TickerBehaviour {
	private static final int ACTION_TIME = 500;

	private Building building;

	public GenerateRequestsBehaviour(Building building) {
		super(building, ACTION_TIME);
		this.building = building;
	}

	@Override
	public void onTick() {
		//System.out.println("ACTION GENERATE");
		generateRandomRequests();
	}

	public int generateRandomRequests() {
		Random randomGenerator = new Random();
		int bf = building.getBottomFloor();
		int tf = building.getTopFloor();
		int n = 0;
		for (int i = bf; i <= tf; i++) {
			if (randomGenerator.nextInt(building.getRequestFreqOfFloor(i)) == 0) {
				int floor = i;
				int d = randomGenerator.nextInt(1);
				ElevatorDirection direction = ElevatorDirection.values()[d];
				this.building.sendMessage(new NewRequest(floor, direction));
				n++;
			}
		}
		return n;
	}
}
