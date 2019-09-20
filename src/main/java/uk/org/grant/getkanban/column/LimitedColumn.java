package uk.org.grant.getkanban.column;

public abstract class LimitedColumn implements Column {
    private int wipLimit;
    private boolean limitsEnabled = true;

    public LimitedColumn(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getLimit() {
        if (limitsEnabled) {
            return wipLimit;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public void setLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public void disableLimits() {
        this.limitsEnabled = false;
    }

    public void enableLimits() {
        this.limitsEnabled = true;
    }
}
