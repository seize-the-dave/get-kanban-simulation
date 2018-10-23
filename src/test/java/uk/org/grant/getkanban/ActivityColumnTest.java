package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ActivityColumnTest {
    @Test
    public void testDoingWorkOnColumnReducesCardWork() {
        Card card = Card.S1;

        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        column.addCard(card);
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.doWork();

        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        column.addCard(Card.S1);
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.doWork();

        assertThat(column.pullCard().get(), is(Card.S1));
    }

    @Test
    public void testCanPullFromUpstream() {
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        ActivityColumn development = new ActivityColumn(Activity.DEVELOPMENT, analysis);

        analysis.addCard(Card.S1);
        analysis.addCard(Card.S2);
        analysis.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(1)));
        development.pullFromUpstream();
        assertThat(development.getIncompleteCards(), empty());

        analysis.doWork();
        development.pullFromUpstream();

        assertThat(analysis.getIncompleteCards(), not(hasItem(Card.S1)));
        assertThat(development.getIncompleteCards(), hasItem(Card.S1));
    }

    @Test
    public void testPullFromUpstreamTraversesBoard() {
        Card emptyCard = new Card(Card.Size.SMALL, 0, 0, 1, new SubscriberProfile(new int[] {}));

        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        ActivityColumn development = new ActivityColumn(Activity.DEVELOPMENT, analysis);
        ActivityColumn test = new ActivityColumn(Activity.TEST, development);
        analysis.addCard(emptyCard);

        test.pullFromUpstream();

        assertThat(test.getIncompleteCards(), hasItem(emptyCard));
    }
}
