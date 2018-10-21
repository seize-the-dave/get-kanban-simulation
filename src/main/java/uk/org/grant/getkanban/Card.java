package uk.org.grant.getkanban;

public class Card {
    private final Size size;
    private final SubscriberProfile profile;
    private int start;
    private int finish;
    
    public Card(Size size, SubscriberProfile profile) {
        this.size = size;
        this.profile = profile;
    }

    public Size getSize() {
        return size;
    }

    public int getSubscribers() {
        return profile.getSubscribers(getCycleTime());
    }

    public int getCycleTime() {
        checkIsFinished();
        return finish - start;
    }

    private void checkIsFinished() {
        if (start == 0 || finish == 0) {
            throw new IllegalStateException();
        }
    }

    public void setStartDay(int start) {
        this.start = start;
    }

    public void setFinishDay(int finish) {
        if (this.start == 0) {
            throw new IllegalStateException();
        }
        if (finish < this.start) {
            throw new IllegalStateException();
        }
        this.finish = finish;
    }

    public enum Size {
        SMALL
    }
}
