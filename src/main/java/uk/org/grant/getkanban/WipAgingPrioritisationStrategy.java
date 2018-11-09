package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.Comparator;

public class WipAgingPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c1.getDaySelected() - c2.getDaySelected();
    }
}
