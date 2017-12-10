package stats;

public abstract class StatisticsInfo {
    public enum StatisticsType {REQUEST, ELEVATOR, GENERAL};

    private StatisticsType type;

    public StatisticsInfo(StatisticsType type) {
        this.type = type;
    }

    public StatisticsType getType() {
        return type;
    }

    public void setType(StatisticsType type) {
        this.type = type;
    }
}
