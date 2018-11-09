package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.*;

public class NullColumn extends AbstractColumn {
    @Override
    public void addCard(StandardCard card) {
        // Do nothing
    }

    @Override
    public Queue<StandardCard> getCards() {
        return new PriorityQueue<>();
    }

    @Override
    public Optional<StandardCard> pull(Context context) {
        return Optional.empty();
    }

    @Override
    public void doTheWork(Context context) {
        // No Op
    }
}
