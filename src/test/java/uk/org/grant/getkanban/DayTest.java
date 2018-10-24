package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DayTest {
    @Test
    public void daysAllocateDiceDuringStandup() {
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, 4, new NullColumn());
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new LoadedDice(6));
        Board board = new Board();
        board.addDice(dice);
        board.setColumn(Column.Type.ANALYSIS, analysis);

        Day day = new Day(1);
        day.standUp(board);

        assertThat(analysis.getAllocatedDice(), hasItem(dice));
    }

    @Test
    public void daysCompleteCardsDuringWork() {
        Card card = new Card("S1", Card.Size.LOW, 1, 1, 1, new SubscriberProfile(new int[] {}));
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        analysis.addCard(card);
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new LoadedDice(6));
        Board board = new Board();
        board.addDice(dice);
        board.setColumn(Column.Type.ANALYSIS, analysis);

        Day day = new Day(1);
        day.standUp(board);
        day.visit(board);

        assertThat(card.getRemainingWork(Activity.ANALYSIS), is(0));
    }

    @Test
    public void canGetOrdinal() {
        Day day = new Day(1);
        assertThat(day.getOrdinal(), is(1));
    }
}
