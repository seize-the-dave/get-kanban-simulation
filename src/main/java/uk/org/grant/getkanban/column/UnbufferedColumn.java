package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;

public abstract class UnbufferedColumn extends AbstractColumn {
    protected final Column upstream;
    protected final Queue<Card> cards;

    public UnbufferedColumn(Column upstream) {
        this(upstream, new BusinessValuePrioritisationStrategy());;
    }

    public UnbufferedColumn(Column upstream, Comparator<Card> comparator) {
        this.upstream = upstream;
        this.cards = new PriorityQueue<>(comparator);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return Collections.unmodifiableCollection(cards);
    }

    @Override
    public Optional<Card> pull(Context context) {
        doTheWork(context);
        return Optional.ofNullable(cards.poll());
    }
}
