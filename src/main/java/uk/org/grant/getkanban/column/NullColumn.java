package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.ClassOfService;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.StandardCard;

import java.util.*;

public class NullColumn extends AbstractColumn {
    @Override
    public void addCard(Card card, ClassOfService cos) {
        // Do nothing
    }

    @Override
    public List<Card> getCards() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Optional<Card> pull(Context context, ClassOfService cos) {
        return Optional.empty();
    }
}
