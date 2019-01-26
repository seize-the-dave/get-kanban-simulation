package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.Context;
import uk.org.grant.getkanban.card.Blocker;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.Day;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.policies.prioritisation.BusinessValuePrioritisationStrategy;

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
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(5)));
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(5)));

        d.standUp(b);
        d.doTheWork(new Context(b, d));

        assertThat(s8.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(s12.getRemainingWork(State.ANALYSIS), is(0));
    }

    @Test
    public void doNotSpendLeftoverWorkOnBlockedItems() {
        Board b = new Board();
        Day d = new Day(10);

        Card s8 = Cards.getCard("S8");
        Card s12 = Cards.getCard("S12");
        s12.setBlocker(new Blocker());
        b.getStateColumn(State.ANALYSIS).addCard(s8);
        b.getStateColumn(State.ANALYSIS).addCard(s12);
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(5)));
        b.addDice(new StateDice(State.ANALYSIS, new LoadedDice(5)));

        d.standUp(b);
        d.doTheWork(new Context(b, d));

        assertThat(s8.getRemainingWork(State.ANALYSIS), is(0));
        assertThat(s12.getRemainingWork(State.ANALYSIS), is(5));
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

    @Test
    public void canChangePriority() {
        Column deployed = new StateColumn(State.ANALYSIS, 2, new NullColumn());
        deployed.addCard(Cards.getCard("S10"));
        deployed.addCard(Cards.getCard("S5"));

        assertThat(deployed.getCards().peek().getName(), is("S5"));

        deployed.orderBy(new BusinessValuePrioritisationStrategy());

        assertThat(deployed.getCards().peek().getName(), is("S10"));
    }

    @Test
    public void triggersListenerWhenCardAdded() {
        StateColumn column = new StateColumn(State.TEST, 2, new NullColumn());
        column.addListener(c -> c.doWork(State.TEST, 2));

        Card s8 = Cards.getCard("S8");
        assertThat(s8.getRemainingWork(State.TEST), is(9));

        column.addCard(s8);

        assertThat(s8.getRemainingWork(State.TEST), is(7));
    }
}
