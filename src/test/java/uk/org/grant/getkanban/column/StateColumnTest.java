package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
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
        column.assignDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        column.doTheWork(new Context(new Board(), new Day(1)));

        assertThat(card.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        Context context = new Context(new Board(), new Day(1));
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        column.addCard(Cards.getCard("S1"));
        column.assignDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        column.doTheWork(context);

        assertThat(column.pull(context).get(), is(Cards.getCard("S1")));
    }

    @Test
    public void testCanPullFromUpstream() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(Cards.getCard("S8"));
//        analysis.addCard(Cards.getCard("S2"));

        StateColumn development = new StateColumn(State.DEVELOPMENT, analysis);
        assertThat(development.getIncompleteCards(), empty());

        analysis.assignDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
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
