package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;

import java.util.EnumMap;
import java.util.Map;

public abstract class AbstractCard implements Card {
    private final String name;
    private final StandardCard.Size size;
    private final Map<State, Integer> work = new EnumMap<>(State.class);
    private int daySelected;
    private int dayDeployed;
    private Blocker blocker;

    public AbstractCard(String name, StandardCard.Size size, int analysis, int development, int test) {
        this.name = name;
        this.size = size;
        this.work.put(State.ANALYSIS, analysis);
        this.work.put(State.DEVELOPMENT, development);
        this.work.put(State.TEST, test);
    }

    public AbstractCard(String name, StandardCard.Size size, int analysis, int development, int test, int daySelected) {
        this(name, size, analysis, development, test);

        this.daySelected = daySelected;
    }

    public AbstractCard(String name, StandardCard.Size size, int analysis, int development, int test, int daySelected, int dayDeployed) {
        this(name, size, analysis, development, test, daySelected);

        this.dayDeployed = dayDeployed;
    }

    @Override
    public int getRemainingWork(State state) {
        return work.get(state);
    }

    @Override
    public void doWork(State state, int effort) {
        int remaining = getRemainingWork(state);
        if (effort > remaining) {
            throw new IllegalArgumentException();
        }
        work.put(state, remaining - effort);
    }

    @Override
    public int getRemainingWork() {
        return getRemainingWork(State.ANALYSIS) + getRemainingWork(State.DEVELOPMENT) + getRemainingWork(State.TEST);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StandardCard.Size getSize() {
        return size;
    }


    public int getDaySelected() {
        return daySelected;
    }

    public int getDayDeployed() {
        return dayDeployed;
    }


    @Override
    public int getCycleTime() {
        if (getDaySelected() == 0 || getDayDeployed() == 0) {
            throw new IllegalStateException();
        }
        return getDayDeployed() - getDaySelected();
    }

    @Override
    public void onSelected(Context context) {
        this.daySelected = context.getDay().getOrdinal();
    }

    @Override
    public void onReadyToDeploy(Context context) {

    }

    @Override
    public void onDeployed(Context context) {
        if (this.daySelected == 0) {
            throw new IllegalStateException("Cannot deploy unselected card " + getName());
        }
        if (context.getDay().getOrdinal() < this.daySelected) {
            throw new IllegalStateException("Cannot deploy before selection getDay");
        }
        this.dayDeployed = context.getDay().getOrdinal();
    }

    @Override
    public int getFineOrPayment() {
        return 0;
    }

    @Override
    public int getDueDate() {
        return -1;
    }

    @Override
    public void setBlocker(Blocker blocker) {
        this.blocker = blocker;
    }

    @Override
    public Blocker getBlocker() {
        return blocker;
    }

    @Override
    public boolean isBlocked() {
        return blocker != null && blocker.getRemainingWork() > 0;
    }

    @Override
    public String toString() {
        String blocked = isBlocked() ? " (BLOCKED)" : "";
        return getName() +
                "[" +
                getRemainingWork(State.ANALYSIS) + "/" +
                getRemainingWork(State.DEVELOPMENT) + "/" +
                getRemainingWork(State.TEST) +
                "]" +
                blocked;
    }
}
