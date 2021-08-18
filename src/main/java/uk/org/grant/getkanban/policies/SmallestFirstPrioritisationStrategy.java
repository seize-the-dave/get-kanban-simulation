package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;

public class SmallestFirstPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return Integer.compare(c1.getRemainingWork(), c2.getRemainingWork());
    }
}
