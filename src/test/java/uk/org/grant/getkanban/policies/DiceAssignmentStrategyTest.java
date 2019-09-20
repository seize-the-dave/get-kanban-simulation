package uk.org.grant.getkanban.policies;

import org.junit.Test;
import uk.org.grant.getkanban.*;
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
        b.clear();

        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        Card s10 = Cards.getCard("S10");
        b.getStateColumn(State.TEST).addCard(s10, ClassOfService.STANDARD);
        ComplexDiceAssignmentStrategy s = new ComplexDiceAssignmentStrategy();

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
        b.getStateColumn(State.TEST).addCard(s10, ClassOfService.STANDARD);
        ComplexDiceAssignmentStrategy s = new ComplexDiceAssignmentStrategy();

        assertThat(s10.getRemainingWork(State.TEST), is(9));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.TEST), is(9));
    }

    @Test
    public void unusedTestDiceAreUsedByDevelopment() {
        Board b = new Board();
        b.clear();

        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        Card s10 = Cards.getCard("S10");
        b.getStateColumn(State.DEVELOPMENT).addCard(s10, ClassOfService.STANDARD);
        ComplexDiceAssignmentStrategy s = new ComplexDiceAssignmentStrategy();

        assertThat(s10.getRemainingWork(State.DEVELOPMENT), is(6));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.DEVELOPMENT), is(3));
    }

    @Test
    public void unusedDevelopmentDiceAreUsedByTest() {
        Board b = new Board();
        b.clear();

        Card s10 = Cards.getCard("S10");
        b.getStateColumn(State.TEST).addCard(s10, ClassOfService.STANDARD);

        b.addDice(new StateDice(State.DEVELOPMENT, new LoadedDice(6)));
        ComplexDiceAssignmentStrategy s = new ComplexDiceAssignmentStrategy();

        System.out.println(b.toString());

        assertThat(s10.getRemainingWork(State.TEST), is(9));

        Day d = new Day(10);
        d.standUp(b);
        d.doTheWork(new Context(b, d));
        d.endOfDay(b);
//        s.assignDice(b);
//        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        assertThat(s10.getRemainingWork(State.TEST), is(6));
    }

    @Test
    public void diceAreAllocatedAccordingToExpectedRoll() {
        // We think each dice will roll a 6
        BigDecimal expectedRoll = new BigDecimal(6);

        Board b = new Board();
        b.clear();

        b.addDice(new StateDice(State.TEST, new LoadedDice(1)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(1)));
        Card s3 = Cards.getCard("S3");
        b.getStateColumn(State.TEST).addCard(s3, ClassOfService.STANDARD);
        ComplexDiceAssignmentStrategy s = new ComplexDiceAssignmentStrategy(expectedRoll);
        assertThat(s3.getRemainingWork(State.TEST), is(6));

        // Only one dice will be assigned because one 6 is enough to finish S3
        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(1)));

        // Only one dice was used
        assertThat(s3.getRemainingWork(State.TEST), is(5));
    }
}
