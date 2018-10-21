package uk.org.grant.getkanban.dice;

import org.junit.Test;
import uk.org.grant.getkanban.dice.Dice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class RandomDiceTest {
    @Test
    public void alwaysRollsBetweenOneAndSix() {
        Dice dice = new RandomDice(new Random());
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.roll(), allOf(greaterThan(0), lessThan(7)));
        }
    }
}
