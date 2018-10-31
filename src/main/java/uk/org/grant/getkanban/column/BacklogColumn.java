package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;

public class BacklogColumn extends UnbufferedColumn {
    private final Queue<Card> cards;

    public BacklogColumn() {
        this(new BusinessValuePrioritisationStrategy());
    }

    public BacklogColumn(Comparator<Card> comparator) {
        super(new NullColumn());

        cards = new PriorityQueue<>(comparator);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return cards;
    }

    @Override
    public Optional<Card> pull() {
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void visit(Context context) {
        //
    }

    @Override
    public String toString() {
        return "[BACKLOG (" + cards.size() + "/-)]";
    }
}
