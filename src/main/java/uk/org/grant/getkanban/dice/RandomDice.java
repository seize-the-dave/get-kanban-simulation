package uk.org.grant.getkanban.dice;

import java.util.Random;

public class RandomDice implements Dice {
    private final Random random;

    public RandomDice(Random random) {
        this.random = random;
    }

    public int roll() {
        return 1 + random.nextInt(6);
    }
}
