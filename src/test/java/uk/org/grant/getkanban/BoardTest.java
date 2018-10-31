package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.column.NullColumn;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void testGetDiceFromBoard() {
        StateDice dice = new StateDice(State.ANALYSIS, new RandomDice(new Random()));
        Board board = new Board();
        board.addDice(dice);
        assertThat(board.getDice(), hasItems(dice));
    }

    @Test
    public void canGetColumnsForBoard() {
        StateColumn column = new StateColumn(State.ANALYSIS, new NullColumn());
        Board board = new Board();
        board.setColumn(State.ANALYSIS, column);
        assertThat(board.getColumn(State.ANALYSIS), is(column));
    }

    @Test
    public void canRemoveDiceBoard() {
        StateDice dice = new StateDice(State.ANALYSIS, new RandomDice(new Random()));
        Board board = new Board();
        board.addDice(dice);

        assertThat(board.getDice(), hasItems(dice));

        board.removeDice(dice);
        assertThat(board.getDice(), empty());
    }
}
