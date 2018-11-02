package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.StateColumn;

import java.util.concurrent.atomic.AtomicInteger;

public class CarlosHired implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarlosHired.class);
    private final AtomicInteger store;

    public CarlosHired(AtomicInteger store) {
        this.store = store;
    }

    @Override
    public void execute(Board b) {
        StateColumn c = b.getStateColumn(State.TEST);
        store.set(c.getLimit());
        c.setLimit(Integer.MAX_VALUE);
        LOGGER.info("Removed WIP limit on {}", c);
    }
}
