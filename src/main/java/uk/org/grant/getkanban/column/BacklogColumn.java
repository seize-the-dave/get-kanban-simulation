package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.IntangiblesFirstPrioritisationStrategy;

import java.util.*;

public class BacklogColumn extends UnbufferedColumn {
    public BacklogColumn() {
        this(new IntangiblesFirstPrioritisationStrategy().thenComparing(new BusinessValuePrioritisationStrategy()));
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
