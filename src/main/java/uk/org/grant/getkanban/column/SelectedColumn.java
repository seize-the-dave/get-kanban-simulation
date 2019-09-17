package uk.org.grant.getkanban.column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.ClassOfService;
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
    public void addCard(Card card, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            throw new IllegalArgumentException("Expedite is not applicable for selected");
        }
        if (cards.size() == getLimit()) {
            throw new IllegalStateException();
        }
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return cards.stream().sorted(cards.getComparator()).collect(Collectors.toList());
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        if (cos == ClassOfService.EXPEDITE) {
            throw new IllegalArgumentException("Shouldn't pull for expedite from selected");
        }
        return Optional.ofNullable(cards.poll());
    }

    /**
     * This is called during the daily stand up (we're only allowed to pull once per day)
     *
     * @param context
     */
    @Override
    public void doTheWork(Context context) {
        while (getCards().size() < getLimit()) {
            Optional<Card> optionalCard = upstream.pull(context, ClassOfService.STANDARD);
            if (optionalCard.isPresent()) {
                optionalCard.get().onSelected(context);
                addCard(optionalCard.get(), ClassOfService.STANDARD);
                LOGGER.info("{}: {} -> {} -> {} ({})", context.getDay(), upstream, optionalCard.get().getName(), this, ClassOfService.STANDARD);
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
