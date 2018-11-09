package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StateColumnTest {
    @Test
    public void testDoingWorkOnColumnReducesCardWork() {
        Card card = Cards.getCard("S1");
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        column.addCard(card);

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        DiceGroup group = new DiceGroup(column.getCards().peek(), dice);
        column.assignDice(group);

        column.doTheWork(new Context(new Board(), new Day(1)));

        assertThat(card.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void testSpendLeftoverWork() {
        Board b = new Board();
        Day d = new Day(10);

        Card s8 = Cards.getCard("S8");
        Card s12 = Cards.getCard("S12");
        b.getStateColumn(State.ANALYSIS).addCard(s8);
        b.getStateColumn(State.ANALYSIS).addCard(s12);
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));

        d.standUp(b);
        d.doTheWork(new Context(b, d));

        assertThat(s8.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(s12.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        Context context = new Context(new Board(), new Day(1));
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        column.addCard(Cards.getCard("S1"));

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        column.assignDice(new DiceGroup(column.getCards().peek(), dice));

        column.doTheWork(context);

        assertThat(column.pull(context).get(), is(Cards.getCard("S1")));
    }

    @Test
    public void testCanPullFromUpstream() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(Cards.getCard("S8"));

        StateColumn development = new StateColumn(State.DEVELOPMENT, analysis);
        assertThat(development.getIncompleteCards(), empty());

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        analysis.assignDice(new DiceGroup(analysis.getCards().peek(), dice));
        analysis.doTheWork(new Context(new Board(), new Day(1)));
        development.doTheWork(new Context(new Board(), new Day(1)));

        assertThat(analysis.getIncompleteCards(), not(hasItem(Cards.getCard("S8"))));
        assertThat(development.getIncompleteCards(), hasItem(Cards.getCard("S8")));
    }

    @Test
    public void canGetWipLimit() {
        StateColumn column = new StateColumn(State.ANALYSIS, 4, new NullColumn());

        assertThat(column.getLimit(), is(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotExceedWipLimit() {
        StateColumn column = new StateColumn(State.ANALYSIS, 1, new NullColumn());

        column.addCard(Cards.getCard("S1"));
        column.addCard(Cards.getCard("S2"));
    }

    @Test
    public void willNotPullBeyondWipLimit() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, 1, new NullColumn());
        StateColumn development = new StateColumn(State.ANALYSIS, 1, analysis);

        analysis.addCard(Cards.getCard("S1"));
        development.addCard(Cards.getCard("S2"));

        development.doTheWork(new Context(new Board(), new Day(1)));
        assertThat(development.getCards().size(), is(1));
    }
}
