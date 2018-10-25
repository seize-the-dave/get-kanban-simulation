package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.Column;
import uk.org.grant.getkanban.dice.StateDice;
import uk.org.grant.getkanban.dice.RandomDice;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CarlosFired implements Instruction {
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
    }

    private void restoreWipLimitsToTest(Board b) {
        Column c = b.getColumn(State.TEST);
        c.setLimit(store.get());
    }
}
