package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
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
    public void visit(Context context) {
        while (getCards().size() < this.limit) {
//            System.out.println("Try pulling from " + upstream);
            Optional<Card> optionalCard = upstream.pull();
            if (optionalCard.isPresent()) {
                optionalCard.get().setDaySelected(context.getDay().getOrdinal());
                addCard(optionalCard.get());
//                System.out.println("Pulled " + optionalCard.get() + " into " + this);
            } else {
//                System.out.println(upstream + " has nothing to pull.");
                break;
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

    @Override
    public String toString() {
        return "[SELECTED (" + cards.size() + "/" + limit + ")]";
    }
}
