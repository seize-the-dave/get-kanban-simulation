package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.dice.StateDice;

import java.util.*;

public class SelectedColumn implements Column, Limited {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectedColumn.class);
    private final Queue<Card> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private Column upstream;
    private int limit;

    public SelectedColumn(int limit, Column upstream) {
        this.upstream = upstream;
        this.limit = limit;
    }

    @Override
    public void addCard(Card card) {
        if (cards.size() == limit) {
            throw new IllegalStateException();
        }
        cards.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return Collections.unmodifiableCollection(cards);
    }

    @Override
    public Optional<Card> pull(Context context) {
        // Only pull once a day!
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void doTheWork(Context context) {
        LOGGER.info("{}: Replenishing {} from {}", context.getDay(), this, upstream);
        while (getCards().size() < this.limit) {
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
    public void assignDice(StateDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateDice> getAssignedDice() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "[SELECTED (" + cards.size() + "/" + limit + ")]";
    }
}
