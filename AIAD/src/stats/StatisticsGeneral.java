package stats;

public class StatisticsGeneral extends StatisticsInfo {
    private long maxWait;
    private long minWait;

    public StatisticsGeneral(int maxWait, int minWait) {
        super(StatisticsType.GENERAL);
        this.maxWait = maxWait;
        this.minWait = minWait;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getMinWait() {
        return minWait;
    }

    public void setMinWait(long minWait) {
        this.minWait = minWait;
    }
}
