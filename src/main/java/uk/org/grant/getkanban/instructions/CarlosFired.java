package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CarlosFired implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarlosFired.class);
    private final AtomicInteger store;

    public CarlosFired(AtomicInteger store) {
        this.store = store;
    }

    @Override
    public void execute(Board b) {
        restoreWipLimitsToTest(b);
        hireAnotherTester(b);
    }

    private void hireAnotherTester(Board b) {
        b.addDice(new StateDice(State.TEST, new RandomDice(new Random())));
        LOGGER.info("Hired additional tester");
    }

    private void restoreWipLimitsToTest(Board b) {
        Column c = b.getColumn(State.TEST);
        c.setLimit(store.get());
        LOGGER.info("Restored WIP limit on {}", c);
    }
}
