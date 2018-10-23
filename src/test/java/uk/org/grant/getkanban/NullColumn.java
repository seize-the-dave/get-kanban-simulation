package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.ActivityDice;

import java.util.List;
import java.util.Optional;

public class NullColumn implements Column {
    @Override
    public void addCard(Card card) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Card> pull() {
        return Optional.empty();
    }

    @Override
    public void visit(Day day) {
        // No Op
    }

    @Override
    public void allocateDice(ActivityDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ActivityDice> getAllocatedDice() {
        throw new UnsupportedOperationException();
    }
}
