package uk.org.grant.getkanban.card;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AbstractCardTest {
    @Test
    public void isBlockedReturnsTrueWhenBlockerPresent() {
        Card c = Cards.getCard("S1");
        c.setBlocker(new Blocker());

        assertTrue(c.isBlocked());
    }

    @Test
    public void isBlockedReturnsFalseWhenBlockerAbsent() {
        Card c = Cards.getCard("S2");

        assertFalse(c.isBlocked());
    }

    @Test
    public void isBlockedReturnsFalseWhenBlockerPresentButEmpty() {
        Card c = Cards.getCard("S3");
        c.setBlocker(new Blocker());
        c.getBlocker().doWork(7);

        assertFalse(c.isBlocked());
    }
}
