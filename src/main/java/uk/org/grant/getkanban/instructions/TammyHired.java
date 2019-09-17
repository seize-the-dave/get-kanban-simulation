package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;
import uk.org.grant.getkanban.dice.RandomDice;
import uk.org.grant.getkanban.dice.StateDice;

import java.util.Random;

public class TammyHired implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(TammyHired.class);
    private final boolean training;

    public TammyHired(boolean training) {
        this.training = training;
    }

    @Override
    public void execute(Board b) {
        if (training) {
            LOGGER.info("Ted has returned from his training course");
            // Ted is Back
            b.addDice(new StateDice(State.TEST, new RandomDice()));
            // Tammy Joins
            LOGGER.info("Tammy has joined the test team");
            b.addDice(new StateDice(State.TEST, new RandomDice()));
        }
    }
}
