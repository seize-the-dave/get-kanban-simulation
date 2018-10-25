package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ActivityColumnTest {
    @Test
    public void testDoingWorkOnColumnReducesCardWork() {
        Card card = Cards.getCard("S1");

        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        column.addCard(card);
        column.allocateDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        column.visit(new Day(1));

        assertThat(card.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        column.addCard(Cards.getCard("S1"));
        column.allocateDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        column.visit(new Day(1));

        assertThat(column.pull().get(), is(Cards.getCard("S1")));
    }

    @Test
    public void testCanPullFromUpstream() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        analysis.addCard(Cards.getCard("S8"));
//        analysis.addCard(Cards.getCard("S2"));

        StateColumn development = new StateColumn(State.DEVELOPMENT, analysis);
        assertThat(development.getIncompleteCards(), empty());

        analysis.allocateDice(new StateDice(State.ANALYSIS, new LoadedDice(6)));
        analysis.visit(new Day(1));
        development.visit(new Day(1));

        assertThat(analysis.getIncompleteCards(), not(hasItem(Cards.getCard("S8"))));
        assertThat(development.getIncompleteCards(), hasItem(Cards.getCard("S8")));
    }

    @Test
    public void testPullFromUpstreamTraversesBoard() {
        Card s1 = Cards.getCard("S6");

        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn());
        StateColumn development = new StateColumn(State.DEVELOPMENT, analysis);
        StateColumn test = new StateColumn(State.TEST, development);
        analysis.addCard(s1);

        test.visit(new Day(1));

        assertThat(development.getIncompleteCards(), hasItem(s1));
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

        development.visit(new Day(1));
        assertThat(development.getCards().size(), is(1));
    }
}
