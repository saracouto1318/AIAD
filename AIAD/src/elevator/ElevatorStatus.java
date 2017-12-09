package elevator;

/**
 * Enumerates the elevator's different status
 */
public enum ElevatorStatus {
	STOPPED {
		public String toString() {
			return "STOPPED";
		}		
	},
	MOVING {
		public String toString() {
			return "MOVING";
		}		
	};
	
	@Override
	public abstract String toString();
}
