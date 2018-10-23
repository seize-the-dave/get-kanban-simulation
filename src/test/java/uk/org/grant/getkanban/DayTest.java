package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.LoadedDice;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;

public class DayTest {
    @Test
    public void daysCanAllocateDiceDuringStandup() {
        ActivityColumn analysis = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new LoadedDice(6));
        Board board = new Board();
        board.addDice(dice);
        board.setColumn(Column.Type.ANALYSIS, analysis);

        Day day = new Day();
        day.standUp(board);

        assertThat(analysis.getAllocatedDice(), hasItem(dice));
    }
}
