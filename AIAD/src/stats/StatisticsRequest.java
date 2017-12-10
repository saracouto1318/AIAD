package stats;

public class StatisticsRequest extends StatisticsInfo {
    private int id;
    private boolean isReceive;
    private String elevatorId;
    private int requestFloor;
    private int elevatorFloor;
    private String requestDirection;
    private String elevatorDirection;
    private long startTime;
    private long finishTime;
    private long diffTime;

    public StatisticsRequest(int id, boolean isReceive, String elevatorId, int requestFloor, int elevatorFloor, String requestDirection, String elevatorDirection, long startTime, long finishTime, long diffTime) {
        super(StatisticsType.REQUEST);
        this.id = id;
        this.isReceive = isReceive;
        this.elevatorId = elevatorId;
        this.requestFloor = requestFloor;
        this.elevatorFloor = elevatorFloor;
        this.requestDirection = requestDirection;
        this.elevatorDirection = elevatorDirection;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.diffTime = diffTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public String getElevatorId() {
        return elevatorId;
    }

    public void setElevatorId(String elevatorId) {
        this.elevatorId = elevatorId;
    }

    public int getRequestFloor() {
        return requestFloor;
    }

    public void setRequestFloor(int requestFloor) {
        this.requestFloor = requestFloor;
    }

    public int getElevatorFloor() {
        return elevatorFloor;
    }

    public void setElevatorFloor(int elevatorFloor) {
        this.elevatorFloor = elevatorFloor;
    }

    public String getRequestDirection() {
        return requestDirection;
    }

    public void setRequestDirection(String requestDirection) {
        this.requestDirection = requestDirection;
    }

    public String getElevatorDirection() {
        return elevatorDirection;
    }

    public void setElevatorDirection(String elevatorDirection) {
        this.elevatorDirection = elevatorDirection;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }

    public long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(long diffTime) {
        this.diffTime = diffTime;
    }
}
