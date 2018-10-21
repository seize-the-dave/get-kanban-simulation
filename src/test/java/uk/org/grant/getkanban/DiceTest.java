package uk.org.grant.getkanban;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class DiceTest {
    @Test
    public void alwaysRollsBetweenOneAndSix() {
        Dice dice = new Dice();
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.roll(), allOf(greaterThan(0), lessThan(7)));
        }
    }
}
