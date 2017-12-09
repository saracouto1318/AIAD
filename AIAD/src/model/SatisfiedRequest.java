package model;

/**
 * 
 * Creates a request that satisfies the assumptions
 *
 */
public class SatisfiedRequest extends Message {

	/**
	 * SatisfiedRequest's Constructor
	 */
	public SatisfiedRequest() {
		super();
	}
	
	/**
	 * SatisfiedRequest's Constructor
	 * @param id Request's identifier
	 */
	public SatisfiedRequest(int id) {
		super(id);
	}

}
