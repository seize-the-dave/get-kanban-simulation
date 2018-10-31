package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;

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
    public void visit(Context context) {
        // No Op
    }
}
