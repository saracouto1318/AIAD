package model;

import java.io.Serializable;

public class AnswerBuilding  implements Serializable {
	private int id;
	private int availability;
	
	public AnswerBuilding() {}
	
	public AnswerBuilding(int id, int availability) {
		this.id = id;
		this.availability = availability;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAvailability() {
		return availability;
	}

	public void setAvailability(int availability) {
		this.availability = availability;
	}
}
