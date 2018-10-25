package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.*;

public class BacklogColumn implements Column {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());

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

    @Override
    public void allocateDice(StateDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateDice> getAllocatedDice() {
        return null;
    }
}
