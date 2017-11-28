package behaviour;

import java.util.Random;

import building.Building;
import jade.core.behaviours.CyclicBehaviour;

public class GenerateRequestsBehaviour extends CyclicBehaviour {

	private Building building;
	
	public GenerateRequestsBehaviour(Building building) {
		this.building = building;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
	
	public int generateRandomRequests(){
		Random randomGenerator = new Random();
		int n = randomGenerator.nextInt();
		return n;
	}

}
