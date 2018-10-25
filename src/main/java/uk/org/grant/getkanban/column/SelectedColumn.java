package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.WipAgingPrioritisationStrategy;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.dice.StateDice;

import java.util.*;

public class SelectedColumn implements Column, Limited {
    private final Queue<Card> cards = new PriorityQueue<>(new WipAgingPrioritisationStrategy());
    private Column upstream;
    private int limit;

    public SelectedColumn(int limit, Column upstream) {
        this.upstream = upstream;
        this.limit = limit;
    }

    @Override
    public void addCard(Card card) {
        if (cards.size() == limit) {
            throw new IllegalStateException();
        }
        cards.add(card);
    }

    @Override
    public Collection<Card> getCards() {
        return Collections.unmodifiableCollection(cards);
    }

    @Override
    public Optional<Card> pull() {
        return Optional.ofNullable(cards.poll());
    }

    @Override
    public void visit(Day day) {
        if (cards.size() < this.limit) {
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                card.setDaySelected(day.getOrdinal());
                addCard(card);
            }
        }
    }

    @Override
    public void allocateDice(StateDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateDice> getAllocatedDice() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
