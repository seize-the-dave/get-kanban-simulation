package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;

import java.util.Comparator;

public abstract class AbstractColumn implements Column {
    public int getLimit() {
        return Integer.MAX_VALUE;
    }

    public void setLimit(int limit) {
        // Do nothing
    }

    @Override
    public void doTheWork(Context context) {
        // No Op
    }

    @Override
    public void orderBy(Comparator<Card> comparator) {

    }
}
