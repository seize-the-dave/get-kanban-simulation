package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;

import java.util.*;
import java.util.stream.Collectors;

public class SelectedColumn extends LimitedColumn {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectedColumn.class);
    private final MutablePriorityQueue<Card> cards = new MutablePriorityQueue<>(new WipAgingPrioritisationStrategy());
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
        while (getCards().size() < getLimit()) {
            Optional<Card> optionalCard = upstream.pull(context);
            if (optionalCard.isPresent()) {
                optionalCard.get().onSelected(context);
                addCard(optionalCard.get());
                LOGGER.info("{}: {} -> {} -> {}", context.getDay(), upstream, optionalCard.get().getName(), this);
            } else {
                LOGGER.warn("{}: Nothing to pull.", context.getDay());
                break;
            }
        }
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        cards.setComparator(comparator);
    }

    @Override
    public String toString() {
        return "[SELECTED (" + cards.size() + "/" + getLimit() + ")]";
    }
}
