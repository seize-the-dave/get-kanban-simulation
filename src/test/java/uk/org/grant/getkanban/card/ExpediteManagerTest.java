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

    @Test
    public void unexpeditedCardIsNotMarkedAsExpedited() {
        ExpediteManager manager = new ExpediteManager();
        manager.expedite(Cards.getCard("F1"), new DaysFactory(true).getDay(12));
        assertFalse(manager.isExpedited(Cards.getCard("F1")));
    }

    @Test
    public void expeditedCardIsMarkedAsExpedited() {
        ExpediteManager manager = new ExpediteManager();
        manager.expedite(Cards.getCard("F1"), new DaysFactory(true).getDay(14));
        assertTrue(manager.isExpedited(Cards.getCard("F1")));
    }

    @Test
    public void canExpediteSingleItem() {
        ExpediteManager manager = new ExpediteManager();
        assertTrue(manager.expedite(Cards.getCard("E1"), new DaysFactory(true).getDay(14)));
        assertFalse(manager.expedite(Cards.getCard("E2"), new DaysFactory(true).getDay(17)));
    }

    @Test
    public void canExpediteUpToWipLimit() {
        ExpediteManager manager = new ExpediteManager();
        assertTrue(manager.expedite(Cards.getCard("E1"), new DaysFactory(true).getDay(14)));
        manager.setWipLimit(2);
        assertTrue(manager.expedite(Cards.getCard("E2"), new DaysFactory(true).getDay(17)));
    }

    @Test
    public void canRemoveCompletedItem() {
        ExpediteManager manager = new ExpediteManager();
        assertTrue(manager.expedite(Cards.getCard("E1"), new DaysFactory(true).getDay(14)));
        manager.remove(Cards.getCard("E1"));
        assertFalse(manager.isExpedited(Cards.getCard("E1")));
    }
}
