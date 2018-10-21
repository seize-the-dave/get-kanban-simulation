package uk.org.grant.getkanban;

import org.junit.Test;

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
