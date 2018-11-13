package uk.org.grant.getkanban.card;

public class Blocker {
    private static int work = 7;

    public int getRemainingWork() {
        return work;
    }

    public void doWork(int work) {
        this.work -= work;
    }
}
