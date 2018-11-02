package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;

import java.util.EnumMap;
import java.util.Map;

public class Card {
    private final Size size;
    private final SubscriberProfile profile;
    private final Map<State, Integer> work = new EnumMap<>(State.class);
    private int daySelected;
    private int dayDeployed;
    private final String name;

    public Card(String name, Size size, int analysis, int development, int test, SubscriberProfile profile) {
        this.name = name;
        this.size = size;
        this.work.put(State.ANALYSIS, analysis);
        this.work.put(State.DEVELOPMENT, development);
        this.work.put(State.TEST, test);
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
        return dayDeployed - daySelected;
    }

    private void checkIsFinished() {
        if (daySelected == 0 || dayDeployed == 0) {
            throw new IllegalStateException();
        }
    }

    public void setDaySelected(int daySelected) {
        this.daySelected = daySelected;
    }

    public void setDayDeployed(int dayDeployed) {
        if (this.daySelected == 0) {
            throw new IllegalStateException("Cannot deploy unselected card");
        }
        if (dayDeployed < this.daySelected) {
            throw new IllegalStateException("Cannot deploy before selection getDay");
        }
        this.dayDeployed = dayDeployed;
    }

    public int getRemainingWork(State state) {
        return work.get(state);
    }

    public void doWork(State state, int effort) {
        int remaining = getRemainingWork(state);
        if (effort > remaining) {
            throw new IllegalArgumentException();
        }
        work.put(state, remaining - effort);
    }

    public int getDaySelected() {
        return daySelected;
    }

    public int getDayDeployed() {
        return dayDeployed;
    }

    public String getName() {
        return this.name;
    }

    public int getRemainingWork() {
        return getRemainingWork(State.ANALYSIS) + getRemainingWork(State.DEVELOPMENT) + getRemainingWork(State.TEST);
    }

    public enum Size {
        VERY_HIGH, HIGH, MEDIUM, LOW, NONE
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card == false) {
            return false;
        }
        return this.name.equals(((Card) o).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name + "[" + getRemainingWork(State.ANALYSIS) + "/" + getRemainingWork(State.DEVELOPMENT) + "/" + getRemainingWork(State.TEST) + "]" ;
    }
}
