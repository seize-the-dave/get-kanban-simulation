package uk.org.grant.getkanban.card;

public class Blocker {
    private int work = 7;

    public int getRemainingWork() {
        return work;
    }

    public void doWork(int work) {
        this.work -= work;
    }
}
