package model;

import java.io.Serializable;

public abstract class Message implements Serializable {
	private int id;
	
	public Message() {}
	
	public Message(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
