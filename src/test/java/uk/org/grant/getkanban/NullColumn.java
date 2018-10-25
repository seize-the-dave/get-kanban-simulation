package uk.org.grant.getkanban;

import uk.org.grant.getkanban.dice.StateDice;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NullColumn implements Column {
    @Override
    public void addCard(Card card) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Card> getCards() {
        return Collections.EMPTY_LIST;
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
    public void allocateDice(StateDice... dice) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<StateDice> getAllocatedDice() {
        throw new UnsupportedOperationException();
    }
}
