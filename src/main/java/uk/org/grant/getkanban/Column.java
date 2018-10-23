package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;
import java.util.Optional;

public interface Column {
    Optional<Card> pullCard();
    void pullFromUpstream();
    void allocateDice(ActivityDice... dice);
    List<ActivityDice> getAllocatedDice();

    public enum Type {
        DEVELOPMENT, ANALYSIS
    }
}
