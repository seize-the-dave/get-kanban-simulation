package uk.org.grant.getkanban;

import java.util.Comparator;

public class DefaultPrioritisationStrategy implements Comparator<Card> {
    @Override
    public int compare(Card o1, Card o2) {
        return 0;
    }
}
