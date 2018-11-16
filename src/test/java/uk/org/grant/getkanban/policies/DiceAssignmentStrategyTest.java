package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Blocker;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.dice.StateDice;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DiceAssignmentStrategyTest {
    @Test
    public void assignTestDiceToTestCards() {
        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        Card s10 = Cards.getCard("S10");
        b.getStateColumn(State.TEST).addCard(s10);
        DiceAssignmentStrategy s = new DiceAssignmentStrategy();

        assertThat(s10.getRemainingWork(State.TEST), is(9));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.TEST), is(3));
    }

    @Test
    public void doNotAssignDiceToBlockedItems() {
        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        Card s10 = Cards.getCard("S10");
        s10.setBlocker(new Blocker());
        b.getStateColumn(State.TEST).addCard(s10);
        DiceAssignmentStrategy s = new DiceAssignmentStrategy();

        assertThat(s10.getRemainingWork(State.TEST), is(9));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.TEST), is(9));
    }

    @Test
    public void unusedTestDiceAreUsedByDevelopment() {
        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        Card s10 = Cards.getCard("S10");
        b.getStateColumn(State.DEVELOPMENT).addCard(s10);
        DiceAssignmentStrategy s = new DiceAssignmentStrategy();

        assertThat(s10.getRemainingWork(State.DEVELOPMENT), is(6));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.DEVELOPMENT), is(3));
    }

    @Test
    public void noMoreThanMaximumDiceAllocatedToOneCard() {
        // Only 1 dice per card
        int maxDice = 1;

        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(3)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(3)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(3)));
        Card s12 = Cards.getCard("S12");
        b.getStateColumn(State.TEST).addCard(s12);
        DiceAssignmentStrategy s = new DiceAssignmentStrategy(new BigDecimal(3.5), maxDice);
        assertThat(s12.getRemainingWork(State.TEST), is(10));

        // S12 has 10 points remaining.  We should use three dice, but we're only going to use one.
        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        // We should use
        assertThat(s12.getRemainingWork(State.TEST), is(7));
    }

    @Test
    public void diceAreAllocatedAccordingToExpectedRoll() {
        // We think each dice will roll a 6
        BigDecimal expectedRoll = new BigDecimal(6);

        Board b = new Board();
        b.addDice(new StateDice(State.TEST, new LoadedDice(1)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(1)));
        Card s3 = Cards.getCard("S3");
        b.getStateColumn(State.TEST).addCard(s3);
        DiceAssignmentStrategy s = new DiceAssignmentStrategy(expectedRoll);
        assertThat(s3.getRemainingWork(State.TEST), is(6));

        // Only one dice will be assigned because one 6 is enough to finish S3
        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        // Only one dice was used
        assertThat(s3.getRemainingWork(State.TEST), is(5));
    }
}
