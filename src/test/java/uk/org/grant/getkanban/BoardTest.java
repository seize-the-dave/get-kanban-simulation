package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void testGetDiceFromBoard() {
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new RandomDice(new Random()));
        Board board = new Board();
        board.addDice(dice);
        assertThat(board.getDice(), hasItems(dice));
    }

    @Test
    public void canGetColumnsForBoard() {
        ActivityColumn column = new ActivityColumn(Activity.ANALYSIS, new NullColumn());
        Board board = new Board();
        board.setColumn(Column.Type.ANALYSIS, column);
        assertThat(board.getColumn(Column.Type.ANALYSIS), is(column));
    }
}
