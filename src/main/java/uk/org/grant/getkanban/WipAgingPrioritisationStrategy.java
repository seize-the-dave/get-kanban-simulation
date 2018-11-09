package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.StandardCard;

import java.util.Comparator;

public class WipAgingPrioritisationStrategy implements Comparator<StandardCard> {
    @Override
    public int compare(StandardCard c1, StandardCard c2) {
        return c1.getDaySelected() - c2.getDaySelected();
    }
}
