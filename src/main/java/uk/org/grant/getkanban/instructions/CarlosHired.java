package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.*;
import uk.org.grant.getkanban.column.StateColumn;

public class CarlosHired implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarlosHired.class);

    @Override
    public void execute(Board b) {
        LOGGER.info("Carlos has been hired as Test Manager.");

        StateColumn c = b.getStateColumn(State.TEST);
        c.disableLimits();
        c.disableSecondaryWorkers();
        LOGGER.info("Carlos has removed the WIP limit on test");
    }
}
