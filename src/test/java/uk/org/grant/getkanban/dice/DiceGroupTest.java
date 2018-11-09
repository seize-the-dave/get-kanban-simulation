package uk.org.grant.getkanban.dice;

import org.junit.Test;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.StandardCard;
import uk.org.grant.getkanban.card.Cards;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DiceGroupTest {
    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfCalledMoreThanOnce() {
        StandardCard s10 = Cards.getCard("S10");
        DiceGroup group = new DiceGroup(s10, new StateDice(State.ANALYSIS, new LoadedDice(2)));

        assertThat(s10.getRemainingWork(State.ANALYSIS), is(1));

        group.rollFor(State.ANALYSIS);
        group.rollFor(State.ANALYSIS);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionIfAttemptingToSpendPointsOutsideOriginalSpecialisation() {
        StandardCard s10 = Cards.getCard("S10");
        DiceGroup group = new DiceGroup(s10, new StateDice(State.ANALYSIS, new LoadedDice(2)));

        assertThat(s10.getRemainingWork(State.ANALYSIS), is(1));

        group.rollFor(State.ANALYSIS);

        group.spendLeftoverPoints(State.DEVELOPMENT, s10);
    }

    @Test
    public void canSpendLeftoverPoints() {
        StandardCard s10 = Cards.getCard("S10");
        DiceGroup group = new DiceGroup(s10,
                new StateDice(State.ANALYSIS, new LoadedDice(6)),
                new StateDice(State.ANALYSIS, new LoadedDice(2)));
        assertThat(s10.getRemainingWork(State.ANALYSIS), is(1));

        group.rollFor(State.ANALYSIS);

        assertThat(s10.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(group.getLeftoverPoints(), is(7));

        StandardCard s18 = Cards.getCard("S18");
        assertThat(s18.getRemainingWork(State.ANALYSIS), is(6));

        group.spendLeftoverPoints(State.ANALYSIS, s18);

        assertThat(s18.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(group.getLeftoverPoints(), is(1));
    }

    @Test
    public void threeDiceShouldHaveNoRemainder() {
        StandardCard s10 = Cards.getCard("S17");
        DiceGroup group = new DiceGroup(s10,
                new StateDice(State.ANALYSIS, new LoadedDice(2)),
                new StateDice(State.ANALYSIS, new LoadedDice(2)),
                new StateDice(State.ANALYSIS, new LoadedDice(2)));

        assertThat(s10.getRemainingWork(State.ANALYSIS), is(5));

        group.rollFor(State.ANALYSIS);

        assertThat(s10.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(group.getLeftoverPoints(), is(0));
    }
}
