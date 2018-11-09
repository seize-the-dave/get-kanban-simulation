package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.Comparator;

public class BusinessValuePrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        return c1.getSize().compareTo(c2.getSize());
    }
}
