package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.SubscriberProfile;
import uk.org.grant.getkanban.VariableSubscriberProfile;

import java.util.EnumMap;
import java.util.Map;

public class StandardCard implements Card {
    private final Size size;
    private final SubscriberProfile profile;
    private final Map<State, Integer> work = new EnumMap<>(State.class);
    private int daySelected;
    private int dayDeployed;
    private final String name;

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile) {
        this.name = name;
        this.size = size;
        this.work.put(State.ANALYSIS, analysis);
        this.work.put(State.DEVELOPMENT, development);
        this.work.put(State.TEST, test);
        this.profile = profile;
    }

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int selected) {
        this(name, size, analysis, development, test, profile);

        this.daySelected = selected;
    }

    public StandardCard(String name, Size size, int analysis, int development, int test, SubscriberProfile profile, int selected, int deployed) {
        this(name, size, analysis, development, test, profile, selected);

        this.daySelected = deployed;
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

    @Override
    public void onSelected(Context context) {
        this.daySelected = context.getDay().getOrdinal();
    }

    @Override
    public void onDeployed(Context context) {
        if (this.daySelected == 0) {
            throw new IllegalStateException("Cannot deploy unselected card");
        }
        if (context.getDay().getOrdinal() < this.daySelected) {
            throw new IllegalStateException("Cannot deploy before selection getDay");
        }
        this.dayDeployed = context.getDay().getOrdinal();
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

    @Override
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
        if (o instanceof StandardCard == false) {
            return false;
        }
        return this.name.equals(((StandardCard) o).name);
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
