package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.card.Card;

public class AlwaysExpediteExpeditePolicy implements ExpeditePolicy {
    @Override
    public boolean shouldExpedite(Card card) {
        return true;
    }
}
