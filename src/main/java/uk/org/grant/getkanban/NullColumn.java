package uk.org.grant.getkanban;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class NullColumn extends AbstractColumn {
    @Override
    public void addCard(Card card) {
        // Do nothing
    }

    @Override
    public Collection<Card> getCards() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Card> pull() {
        return Optional.empty();
    }

    @Override
    public void visit(Day day) {
        // No Op
    }
}
