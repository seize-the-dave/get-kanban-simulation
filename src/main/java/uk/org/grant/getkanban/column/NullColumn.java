package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;

import java.util.*;

public class NullColumn extends AbstractColumn {
    @Override
    public void addCard(Card card, ClassOfService cos) {
        // Do nothing
    }

    @Override
    public List<Card> getCards() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        return Optional.empty();
    }

    public void clear() {}
}
