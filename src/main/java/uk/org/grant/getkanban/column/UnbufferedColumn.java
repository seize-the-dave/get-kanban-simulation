package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;

public abstract class UnbufferedColumn extends AbstractColumn {
    protected final Column upstream;
    private final Queue<Card> cards = new PriorityQueue<>(new BusinessValuePrioritisationStrategy());

    public UnbufferedColumn(Column upstream) {
        this.upstream = upstream;
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
    public Optional<Card> pull() {
        return Optional.ofNullable(cards.remove());
    }
}
