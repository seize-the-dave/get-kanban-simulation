package uk.org.grant.getkanban.instructions;

import uk.org.grant.getkanban.Board;
import uk.org.grant.getkanban.State;

public class TedsTrainingOpportunity implements Instruction {

    private final boolean training;

    public TedsTrainingOpportunity(boolean training) {
        this.training = training;    
    }

    @Override
    public void execute(Board b) {
        if (this.training) {
            b.removeDice(b.getDice(State.TEST).get(0));
        }
    }
}
