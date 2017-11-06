package behaviour;

import jade.core.behaviours.Behaviour;

public class CommunicationBehaviour extends Behaviour {
	
	private static final int MAX_PRINTS = 200;
	private int number_prints = 0;

	/**
	 * Prints a message and increments a counter
	 */
	@Override
	public void action() {
		System.out.println("Hello World");
		number_prints++;

	}

	/**
	 * End the behaviour when it prints the maximum number
	 */
	@Override
	public boolean done() {
		return number_prints >= MAX_PRINTS;
	}

}
