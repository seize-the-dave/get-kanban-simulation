package uk.org.grant.getkanban.policies.dice;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.policies.dice.DiceAssignmentStrategy;
import uk.org.grant.getkanban.policies.dice.NoCrossSkillingDiceAssignmentStrategy;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class NoCrossSkillingDiceAssignmentStrategyTest {
    @Test
    public void allTestDiceAreAssignedToFirstTestCard() {
        DiceAssignmentStrategy s = new NoCrossSkillingDiceAssignmentStrategy();

        Board b = new Board();
        Card s10 = Cards.getCard("S10");
        Card s11 = Cards.getCard("S11");

        b.getStateColumn(State.TEST).addCard(s10);
        b.getStateColumn(State.TEST).addCard(s11);

        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));
        b.addDice(new StateDice(State.TEST, new LoadedDice(6)));

        assertThat(s10.getRemainingWork(State.TEST), is(9));
        assertThat(s11.getRemainingWork(State.TEST), is(9));

        s.assignDice(b);
        b.getStateColumn(State.TEST).doTheWork(new Context(b, new Day(9)));

        assertThat(s11.getRemainingWork(State.TEST), is(0));
        assertThat(s10.getRemainingWork(State.TEST), is(6));
    }
}
