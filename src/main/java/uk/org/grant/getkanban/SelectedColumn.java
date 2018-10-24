package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.*;

public class SelectedColumn implements Column, Limited {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());
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
    public void allocateDice(ActivityDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLimit() {
        return limit;
    }
}
