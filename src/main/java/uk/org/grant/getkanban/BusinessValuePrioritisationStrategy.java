package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.StandardCard;

import java.util.Comparator;

public class BusinessValuePrioritisationStrategy implements Comparator<StandardCard> {
    @Override
    public int compare(StandardCard c1, StandardCard c2) {
        return c1.getSize().compareTo(c2.getSize());
    }
}
