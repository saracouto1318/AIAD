package behaviour;

import jade.core.behaviours.Behaviour;

public class CommunicationBehaviour extends Behaviour {
	
	private static final int MAX_PRINTS = 200;
	private int number_prints = 0;

	/**
	 * Reads all the messages received and handles them
	 */
	@Override
	public void action() {
		System.out.println("Hello World");
		number_prints++;

	}

	/**
	 * Returns false
	 */
	@Override
	public boolean done() {
		return number_prints >= MAX_PRINTS;
	}

}
