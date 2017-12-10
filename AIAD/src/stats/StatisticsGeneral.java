package stats;

public class StatisticsGeneral extends StatisticsInfo {
    private int maxWait;
    private int minWait;

    public StatisticsGeneral(int maxWait, int minWait) {
        super(StatisticsType.GENERAL);
        this.maxWait = maxWait;
        this.minWait = minWait;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMinWait() {
        return minWait;
    }

    public void setMinWait(int minWait) {
        this.minWait = minWait;
    }
}
