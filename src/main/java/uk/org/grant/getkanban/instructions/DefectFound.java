package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;

public class DefectFound implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefectFound.class);

    @Override
    public void execute(Board b) {
        LOGGER.info("Testing has found a defect!");
        // Add to the first item in test, or to the next defect to arrive in test
    }
}
