package uk.org.grant.getkanban;

public class Card {
    private final Size size;
    private int start;
    private int finish;


    public Card(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void getSubscribers() {
        throw new IllegalStateException();
    }

    public int getCycleTime() {
        if (start == 0 || finish == 0) {
            throw new IllegalStateException();
        }
        return finish - start;
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
