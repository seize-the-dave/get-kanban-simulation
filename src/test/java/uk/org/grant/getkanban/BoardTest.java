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
        ActivityDice dice = new ActivityDice(State.ANALYSIS, new RandomDice(new Random()));
        Board board = new Board();
        board.addDice(dice);
        assertThat(board.getDice(), hasItems(dice));
    }

    @Test
    public void canGetColumnsForBoard() {
        ActivityColumn column = new ActivityColumn(State.ANALYSIS, new NullColumn());
        Board board = new Board();
        board.setColumn(State.ANALYSIS, column);
        assertThat(board.getColumn(State.ANALYSIS), is(column));
    }
}
