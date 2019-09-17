package uk.org.grant.getkanban.column;

import org.junit.Test;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.card.Blocker;
import uk.org.grant.getkanban.card.Card;
import uk.org.grant.getkanban.card.Cards;
import uk.org.grant.getkanban.dice.DiceGroup;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.LoadedDice;
import uk.org.grant.getkanban.policies.BusinessValuePrioritisationStrategy;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StateColumnTest {
    @Test
    public void testDoingWorkOnColumnReducesCardWork() {
        Card card = Cards.getCard("S1");
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn(), new NullColumn());
        column.addCard(card, ClassOfService.STANDARD);

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        DiceGroup group = new DiceGroup(column.getCards().get(0), dice);
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
        b.getStateColumn(State.ANALYSIS).addCard(s8, ClassOfService.STANDARD);
        b.getStateColumn(State.ANALYSIS).addCard(s12, ClassOfService.STANDARD);
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
        b.getStateColumn(State.ANALYSIS).addCard(s8, ClassOfService.STANDARD);
        b.getStateColumn(State.ANALYSIS).addCard(s12, ClassOfService.STANDARD);
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
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn(), new NullColumn());
        column.addCard(Cards.getCard("S1"), ClassOfService.STANDARD);

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        column.assignDice(new DiceGroup(column.getCards().get(0), dice));

        column.doTheWork(context);

        assertThat(column.pull(context, ClassOfService.STANDARD).get(), is(Cards.getCard("S1")));
    }

    @Test
    public void testCanPullFromUpstream() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, new NullColumn(), new NullColumn());
        analysis.addCard(Cards.getCard("S8"), ClassOfService.STANDARD);

        StateColumn development = new StateColumn(State.DEVELOPMENT, analysis, new NullColumn());
        assertThat(development.getIncompleteCards(), empty());

        StateDice dice = new StateDice(State.ANALYSIS, new LoadedDice(6));
        analysis.assignDice(new DiceGroup(analysis.getCards().get(0), dice));
        analysis.doTheWork(new Context(new Board(), new Day(1)));
        development.doTheWork(new Context(new Board(), new Day(1)));

        assertThat(analysis.getIncompleteCards(), not(hasItem(Cards.getCard("S8"))));
        assertThat(development.getIncompleteCards(), hasItem(Cards.getCard("S8")));
    }

    @Test
    public void canGetWipLimit() {
        StateColumn column = new StateColumn(State.ANALYSIS, 4, new NullColumn(), new NullColumn());

        assertThat(column.getLimit(), is(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotExceedWipLimit() {
        StateColumn column = new StateColumn(State.ANALYSIS, 1, new NullColumn(), new NullColumn());

        column.addCard(Cards.getCard("S1"), ClassOfService.STANDARD);
        column.addCard(Cards.getCard("S2"), ClassOfService.STANDARD);
    }

    @Test
    public void willNotPullBeyondWipLimit() {
        StateColumn analysis = new StateColumn(State.ANALYSIS, 1, new NullColumn(), new NullColumn());
        StateColumn development = new StateColumn(State.ANALYSIS, 1, analysis, new NullColumn());

        analysis.addCard(Cards.getCard("S1"), ClassOfService.STANDARD);
        development.addCard(Cards.getCard("S2"), ClassOfService.STANDARD);

        development.doTheWork(new Context(new Board(), new Day(1)));
        assertThat(development.getCards().size(), is(1));
    }

    @Test
    public void canChangePriority() {
        Column deployed = new StateColumn(State.ANALYSIS, 2, new NullColumn(), new NullColumn());
        deployed.addCard(Cards.getCard("S10"), ClassOfService.STANDARD);
        deployed.addCard(Cards.getCard("S5"), ClassOfService.STANDARD);

        assertThat(deployed.getCards().get(0).getName(), is("S5"));

        deployed.orderBy(new BusinessValuePrioritisationStrategy());

        assertThat(deployed.getCards().get(0).getName(), is("S10"));
    }

    @Test
    public void triggersListenerWhenCardAdded() {
        StateColumn column = new StateColumn(State.TEST, 2, new NullColumn(), new NullColumn());
        column.addListener(c -> c.doWork(State.TEST, 2));

        Card s8 = Cards.getCard("S8");
        assertThat(s8.getRemainingWork(State.TEST), is(9));

        column.addCard(s8, ClassOfService.STANDARD);

        assertThat(s8.getRemainingWork(State.TEST), is(7));
    }
}
