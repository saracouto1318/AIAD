package behaviour;

import jade.core.behaviours.Behaviour;

public class NegociateBehaviour extends TakeActionBehaviour {

	private Behaviour hello_behaviour;
	private String id;
	
	public NegociateBehaviour(Behaviour behaviour, String id) {
		this.hello_behaviour = behaviour;
		this.id = id;
	}
	
	@Override
	public void action() {
		System.out.println("I'm \'" + id + "\'");
	}

	@Override
	public boolean done() {
		return hello_behaviour.done();
	}

}
