package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class UnbufferedColumn extends AbstractColumn {
    protected final Column upstream;
    protected final MutablePriorityQueue<Card> cards;

    public UnbufferedColumn(Column upstream) {
        this(upstream, new WipAgingPrioritisationStrategy());;
    }

    public UnbufferedColumn(Column upstream, Comparator<Card> comparator) {
        this.upstream = upstream;
        this.cards = new MutablePriorityQueue<>(comparator);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Queue<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull(Context context) {
        doTheWork(context);
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        cards.setComparator(comparator);
    }
}
