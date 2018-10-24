package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ActivityColumnTest {
    @Test
    public void testDoingWorkOnColumnReducesCardWork() {
        Card card = Cards.getCard("S1");

        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        column.addCard(card);
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.visit(new Day(1));

        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        column.addCard(Cards.getCard("S1"));
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.visit(new Day(1));

        assertThat(column.pull().get(), is(Cards.getCard("S1")));
    }

    @Test
    public void testCanPullFromUpstream() {
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        analysis.addCard(Cards.getCard("S8"));
//        analysis.addCard(Cards.getCard("S2"));

        ActivityColumn development = new ActivityColumn(Activity.DEVELOPMENT, analysis);
        assertThat(development.getIncompleteCards(), empty());

        analysis.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        analysis.visit(new Day(1));
        development.visit(new Day(1));

        assertThat(analysis.getIncompleteCards(), not(hasItem(Cards.getCard("S8"))));
        assertThat(development.getIncompleteCards(), hasItem(Cards.getCard("S8")));
    }

    @Test
    public void testPullFromUpstreamTraversesBoard() {
        Card s1 = Cards.getCard("S6");

        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        ActivityColumn development = new ActivityColumn(Activity.DEVELOPMENT, analysis);
        ActivityColumn test = new ActivityColumn(Activity.TEST, development);
        analysis.addCard(s1);

        test.visit(new Day(1));

        assertThat(development.getIncompleteCards(), hasItem(s1));
    }

    @Test
    public void canGetWipLimit() {
        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, 4, new NullColumn());

        assertThat(column.getLimit(), is(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotExceedWipLimit() {
        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, 1, new NullColumn());

        column.addCard(Cards.getCard("S1"));
        column.addCard(Cards.getCard("S2"));
    }

    @Test
    public void willNotPullBeyondWipLimit() {
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, 1, new NullColumn());
        ActivityColumn development = new ActivityColumn(Activity.ANALYSIS, 1, analysis);

        analysis.addCard(Cards.getCard("S1"));
        development.addCard(Cards.getCard("S2"));

        development.visit(new Day(1));
        assertThat(development.getCards().size(), is(1));
    }
}
