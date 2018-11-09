package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;

public abstract class UnbufferedColumn extends AbstractColumn {
    protected final Column upstream;
    protected final Queue<StandardCard> cards;

    public UnbufferedColumn(Column upstream) {
        this(upstream, new BusinessValuePrioritisationStrategy());;
    }

    public UnbufferedColumn(Column upstream, Comparator<StandardCard> comparator) {
        this.upstream = upstream;
        this.cards = new PriorityQueue<>(comparator);
    }

    @Override
    public void addCard(StandardCard card) {
        cards.add(card);
    }

    @Override
    public Queue<StandardCard> getCards() {
        return cards;
    }

    @Override
    public Optional<StandardCard> pull(Context context) {
        doTheWork(context);
        return Optional.ofNullable(cards.poll());
    }
}
