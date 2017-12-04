package behaviour;

import java.util.Random;

import building.Building;
import elevator.ElevatorDirection;
import jade.core.behaviours.CyclicBehaviour;
import model.Message;
import model.NewRequest;

public class GenerateRequestsBehaviour extends CyclicBehaviour {

	private Building building;

	public GenerateRequestsBehaviour(Building building) {
		this.building = building;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	public int generateRandomRequests() {
		Random randomGenerator = new Random();
		int bf = building.getBottomFloor();
		int tf = building.getTopFloor();
		int n = 0;
		for (int i = bf; i <= tf; i++) {
			if (randomGenerator.nextInt(building.getFreqOfFloor(i)) == 0) {
				int id = 0;
				int floor = i;
				int d = randomGenerator.nextInt(1);
				ElevatorDirection direction = ElevatorDirection.values()[d];
				Message m = new NewRequest(id, floor, direction);
				this.building.sendMessage(m);
				n++;
			}
		}
		return n;
	}

}
