package uk.org.grant.getkanban.dice;

import org.junit.Test;
import uk.org.grant.getkanban.Role;
import uk.org.grant.getkanban.dice.RandomDice;
import uk.org.grant.getkanban.dice.RoleSpecificDice;

import java.util.Random;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class RollSpecificDiceTest {
    @Test
    public void alwaysRollsBetweenOneAndSixForOwnRole() {
        RoleSpecificDice dice = new RoleSpecificDice(Role.DEVELOPMENT, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(Role.DEVELOPMENT), allOf(greaterThan(0), lessThan(7)));
        }
    }

    @Test
    public void alwaysRollsBetweenOneAndThreeForDifferentRole() {
        RoleSpecificDice dice = new RoleSpecificDice(Role.ANALYSIS, new RandomDice(new Random()));
        for (int i = 0; i < 10000; i++) {
            assertThat(dice.rollFor(Role.TEST), allOf(greaterThan(0), lessThan(4)));
        }
    }
}
