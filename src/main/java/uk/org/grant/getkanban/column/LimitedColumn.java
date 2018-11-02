package uk.org.grant.getkanban.column;

public abstract class LimitedColumn implements Column {
    private int wipLimit;

    public LimitedColumn(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public int getLimit() {
        return wipLimit;
    }

    public void setLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }
}
