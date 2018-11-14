package uk.org.grant.getkanban.instructions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;

public class TedsTrainingOpportunity implements Instruction {
    private static final Logger LOGGER = LoggerFactory.getLogger(TedsTrainingOpportunity.class);

    private final boolean training;

    public TedsTrainingOpportunity(boolean training) {
        this.training = training;    
    }

    @Override
    public void execute(Board b) {
        if (this.training) {
            LOGGER.info("Ted the tester is going away on a training course");
            b.removeDice(b.getDice(State.TEST).get(0));
        }
    }
}
