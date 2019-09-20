package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.column.StateColumn;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CarlosFired implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarlosFired.class);

    @Override
    public void execute(Board b) {
        LOGGER.info("Carlos has been fired.");

        restoreWipLimitsToTest(b);
        hireAnotherTester(b);
    }

    private void hireAnotherTester(Board b) {
        b.addDice(new StateDice(State.TEST, new RandomDice()));
        LOGGER.info("Another tester has been hired");
    }

    private void restoreWipLimitsToTest(Board b) {
        StateColumn c = b.getStateColumn(State.TEST);
        c.enableLimits();
        c.enableSecondaryWorkers();
        LOGGER.info("The original WIP limit on test has been restored");
    }
}
