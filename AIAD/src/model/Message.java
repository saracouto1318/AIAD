package model;

import java.io.Serializable;

/**
 * 
 * Creates the request's message
 *
 */
public abstract class Message implements Serializable {
	/**
	 * Message's identifier
	 */
	private int id;
	
	/**
	 * Message's constructor
	 */
	public Message() {}
	
	/**
	 * Message's constructor
	 * @param id Message's identifier
	 */
	public Message(int id) {
		this.id = id;
	}

	/**
	 * Gets the message's identifier
	 * @return The message's identifier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the message's identifier
	 * @param id The message's new identifier
	 */
	public void setId(int id) {
		this.id = id;
	}
}
