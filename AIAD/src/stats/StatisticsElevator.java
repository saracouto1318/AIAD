package stats;

public class StatisticsElevator extends StatisticsInfo {
	private int id;
    private String name;
    private double totalOccupation;
    private double totalUsage;
    private int updateCounter;

    public StatisticsElevator(int id, String name) {
        super(StatisticsType.ELEVATOR);
        this.id = id;
        this.name = name;
        totalOccupation = 0;
        totalUsage = 0;
        updateCounter = 0;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOccupation() {
        return totalOccupation * 100 / (double)updateCounter;
    }

    public double getUsage() {
        return totalUsage * 100 / (double)updateCounter;
    }

    public void updateValues(double occupation, double usage) {
    	totalOccupation += occupation;
    	totalUsage += usage;
    	updateCounter++;
    }
}
