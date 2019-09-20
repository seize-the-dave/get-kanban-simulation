package uk.org.grant.getkanban.dice;

import java.util.Random;

public class RandomDice implements Dice {
    private static Random RANDOM = new Random();

    public int roll() {
        return 1 + RANDOM.nextInt(6);
    }
}
