package uk.org.grant.getkanban;

import java.util.*;

public class BacklogColumn extends UnbufferedColumn {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());

    public BacklogColumn() {
        super(new NullColumn());
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
    public void visit(Day day) {
        //
    }
}
