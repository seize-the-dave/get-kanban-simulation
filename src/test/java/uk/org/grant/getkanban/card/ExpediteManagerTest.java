package uk.org.grant.getkanban.card;

import org.junit.Test;
import uk.org.grant.getkanban.DaysFactory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExpediteManagerTest {
    @Test
    public void intangiblesCannotBeExpedited() {
        ExpediteManager manager = new ExpediteManager();
        assertFalse(manager.expedite(Cards.getCard("I1"), new DaysFactory(true).getDay(12)));
    }

    @Test
    public void standardCardsCannotBeExpedited() {
        ExpediteManager manager = new ExpediteManager();
        assertFalse(manager.expedite(Cards.getCard("S1"), new DaysFactory(true).getDay(12)));
    }

    @Test
    public void expediteCardsCanBeExpedited() {
        ExpediteManager manager = new ExpediteManager();
        assertTrue(manager.expedite(Cards.getCard("E1"), new DaysFactory(true).getDay(12)));
    }

    @Test
    public void fixedDateCardsCannotBeExpeditedIfDueInThreeDaysOrMore() {
        ExpediteManager manager = new ExpediteManager();
        assertFalse(manager.expedite(Cards.getCard("F1"), new DaysFactory(true).getDay(12)));
    }

    @Test
    public void fixedDateCardsCanBeExpeditedIfDueInLessThanThreeDays() {
        ExpediteManager manager = new ExpediteManager();
        assertTrue(manager.expedite(Cards.getCard("F1"), new DaysFactory(true).getDay(14)));
    }
}
