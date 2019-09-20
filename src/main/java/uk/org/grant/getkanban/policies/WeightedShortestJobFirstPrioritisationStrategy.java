package uk.org.grant.getkanban.policies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.DayStore;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;

public class WeightedShortestJobFirstPrioritisationStrategy implements Comparator<Card> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeightedShortestJobFirstPrioritisationStrategy.class);

    @Override
    public int compare(Card c1, Card c2) {
//        if (c1 instanceof StandardCard && c2 instanceof StandardCard) {
        BigDecimal c1Cd3 = getCD3(c1);
        BigDecimal c2Cd3 = getCD3(c2);

        return c2Cd3.compareTo(c1Cd3);
//        } else {
//            return 0;
//        }
    }

    private BigDecimal getCD3(Card c) {
        BigDecimal cd3 = new BigDecimal(c.getCostOfDelay(DayStore.getDay())).divide(new BigDecimal(Math.max(c.getRemainingWork(), 1)), 3, RoundingMode.HALF_UP);
//        LOGGER.info("CD3 for {} is {}", c, cd3);
        return cd3;
    }
}
