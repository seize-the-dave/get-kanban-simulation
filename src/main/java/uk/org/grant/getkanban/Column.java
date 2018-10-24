package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.Collection;
import java.util.List;

public interface Column extends Pullable, Visitable<Day> {
    void addCard(Card card);
    Collection<Card> getCards();
    void allocateDice(ActivityDice... dice);
    List<ActivityDice> getAllocatedDice();

    public enum Type {
        DEVELOPMENT, READY_TO_DEPLOY, ANALYSIS
    }
}
