package uk.org.grant.getkanban.card;

import uk.org.grant.getkanban.Day;

public class ExpediteManager {
    public boolean expedite(Card card, Day day) {
        if (card instanceof FixedDateCard && card.getName().startsWith("E")) {
            return true;
        }
        if (card instanceof FixedDateCard && card.getDueDate() - day.getOrdinal() < 3) {
            return true;
        }
        return false;
    }
}
