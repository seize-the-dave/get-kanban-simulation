package uk.org.grant.getkanban;

import uk.org.grant.getkanban.card.Card;

import java.util.List;

public interface Swimlane {
    void pull(Day day);
    List<Card> getCards();
    int getWipLimit();
    void setWipLimit(int wipLimit);
}
