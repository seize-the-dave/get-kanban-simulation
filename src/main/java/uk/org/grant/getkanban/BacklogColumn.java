package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public class BacklogColumn implements Column {
    private final Queue<Card> cards = new PriorityQueue<>(new DefaultPrioritisationStrategy());

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Optional<Card> pull() {
        return Optional.ofNullable(cards.remove());
    }

    @Override
    public void visit(Day day) {
        //
    }

    @Override
    public void allocateDice(ActivityDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        return null;
    }
}
