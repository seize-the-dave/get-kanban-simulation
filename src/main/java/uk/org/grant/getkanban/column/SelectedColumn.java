package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.dice.DiceGroup;

import java.util.*;

public class SelectedColumn extends LimitedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectedColumn.class);
    private final Queue<Card> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private Column upstream;

    public SelectedColumn(int limit, Column upstream) {
        super(limit);
        this.upstream = upstream;
    }

    @Override
    public void addCard(Card card) {
        if (cards.size() == getLimit()) {
            throw new IllegalStateException();
        }
        cards.add(card);
    }

    @Override
    public Queue<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context) {
        // Only pull once a day!
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void doTheWork(Context context) {
        LOGGER.info("{}: Replenishing {} from {}", context.getDay(), this, upstream);
        while (getCards().size() < getLimit()) {
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                optionalCard.get().setDaySelected(context.getDay().getOrdinal());
                addCard(optionalCard.get());
                LOGGER.info("Pulled {} into {} from {}", optionalCard.get(), this, upstream);
            } else {
                LOGGER.warn("Nothing to pull.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "[SELECTED (" + cards.size() + "/" + getLimit() + ")]";
    }
}
