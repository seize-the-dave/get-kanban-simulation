package uk.org.grant.getkanban.card;

import org.junit.Test;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CardTest {
    @Test
    public void testSize() {
        Card card = createCard();
        assertThat(card.getSize(), is(Card.Size.HIGH));
    }

    @Test
    public void testGetName() {
        Card card = createCard();
        assertThat(card.getName(), is("S18"));
    }

    @Test
    public void testGetRemainingDevelopmentWork() {
        Card card = Cards.getCard("S1");
        assertThat(card.getRemainingWork(State.DEVELOPMENT), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoingExcessWorkThrowsException() {
        Card card = createCard();
        card.doWork(State.DEVELOPMENT, 15);
    }

    @Test
    public void testDoingWorkReducesRemainingWork() {
        Card card = Cards.getCard("S10");
        card.doWork(State.DEVELOPMENT, 5);

        assertThat(card.getRemainingWork(State.DEVELOPMENT), is(1));
    }

    @Test
    public void testReducingDevelopmentWorkDoesNotAffectTestOrAnalysis() {
        Card card = Cards.getCard("S10");
        assertThat(card.getRemainingWork(State.TEST), is(9));
        assertThat(card.getRemainingWork(State.ANALYSIS), is(1));

        card.doWork(State.DEVELOPMENT, 6);

        assertThat(card.getRemainingWork(State.TEST), is(9));
        assertThat(card.getRemainingWork(State.ANALYSIS), is(1));
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
        Card card = Cards.getCard("S10");
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
        Card firstCard = Cards.getCard("S10");
        firstCard.setDaySelected(1);
        firstCard.setDayDeployed(3);
        assertThat(16, is(firstCard.getSubscribers()));

        Card secondCard = Cards.getCard("S10");
        secondCard.setDaySelected(1);
        secondCard.setDayDeployed(2);
        assertThat(17, is(secondCard.getSubscribers()));
    }

    private Card createCard() {
        return Cards.getCard("S18");
    }
}
