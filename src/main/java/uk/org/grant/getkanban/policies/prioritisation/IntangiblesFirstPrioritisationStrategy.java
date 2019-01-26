package uk.org.grant.getkanban.policies.prioritisation;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.IntangibleCard;

import java.util.Comparator;

public class IntangiblesFirstPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        if (c1 instanceof IntangibleCard && c2 instanceof IntangibleCard) {
            return 0;
        } else if (c1 instanceof IntangibleCard) {
            return -1;
        } else if (c2 instanceof IntangibleCard) {
            return 1;
        } else {
            return 0;
        }
    }
}
