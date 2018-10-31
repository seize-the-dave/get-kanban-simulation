package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.RandomDice;
import uk.org.grant.getkanban.dice.StateDice;

import java.util.Random;

public class TammyHired implements Instruction {
    private final boolean training;

    public TammyHired(boolean training) {
        this.training = training;
    }

    @Override
    public void execute(Board b) {
        // Ted is Back
        b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));
        if (training) {
            // Tammy Joins
            b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));
        }
    }
}
