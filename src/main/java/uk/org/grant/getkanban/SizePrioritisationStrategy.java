package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;

public class SizePrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c1.getRemainingWork() - c2.getRemainingWork();
    }
}
