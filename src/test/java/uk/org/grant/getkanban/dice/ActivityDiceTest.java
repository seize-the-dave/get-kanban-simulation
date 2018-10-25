package uk.org.grant.getkanban.dice;

import org.junit.Test;
import uk.org.grant.getkanban.State;

import java.util.Random;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class ActivityDiceTest {
    @Test
    public void alwaysRollsBetweenOneAndSixForOwnActivity() {
        ActivityDice dice = new ActivityDice(State.DEVELOPMENT, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(State.DEVELOPMENT), allOf(greaterThan(0), lessThan(7)));
        }
    }

    @Test
    public void alwaysRollsBetweenOneAndThreeForDifferentActivity() {
        ActivityDice dice = new ActivityDice(State.ANALYSIS, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(State.TEST), allOf(greaterThan(0), lessThan(4)));
        }
    }
}
