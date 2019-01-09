package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.Options;

import java.util.function.Predicate;

public class StandardLane extends AbstractLane implements Swimlane {

    public StandardLane(Options options) {
        super(options);
    }

    protected Predicate<Card> getLaneFilter(Day day) {
        return c -> !c.getName().startsWith("E");
    }

}
