package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardTest {
    @Test
    public void testSize() {
        Card card = createCard();
        assertThat(card.getSize(), is(Card.Size.LOW));
    }

    @Test
    public void testGetRemainingDevelopmentWork() {
        Card card = createCard();
        assertThat(card.getRemainingWork(Activity.DEVELOPMENT), is(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoingExcessWorkThrowsException() {
        Card card = createCard();
        card.doWork(Activity.DEVELOPMENT, 15);
    }

    @Test
    public void testDoingWorkReducesRemainingWork() {
        Card card = createCard();
        card.doWork(Activity.DEVELOPMENT, 5);

        assertThat(card.getRemainingWork(Activity.DEVELOPMENT), is(5));
    }

    @Test
    public void testReducingDevelopmentWorkDoesNotAffectTestOrAnalysis() {
        Card card = createCard();
        assertThat(card.getRemainingWork(Activity.TEST), is(5));
        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(5));

        card.doWork(Activity.DEVELOPMENT, 5);

        assertThat(card.getRemainingWork(Activity.TEST), is(5));
        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(5));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnstartedCard() {
        Card card = createCard();
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnfinishedCard() {
        Card card = createCard();
        card.setDaySelected(1);
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotSetFinishBeforeSettingStart() {
        Card card = createCard();
        card.setDayDeployed(1);
    }

    @Test(expected = IllegalStateException.class)
    public void testFinishCannotBeBeforeStart() {
        Card card = createCard();
        card.setDaySelected(3);
        card.setDayDeployed(1);
    }

    @Test
    public void testCycleTimeIsEndMinusStart() {
        Card card = createCard();
        card.setDaySelected(1);
        card.setDayDeployed(3);
        assertThat(2, is(card.getCycleTime()));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnstartedCard() {
        Card card = createCard();
        card.getSubscribers();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnfinishedCard() {
        Card card = createCard();
        card.setDaySelected(1);
        card.getSubscribers();
    }

    @Test
    public void testSubscribersDecreaseWithLongerCycleTimes() {
        Card firstCard = createCard();
        firstCard.setDaySelected(1);
        firstCard.setDayDeployed(3);
        assertThat(10, is(firstCard.getSubscribers()));

        Card secondCard = createCard();
        secondCard.setDaySelected(1);
        secondCard.setDayDeployed(2);
        assertThat(20, is(secondCard.getSubscribers()));
    }

    private Card createCard() {
        return new Card(Card.Size.LOW, 5, 10, 5, new SubscriberProfile(new int[]{30, 20, 10}));
    }
}
