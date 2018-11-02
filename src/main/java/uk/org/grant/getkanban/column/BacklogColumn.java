package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.BusinessValuePrioritisationStrategy;

import java.util.*;

public class BacklogColumn extends UnbufferedColumn {
    public BacklogColumn() {
        this(new BusinessValuePrioritisationStrategy());
    }

    public BacklogColumn(Comparator<Card> comparator) {
        super(new NullColumn(), comparator);
    }

    @Override
    public void doTheWork(Context context) {
        //
    }

    @Override
    public String toString() {
        return "[BACKLOG (" + cards.size() + "/∞)]";
    }
}
