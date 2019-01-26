package uk.org.grant.getkanban.policies.expedite;

import org.junit.Test;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.policies.expedite.AlwaysExpediteExpeditePolicy;
import uk.org.grant.getkanban.policies.expedite.ExpeditePolicy;

import static org.junit.Assert.assertTrue;

public class AlwaysExpediteExpeditePolicyTest {
    @Test
    public void shouldAlwaysExpedite() {
        ExpeditePolicy policy = new AlwaysExpediteExpeditePolicy();
        assertTrue(policy.shouldExpedite(Cards.getCard("S10")));

    }
}
