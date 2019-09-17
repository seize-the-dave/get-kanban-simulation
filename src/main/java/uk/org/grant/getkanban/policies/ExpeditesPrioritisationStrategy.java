package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.ExpediteCard;
import uk.org.grant.getkanban.card.FixedDateCard;
import uk.org.grant.getkanban.card.IntangibleCard;

import java.util.Comparator;

public class ExpeditesPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        if (c1 instanceof ExpediteCard) {
            if (c2 instanceof ExpediteCard) {
                return c1.getName().compareTo(c2.getName());
            } else {
                return -1;
            }
        } else if (c2 instanceof ExpediteCard) {
            return 1;
        } else {
            return 0;
        }
    }
}
