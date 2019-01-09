package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.column.Options;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractLane implements Swimlane {
    private final Options options;
    private final List<Card> cards = new LinkedList<Card>();
    private int wipLimit = Integer.MAX_VALUE;

    public AbstractLane(Options options) {
        this.options = options;
    }

    @Override
    public void pull(Day day) {
        Predicate<Card> notExpediteCards = getLaneFilter(day);
        options.getCards().stream().filter(notExpediteCards).limit(wipLimit).forEach(c -> {
            cards.add(c);
        });
    }

    protected abstract Predicate<Card> getLaneFilter(Day day);

    @Override
    public List<Card> getCards() {
        return cards;
    }

    @Override
    public int getWipLimit() {
        return wipLimit;
    }

    @Override
    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }
}
