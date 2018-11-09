package uk.org.grant.getkanban.card;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.DaysFactory;
import uk.org.grant.getkanban.State;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StandardCardTest {
    @Test
    public void testSize() {
        StandardCard card = createCard();
        assertThat(card.getSize(), is(StandardCard.Size.HIGH));
    }

    @Test
    public void testGetName() {
        StandardCard card = createCard();
        assertThat(card.getName(), is("S18"));
    }

    @Test
    public void testGetRemainingDevelopmentWork() {
        StandardCard card = Cards.getCard("S1");
        assertThat(card.getRemainingWork(State.DEVELOPMENT), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoingExcessWorkThrowsException() {
        StandardCard card = createCard();
        card.doWork(State.DEVELOPMENT, 15);
    }

    @Test
    public void testDoingWorkReducesRemainingWork() {
        StandardCard card = Cards.getCard("S10");
        card.doWork(State.DEVELOPMENT, 5);

        assertThat(card.getRemainingWork(State.DEVELOPMENT), is(1));
    }

    @Test
    public void testReducingDevelopmentWorkDoesNotAffectTestOrAnalysis() {
        StandardCard card = Cards.getCard("S10");
        assertThat(card.getRemainingWork(State.TEST), is(9));
        assertThat(card.getRemainingWork(State.ANALYSIS), is(1));

        card.doWork(State.DEVELOPMENT, 6);

        assertThat(card.getRemainingWork(State.TEST), is(9));
        assertThat(card.getRemainingWork(State.ANALYSIS), is(1));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnstartedCard() {
        StandardCard card = createCard();
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetCycleTimeForUnfinishedCard() {
        StandardCard card = createCard();
        card.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));
        card.getCycleTime();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotSetFinishBeforeSettingStart() {
        StandardCard card = Cards.getCard("S18");
        card.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(1)));
    }

    @Test(expected = IllegalStateException.class)
    public void testFinishCannotBeBeforeStart() {
        StandardCard card = createCard();
        card.onSelected(new Context(new Board(), new DaysFactory(true).getDay(3)));
        card.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(1)));
    }

    @Test
    public void testCycleTimeIsEndMinusStart() {
        StandardCard card = createCard();
        card.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));
        card.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(3)));
        assertThat(2, is(card.getCycleTime()));
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnstartedCard() {
        StandardCard card = createCard();
        card.getSubscribers();
    }

    @Test(expected = IllegalStateException.class)
    public void testCannotGetSubscribersForUnfinishedCard() {
        StandardCard card = createCard();
        card.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));
        card.getSubscribers();
    }

    @Test
    public void testSubscribersDecreaseWithLongerCycleTimes() {
        StandardCard firstCard = Cards.getCard("S10");
        firstCard.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));
        firstCard.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(3)));
        assertThat(16, is(firstCard.getSubscribers()));

        StandardCard secondCard = Cards.getCard("S10");
        secondCard.onSelected(new Context(new Board(), new DaysFactory(true).getDay(1)));
        secondCard.onDeployed(new Context(new Board(), new DaysFactory(true).getDay(2)));
        assertThat(17, is(secondCard.getSubscribers()));
    }

    private StandardCard createCard() {
        return Cards.getCard("S18");
    }
}
