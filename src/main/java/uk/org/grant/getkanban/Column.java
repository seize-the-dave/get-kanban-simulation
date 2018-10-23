package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;

public interface Column extends Pullable {
    void addCard(Card card);
    void allocateDice(ActivityDice... dice);
    List<ActivityDice> getAllocatedDice();

    public enum Type {
        DEVELOPMENT, READY_TO_DEPLOY, ANALYSIS
    }
}
