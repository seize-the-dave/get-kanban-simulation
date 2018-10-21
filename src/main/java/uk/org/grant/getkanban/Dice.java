package uk.org.grant.getkanban;

import java.util.Random;

public class Dice {
    private final Random random = new Random();

    public int roll() {
        return 1 + random.nextInt(6);
    }
}
