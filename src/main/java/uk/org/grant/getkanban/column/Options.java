package uk.org.grant.getkanban.column;

import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.policies.prioritisation.BusinessValuePrioritisationStrategy;
import uk.org.grant.getkanban.policies.prioritisation.IntangiblesFirstPrioritisationStrategy;

import java.util.*;

public class Options extends UnbufferedColumn {
    public Options() {
        this(new IntangiblesFirstPrioritisationStrategy().thenComparing(new BusinessValuePrioritisationStrategy()));
    }

    public Options(Comparator<Card> comparator) {
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
