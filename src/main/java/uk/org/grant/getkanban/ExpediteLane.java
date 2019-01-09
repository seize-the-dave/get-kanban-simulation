package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.Options;

import java.util.function.Predicate;

public class ExpediteLane extends AbstractLane implements Swimlane {
    public ExpediteLane(Options options) {
        super(options);
    }

    @Override
    protected Predicate<Card> getLaneFilter(Day day) {
        Predicate<Card> expediteCards = c -> c.getName().startsWith("E");
        Predicate<Card> fixedDateCards = c -> c.getName().startsWith("F");
        Predicate<Card> dueInLessThanThreeDays = c -> c.getDueDate() - day.getOrdinal() < 3;

        return expediteCards.or(fixedDateCards.and(dueInLessThanThreeDays));
    }
}
