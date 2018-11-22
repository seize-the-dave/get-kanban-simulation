package uk.org.grant.getkanban.policies;

import uk.org.grant.getkanban.card.Card;

public interface ExpeditePolicy {
    boolean shouldExpedite(Card card);
}
