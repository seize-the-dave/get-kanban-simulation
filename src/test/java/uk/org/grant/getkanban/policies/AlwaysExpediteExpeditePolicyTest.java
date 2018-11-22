package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.card.Cards;

import static org.junit.Assert.assertTrue;

public class AlwaysExpediteExpeditePolicyTest {
    @Test
    public void shouldAlwaysExpedite() {
        ExpeditePolicy policy = new AlwaysExpediteExpeditePolicy();
        assertTrue(policy.shouldExpedite(Cards.getCard("S10")));

    }
}
