package uk.org.grant.getkanban.policies.prioritisation;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.Comparator;

public class SizePrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c1.getRemainingWork() - c2.getRemainingWork();
    }
}
