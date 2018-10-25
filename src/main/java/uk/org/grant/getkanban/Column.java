package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.Collection;
import java.util.List;

public interface Column extends Pullable, Visitable<Day> {
    void addCard(Card card);
    Collection<Card> getCards();
    void allocateDice(StateDice... dice);
    List<StateDice> getAllocatedDice();

}
