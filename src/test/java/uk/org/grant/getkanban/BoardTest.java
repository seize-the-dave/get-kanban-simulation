package uk.org.grant.getkanban;

import org.junit.Test;
import uk.org.grant.getkanban.dice.ActivityDice;
import uk.org.grant.getkanban.dice.Dice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class BoardTest {
    @Test
    public void testGetDiceFromBoard() {
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new RandomDice(new Random()));
        Board board = new Board(dice);
        assertThat(board.getDice(), hasItems(dice));
    }
}
