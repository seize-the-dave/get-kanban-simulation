package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.*;

public class NullColumn extends AbstractColumn {
    @Override
    public void addCard(Card card) {
        // Do nothing
    }

    @Override
    public Queue<Card> getCards() {
        return new PriorityQueue<>();
    }

    @Override
    public Optional<Card> pull(Context context) {
        return Optional.empty();
    }

    @Override
    public void doTheWork(Context context) {
        // No Op
    }
}
