package elevator;
/**
 * Enumerates the elevator's direction
 */
public enum ElevatorDirection {
	/**
	 * Possible directions
	 */
	DOWN, UP, NO_DIRECTION; 
	
	/**
	 * Gets the direction when it changed
	 * @param direction New direction
	 * @param The new direction
	 */
	public static ElevatorDirection changeDirection(ElevatorDirection direction) {
		return direction == ElevatorDirection.DOWN ? 
				ElevatorDirection.UP : 
				ElevatorDirection.DOWN;
	}
}
