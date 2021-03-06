package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.policies.WipAgingPrioritisationStrategy;

import java.util.*;
import java.util.stream.Collectors;

public abstract class UnbufferedColumn extends AbstractColumn {
    protected final Column standard;
    protected final MutablePriorityQueue<Card> cards;

    public UnbufferedColumn(Column upstream) {
        this(upstream, new WipAgingPrioritisationStrategy());;
    }

    public UnbufferedColumn(Column standard, Comparator<Card> comparator) {
        this.standard = standard;
        this.cards = new MutablePriorityQueue<>(comparator);
    }

    @Override
    public void addCard(Card card, ClassOfService cos) {
        cards.add(card);
    }

    @Override
    public List<Card> getCards() {
        return cards.stream().sorted(cards.getComparator()).collect(Collectors.toList());
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        doTheWork(context);
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {
        cards.setComparator(comparator);
    }
}
