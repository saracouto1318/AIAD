package elevator;

public enum ElevatorDirection {
	DOWN, UP, NO_DIRECTION;
	
	public static ElevatorDirection changeDirection(ElevatorDirection direction) {
		return direction == ElevatorDirection.DOWN ? 
				ElevatorDirection.UP : 
				ElevatorDirection.DOWN;
	}
}
