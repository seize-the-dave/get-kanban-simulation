package uk.org.grant.getkanban.dice;

import org.junit.Test;
import uk.org.grant.getkanban.Activity;

import java.util.Random;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class ActivityDiceTest {
    @Test
    public void alwaysRollsBetweenOneAndSixForOwnActivity() {
        ActivityDice dice = new ActivityDice(Activity.DEVELOPMENT, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(Activity.DEVELOPMENT), allOf(greaterThan(0), lessThan(7)));
        }
    }

    @Test
    public void alwaysRollsBetweenOneAndThreeForDifferentActivity() {
        ActivityDice dice = new ActivityDice(Activity.ANALYSIS, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(Activity.TEST), allOf(greaterThan(0), lessThan(4)));
        }
    }
}
