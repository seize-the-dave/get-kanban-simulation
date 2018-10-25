package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;

public class DefaultPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        return 0;
    }
}
