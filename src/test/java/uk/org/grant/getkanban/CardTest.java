package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardTest {
    @Test
    public void testSize() {
        Card card = new Card(Card.Size.SMALL);
        assertThat(card.getSize(), is(Card.Size.SMALL));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnstartedCard() {
        Card card = new Card(Card.Size.SMALL);
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnfinishedCard() {
        Card card = new Card(Card.Size.SMALL);
        card.setStartDay(1);
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotSetFinishBeforeSettingStart() {
        Card card = new Card(Card.Size.SMALL);
        card.setFinishDay(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testFinishCannotBeBeforeStart() {
        Card card = new Card(Card.Size.SMALL);
        card.setStartDay(3);
        card.setFinishDay(1);
    }

    @Test
    public void testCycleTimeIsEndMinusStart() {
        Card card = new Card(Card.Size.SMALL);
        card.setStartDay(1);
        card.setFinishDay(3);
        assertThat(2, is(card.getCycleTime()));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnfinishedCard() {
        Card card = new Card(Card.Size.SMALL);
        card.getSubscribers();
    }
}
