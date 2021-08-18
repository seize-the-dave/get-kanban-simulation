package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.DayStore;
import uk.org.grant.getkanban.card.Card;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class WeightedShortestJobFirstPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card c1, Card c2) {
        BigDecimal c1Cd3 = getCD3(c1);
        BigDecimal c2Cd3 = getCD3(c2);

        return c2Cd3.compareTo(c1Cd3);
    }

    private BigDecimal getCD3(Card c) {
        return new BigDecimal(c.getCostOfDelay(DayStore.getDay())).divide(new BigDecimal(Math.max(c.getRemainingWork(), 1)), 3, RoundingMode.HALF_UP);
    }
}
