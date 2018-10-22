package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ColumnTest {
    @Test
    public void testCanAddCardsToColumn() {
        Column column = new Column(Activity.ANALYSIS, null);
        column.addCard(Card.S1);

        assertThat(column.getCards(), hasItem(Card.S1));
    }

    @Test
    public void testCanAllocateDiceToColumnAndRoll() {
        Column column = new Column(Activity.ANALYSIS, null);
        column.addCard(Card.S1);
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.rollDice();

        assertThat(column.getCards().get(0).getRemainingWork(Activity.ANALYSIS), is(0));
    }

    @Test
    public void testFinishingCardMakesItPullable() {
        Column column = new Column(Activity.ANALYSIS, null);
        column.addCard(Card.S1);
        column.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(6)));
        column.rollDice();

        assertThat(column.getCards(column.isPullable()).get(0), is(Card.S1));
    }

    @Test
    public void testCanPullFromUpstream() {
        Column analysis = new Column(Activity.ANALYSIS, null);
        analysis.addCard(Card.S1);
        analysis.addCard(Card.S2);
        analysis.allocateDice(new ActivityDice(Activity.ANALYSIS, new LoadedDice(1)));
        analysis.rollDice();

        Column development = new Column(Activity.DEVELOPMENT, analysis);
        development.pull();

        assertThat(development.getCards(), hasItem(Card.S1));
    }
}
