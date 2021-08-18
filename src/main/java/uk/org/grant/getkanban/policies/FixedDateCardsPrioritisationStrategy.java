package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.FixedDateCard;

import java.util.Comparator;

public class FixedDateCardsPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        if (c1 instanceof FixedDateCard) {
            if (c2 instanceof FixedDateCard) {
                return Integer.compare(c1.getDueDate(), c2.getDueDate());
            } else {
                return -1;
            }
        } else if (c2 instanceof FixedDateCard) {
            return 1;
        } else {
            return 0;
        }
    }
}
