package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.State;

public interface Card {
    String getName();
    StandardCard.Size getSize();
    int getRemainingWork(State state);
    void doWork(State state, int effort);
    int getRemainingWork();
    void onSelected(Context context);
    void onDeployed(Context context);
    int getDaySelected();
    int getDayDeployed();
    int getSubscribers();
    int getCycleTime();

    public enum Size {
        VERY_HIGH, HIGH, MEDIUM, LOW, NONE
    }
}
